package camellia.util;

import camellia.domain.model.EntrustOrder;
import camellia.domain.model.Order;
import camellia.enums.OrderDirection;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:24
 */
public class BeanUtil {
    public static Order entrustOrderToOrder(EntrustOrder entrustOrder) {
        Order order = new Order();
        order.setId(entrustOrder.getId());
        order.setPrice(entrustOrder.getPrice());
        order.setAmount(entrustOrder.getVolume().subtract(entrustOrder.getDeal())); // 交易的数量= 总数量- 已经成交的数量

        order.setSymbol(entrustOrder.getSymbol());
        order.setOrderDirection(OrderDirection.getOrderDirection(entrustOrder.getType()));
        order.setCreateTime(entrustOrder.getCreateTime());
        return order;
    }
}
