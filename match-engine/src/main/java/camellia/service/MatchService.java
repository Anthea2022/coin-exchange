package camellia.service;

import camellia.domain.model.*;
import camellia.enums.OrderDirection;
import camellia.factory.MatchServiceFactory;
import camellia.rocket.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 14:53
 */

/**
 * sell价低者优先
 * buy价高者优先
 * 相同创建时间早优先
 */
@Slf4j
@Service
public class MatchService implements InitializingBean {
    private Source source;

    /**
     * 撮合交易
     * @param order
     */
    public void match(OrderBook orderBook, Order order) {
        log.info("开始撮合订单");
        if(order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        // 获取一个挂单队列
        Iterator<Map.Entry<BigDecimal, MergeOrder>> marketQueue = null;
        if (order.getOrderDirection() == OrderDirection.BUY) {
            marketQueue = orderBook.getCurrentOrderIterator(OrderDirection.SELL);
        } else if (order.getOrderDirection() == OrderDirection.SELL) {
            marketQueue = orderBook.getCurrentOrderIterator(OrderDirection.BUY);
        }

        // 已经完成的订单
        List<Order> completedOrder = new ArrayList<>();

        // 产生交易记录
        List<ExchangeTrade> exchangeTrades = new ArrayList<>();

        boolean exit = false;
        while(marketQueue.hasNext() && false) {
            Map.Entry<BigDecimal, MergeOrder> marketNext = marketQueue.next();
            BigDecimal marketPrice = marketNext.getKey();
            MergeOrder marketMergeOrder = marketNext.getValue();
            // 出价比卖价低买不了
            if (order.getOrderDirection() == OrderDirection.BUY &&order.getPrice().compareTo(marketPrice) < 0) {
                return;
            }
            //卖价比别人高卖不出去
            if (order.getOrderDirection() == OrderDirection.SELL && order.getPrice().compareTo(marketPrice) > 0) {
                return;
            }
            Iterator<Order> iterator = marketMergeOrder.iterator();
            while(iterator.hasNext()) {
                Order market = iterator.next();
                ExchangeTrade exchangeTrade = processMath(order, market, orderBook);
                exchangeTrades.add(exchangeTrade);
                // 我的订单完成
                if (order.isCompleted()) {
                    completedOrder.add(order);
                    exit = true;
                    break;
                }

                // 别人的订单完成 MergeOrder的一个Order完成
                if (market.isCompleted()) {
                    completedOrder.add(market);
                    iterator.remove();
                }
            }
            // 同一价格的订单都完成 整个MergeOrder完成
            if (marketMergeOrder.size() == 0) {
                marketQueue.remove();
            }
        }
        // 如果订单没有完成
        if (order.getAmount().compareTo(order.getTradeAmount()) > 0) {
            orderBook.add(order);
        }
        handleExchangeOrders(exchangeTrades);
        if (completedOrder.size() > 0) {
            //更新盘口数据
            completeOrder(completedOrder);
            TradePlate tradePlate = order.getOrderDirection() == OrderDirection.BUY ?
                    orderBook.getBuyTradePlate() : orderBook.getSellTradePlate();
            saveTradePlateData(tradePlate);
        }
    }

    /**
     * 进行委托单的匹配
     * @param order
     * @param market
     * @return
     */
    private ExchangeTrade processMath(Order order, Order market, OrderBook orderBook) {
        // 成交的价格
        BigDecimal dealPrice = market.getPrice();
        // 成交数量
        BigDecimal dealAmount = BigDecimal.ZERO;
        // 需要的数量
        BigDecimal needAmount = realAmout(order);
        // 提供的数量
        BigDecimal provideAmount = realAmout(market);

        dealAmount = needAmount.compareTo(provideAmount) <= 0 ? needAmount : provideAmount;
        if (dealPrice.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        order.setTradeAmount(order.getTradeAmount().add(dealAmount));
        BigDecimal turnover = dealAmount.multiply(dealPrice).setScale(orderBook.getCoinScale(), RoundingMode.HALF_UP);
        order.setTurnover(turnover);
        market.setTradeAmount(market.getTradeAmount().add(dealAmount));
        market.setTurnover(turnover);


        ExchangeTrade exchangeTrade = new ExchangeTrade();

        exchangeTrade.setAmount(dealAmount); // 设置购买的数量
        exchangeTrade.setPrice(dealPrice);  // 设置购买的价格
        exchangeTrade.setCreateTime(new Date()); // 设置成交的时间
        exchangeTrade.setSymbol(orderBook.getSymbol());  // 设置成交的交易对
        exchangeTrade.setDirection(order.getOrderDirection());  // 设置交易的方法
        exchangeTrade.setSellOrderId(market.getId()); // 设置出售方的id
        exchangeTrade.setBuyOrderId(order.getId()); // 设置买方的id

        exchangeTrade.setBuyTurnover(order.getTurnover()); // 设置买方的交易额
        exchangeTrade.setSellTurnover(market.getTurnover()); // 设置卖方的交易额

        if (order.getOrderDirection() == OrderDirection.BUY) {
            orderBook.getBuyTradePlate().remove(market, dealAmount);
        } else {
            orderBook.getSellTradePlate().remove(market, dealAmount);
        }
        return exchangeTrade;
    }

    private BigDecimal realAmout(Order order) {
        return order.getAmount().subtract(order.getTradeAmount());
    }

    /**
     * 发送盘口数据
     * @param tradePlate
     */
    private void saveTradePlateData(TradePlate tradePlate) {
        MessageBuilder<TradePlate> tradePlateMessageBuilder = MessageBuilder.withPayload(tradePlate).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        source.plateOrderOut().send(tradePlateMessageBuilder.build());
    }

    /**
     * 处理完成的订单数据
     * @param completedOrder
     */
    private void completeOrder(List<Order> completedOrder) {
        MessageBuilder<List<Order>> completedOrderMessageBuilder = MessageBuilder.withPayload(completedOrder).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        source.completedOrderOut().send(completedOrderMessageBuilder.build());
    }

    /**
     * 进行委托单的匹配
     */
    private void handleExchangeOrders(List<ExchangeTrade> exchangeTrades) {
        MessageBuilder<List<ExchangeTrade>> entrustOrderMessageBuilder = MessageBuilder.withPayload(exchangeTrades).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        source.exchangeOrderOut().send(entrustOrderMessageBuilder.build());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService("", this);
    }
}
