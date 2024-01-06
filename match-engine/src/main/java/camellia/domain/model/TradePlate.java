package camellia.domain.model;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 12:03
 */

import camellia.domain.vo.DepthItemVo;
import camellia.enums.OrderDirection;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 盘口数据
 */
@Data
public class TradePlate {
    private LinkedList<DepthItemVo> items = new LinkedList<>();

    private int maxDepth = 100;

    private OrderDirection orderDirection;

    private String symbol;

    public TradePlate(String symbol, OrderDirection orderDirection) {
        this.symbol = symbol;
        this.orderDirection = orderDirection;
    }

    public void add(Order order) {
        if (order.getOrderDirection() != orderDirection) {
            return;
        }
        int i = 0;
        for (;i<items.size();i++) {
            // sell从小到大
            // buy从大到小
            DepthItemVo depthItemVo = items.get(i);
            if ((orderDirection == OrderDirection.BUY && order.getPrice().compareTo(depthItemVo.getPrice()) == -1) ||
                    (orderDirection == OrderDirection.SELL && order.getPrice().compareTo(depthItemVo.getPrice()) == 1)) {
                continue;
            } else if (depthItemVo.getPrice().compareTo(order.getPrice()) == 0) {
                depthItemVo.setVolume(depthItemVo.getVolume().add(order.getAmount().subtract(order.getTradeAmount())));
                return;
            } else {
                break;
            }
        }
        if (i < maxDepth) {
            DepthItemVo depthItemVo = new DepthItemVo();
            depthItemVo.setPrice(order.getPrice());
            depthItemVo.setVolume(order.getAmount().subtract(order.getTradeAmount()));
            items.add(i, depthItemVo);
        }
    }

    public void remove(Order order, BigDecimal amount) {
        if (items.size() == 0) {
            return;
        }
        if (orderDirection != order.getOrderDirection()) {
            return;
        }
        Iterator<DepthItemVo> iterator = items.iterator();
        while (iterator.hasNext()) {
            DepthItemVo depthItemVo = iterator.next();
            if (order.getPrice().compareTo(depthItemVo.getPrice()) == 0) {
                depthItemVo.setVolume(depthItemVo.getVolume().subtract(amount));
                if (depthItemVo.getVolume().compareTo(BigDecimal.ZERO) <= 0) {
                    iterator.remove();
                }
            }
        }
    }

    public void remove(Order order) {
        remove(order, order.getAmount().subtract(order.getTradeAmount()));
    }
}
