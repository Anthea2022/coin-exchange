package camellia.match;

import camellia.disruptor.OrderEvent;
import camellia.disruptor.OrderEventHandler;
import camellia.domain.model.OrderBook;
import com.lmax.disruptor.EventHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 14:29
 */
@Configuration
@EnableConfigurationProperties(value = MatchConfiguration.class)
public class MatchConfiguration {
    private MatchProperties matchProperties;

    public MatchConfiguration(MatchProperties matchProperties) {
        this.matchProperties = matchProperties;
    }

    @Bean("eventHandlers")
    public EventHandler<OrderEvent>[] eventHandlers() {
        Map<String, MatchProperties.CoinScale> symbols = matchProperties.getSymbol();
        Set<Map.Entry<String, MatchProperties.CoinScale>> entries = symbols.entrySet();
        EventHandler<OrderEvent>[] eventHandlers = new EventHandler[symbols.size()];
        int i = 0;
        for (Map.Entry<String, MatchProperties.CoinScale> entry : entries) {
            String symbol = entry.getKey();
            MatchProperties.CoinScale value = entry.getValue();
            OrderBook orderBook = null;
            if (ObjectUtils.isEmpty(value)) {
                orderBook = new OrderBook(symbol);
            } else {
                orderBook = new OrderBook(symbol, value.getCoinScale(), value.getBaseCoinScale());
            }
            eventHandlers[i++] = new OrderEventHandler(orderBook);
        }
        return eventHandlers;
    }
}
