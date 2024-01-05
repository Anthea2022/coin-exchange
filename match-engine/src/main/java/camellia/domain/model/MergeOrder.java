package camellia.domain.model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 12:55
 */
public class MergeOrder {
    private LinkedList<Order> orders = new LinkedList<>();

    /**
     * 按时间先后排序
     * @param order
     */
    public void add(Order order) {
        orders.addLast(order);
    }

    public Order get() {
        return orders.get(0);
    }

    public int size() {
        return orders.size();
    }

    public BigDecimal getPrice() {
        return orders.get(0).getPrice();
    }

    public Iterator<Order> iterator() {
        return orders.iterator();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = new BigDecimal(0);
        for (Order order : orders) {
            total = total.add(order.getAmount());
        }
        return total;
    }
}
