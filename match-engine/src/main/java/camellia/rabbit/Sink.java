package camellia.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:20
 */
public interface Sink {
    @Input("order_in")
    MessageChannel messageChannel() ;
}
