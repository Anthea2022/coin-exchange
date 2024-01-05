package camellia.disruptor;

import camellia.domain.model.Order;
import camellia.domain.model.OrderBook;
import camellia.enums.MatchStrategy;
import camellia.factory.MatchServiceFactory;
import com.lmax.disruptor.EventHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:12
 * 多少种symbol，就有多少个该类对象
 * 一个对象只能由一个线程执行
 */
@Data
@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent> {
   private OrderBook orderBook;

   public OrderEventHandler(OrderBook orderBook) {
       this.orderBook = orderBook;
   }

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        Order order = (Order) orderEvent.getResource();
        if (!order.getSymbol().equals(orderBook.getSymbol())) {
            return;
        }
        log.info("开始处理订单事件===============================>{}", orderEvent);
        MatchServiceFactory.getMatchByName(MatchStrategy.LIMIT_PRICE.getName()).match(orderBook, order);
        orderBook.add(order);
        log.info("订单处理完成===================================>{}", orderEvent);
    }
}
