package camellia.disruptor;

import camellia.domain.model.Order;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 20:17
 */
public class DisruptorTemplate {
    private static final EventTranslatorOneArg<OrderEvent, Order> TRANSLATOR = new EventTranslatorOneArg<OrderEvent, Order>() {
        @Override
        public void translateTo(OrderEvent orderEvent, long l, Order order) {

        }
    };

    private final RingBuffer<OrderEvent> ringBuffer;

    public DisruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Order input) {
        ringBuffer.publishEvent(TRANSLATOR, input);
    }
}
