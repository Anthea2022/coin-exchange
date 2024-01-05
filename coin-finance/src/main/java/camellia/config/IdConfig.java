package camellia.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 11:30
 */
@Configuration
public class IdConfig {
    @Bean
    public Snowflake snowflake() {
        return new Snowflake(0, 0);
    }
}
