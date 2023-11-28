package camellia.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2023/11/19 23:44
 */
@Configuration
@MapperScan("camellia.mapper")
public class MybatisConfig {
}
