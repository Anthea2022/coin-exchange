package camellia.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 23:54
 */
public interface Sink {
    @Input("exchange_trade_in")
    MessageChannel exchangeTradeIn();
}
