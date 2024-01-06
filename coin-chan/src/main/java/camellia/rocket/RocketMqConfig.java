package camellia.rocket;

import camellia.rocket.Sink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 13:32
 */
@Configuration
@EnableBinding(value = Sink.class)
public class RocketMqConfig {
}
