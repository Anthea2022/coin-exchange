package camellia.rocket;

import camellia.domain.entity.ExchangeTrade;
import camellia.service.EntrustOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 23:56
 */
@Slf4j
@Component
public class ExchangeTradeListener {
    @Autowired
    private EntrustOrderService entrustOrderService;

    @Transactional
    @StreamListener("exchange_trade_in")
    public void receiveExchangeTrade(List<ExchangeTrade> exchangeTrades) {
        if (CollectionUtils.isEmpty(exchangeTrades)) {
            return;
        }
        for (ExchangeTrade exchangeTrade : exchangeTrades) {
            entrustOrderService.doMatch(exchangeTrade);
        }
    }
}
