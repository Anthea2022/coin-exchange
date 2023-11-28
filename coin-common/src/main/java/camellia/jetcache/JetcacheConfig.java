package camellia.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨染盛夏
 * @version 2023/11/18 13:52
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "camellia.service.impl")
public class JetcacheConfig {

}
