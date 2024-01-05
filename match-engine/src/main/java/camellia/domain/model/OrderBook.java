package camellia.domain.model;

import camellia.common.ResponseCodes;
import camellia.enums.OrderDirection;
import camellia.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 13:06
 */
public class OrderBook {
    private TreeMap<BigDecimal, MergeOrder> buyOrder;

    private TreeMap<BigDecimal, MergeOrder> sellOrder;

    private int coinScale;

    private int baseCoinScale;

    private String symbol;

    private TradePlate buyTradePlate;

    private TradePlate sellTradePlate;

    private SimpleDateFormat simpleDateFormat;

    public OrderBook(String symbol) {
        this(symbol, 4, 4);
    }

    public OrderBook(String symbol, int coinScale, int baseCoinScale) {
        this.symbol = symbol;
        this.coinScale = coinScale;
        this.baseCoinScale = baseCoinScale;
        this.initialize();
    }

    public void initialize() {
        buyOrder = new TreeMap<>(Comparator.reverseOrder());
        sellOrder =  new TreeMap<>(Comparator.naturalOrder());
        buyTradePlate = new TradePlate(symbol, OrderDirection.BUY);
        sellTradePlate = new TradePlate(symbol, OrderDirection.SELL);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public TreeMap<BigDecimal, MergeOrder> getCurrentOrder(OrderDirection orderDirection) {
        return orderDirection == OrderDirection.BUY ? buyOrder : sellOrder;
    }

    public Iterator<Map.Entry<BigDecimal, MergeOrder>> getCurrentOrderIterator(OrderDirection orderDirection) {
        return getCurrentOrder(orderDirection).entrySet().iterator();
    }

    public void add(Order order) {
        TreeMap<BigDecimal, MergeOrder> currentOrder = getCurrentOrder(order.getOrderDirection());
        MergeOrder mergeOrder = currentOrder.get(order.getPrice());
        if (ObjectUtils.isEmpty(mergeOrder)) {
            mergeOrder = new MergeOrder();
            currentOrder.put(order.getPrice(), mergeOrder);
        }
        mergeOrder.add(order);
        if (order.getOrderDirection() == OrderDirection.BUY) {
            buyTradePlate.add(order);
        } else if (order.getOrderDirection() == OrderDirection.SELL) {
            sellTradePlate.add(order);
        } else {
            throw new BusinessException(ResponseCodes.FAIL, "无此交易方向");
        }
    }

    public void remove(Order order) {
        TreeMap<BigDecimal, MergeOrder> currentOrder = getCurrentOrder(order.getOrderDirection());
        MergeOrder mergeOrder = currentOrder.get(order.getPrice());
        if (ObjectUtils.isEmpty(mergeOrder)) {
            return;
        }
        Iterator<Order> iterator = mergeOrder.iterator();
        while(iterator.hasNext()) {
            Order next = iterator.next();
            if (next.getId().equals(order.getId())) {
                iterator.remove();
            }
        }
        int size = mergeOrder.size();
        if (size == 0) {
            currentOrder.remove(order.getPrice());
        }
        if (order.getOrderDirection() == OrderDirection.BUY) {
            buyTradePlate.remove(order);
        } else if (order.getOrderDirection() == OrderDirection.SELL) {
            sellTradePlate.remove(order);
        } else {
            throw new BusinessException(ResponseCodes.FAIL, "无此交易方向");
        }
    }

    public Map.Entry<BigDecimal, MergeOrder> getBestSuitOrder(OrderDirection orderDirection) {
        return getCurrentOrder(orderDirection).firstEntry();
    }

    public String getSymbol() {
        return symbol;
    }
}
