package camellia.match;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 14:27
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.match")
public class MatchProperties {
    private Map<String, CoinScale> symbol;

    @Data
    public static class CoinScale{
        private int coinScale;

        private int baseCoinScale;
    }
}
