package camellia.rocket;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 18:51
 */
public interface Source {
    @Output("order_out")
    MessageChannel outputMessage();
}
