package camellia.rocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 13:30
 */
public interface Sink {
    @Input("tio_group")
    MessageChannel tioGroupChannel();
}
