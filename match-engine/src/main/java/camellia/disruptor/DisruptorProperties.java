package camellia.disruptor;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.disruptor")
public class DisruptorProperties {


    /**
     * 缓冲区的大小
     */
    private Integer ringBufferSize = 1024 * 1024 ;

    /**
     * 是否支持多生产者
     */
    private boolean isMultiProducer = false ;
}
