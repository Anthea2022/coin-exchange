package camellia.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:20
 */
public interface Source {
    /**
     * 完成订单输出
     * @return
     */
    @Output("completed_order_out")
    MessageChannel completedOrderOut();

    /**
     *交易记录输出
     * @return
     */
    @Output("exchange_order_out")
    MessageChannel exchangeOrderOut();

    /**
     * 取消订单输出
     * @return
     */
    @Output("cancel_order_out")
    MessageChannel cancelOrderOut();
}
