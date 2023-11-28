package camellia.service;

import camellia.model.WebLog;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/11/19 18:01
 */
@Service
public class TestService {
    @Cached(name = "camellia.service.TestService:", key = "#username", cacheType = CacheType.BOTH)
    public WebLog get(String username) {
        WebLog webLog = new WebLog();
        webLog.setUsername(username);
        webLog.setResult("ok");
        return webLog;
    }
}
