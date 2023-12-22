package camellia.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2023/12/16 0:16
 */
@Configuration
public class IdGenConfig {
    @Value("${id.machine:0}")
    private int machineCode;

    @Value("${id.app:0}")
    private int appCode;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(machineCode, appCode);
    }
}
