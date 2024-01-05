package camellia.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 21:19
 */
@Configuration
@EnableBinding(value = {Sink.class,Source.class})
public class RabbitConfig {
}
