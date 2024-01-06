package camellia.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 18:55
 */
@Configuration
@EnableBinding(value = Source.class)
public class RabbitMQConfig {
}
