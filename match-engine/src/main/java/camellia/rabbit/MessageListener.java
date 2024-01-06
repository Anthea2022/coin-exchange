package camellia.rabbit;

import camellia.disruptor.DisruptorTemplate;
import camellia.domain.model.EntrustOrder;
import camellia.domain.model.Order;
import camellia.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:21
 */
@Service
public class MessageListener {
    @Autowired
    private DisruptorTemplate disruptorTemplate;

    @StreamListener("order_in")
    public void handleMessage(EntrustOrder entrustOrder) {
        Order order = null;
        if (entrustOrder.getStatus() == 2) { // 该单需要取消
            order = new Order();
            order.setId(entrustOrder.getId());
            order.setCancel(true);
            order.setCancelTime(new Date());
        } else {
            order = BeanUtil.entrustOrderToOrder(entrustOrder);
        }
        disruptorTemplate.onData(order);
    }
}
