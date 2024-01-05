package camellia.service;

import camellia.domain.model.Order;
import camellia.domain.model.OrderBook;
import camellia.factory.MatchServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 14:53
 */

/**
 * sell价低者优先
 * buy价高者优先
 * 相同创建时间早优先
 */
@Slf4j
@Service
public class MatchService implements InitializingBean {
    /**
     * 撮合交易
     * @param order
     */
    public void match(OrderBook orderBook, Order order) {
        log.info("开始撮合订单");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService("", this);
    }
}
