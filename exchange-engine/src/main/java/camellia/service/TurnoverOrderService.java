package camellia.service;

import camellia.domain.entity.TurnoverOrder;
import camellia.mapper.TurnoverOrderMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 21:44
 */
@Service
public class TurnoverOrderService extends BaseService<TurnoverOrder, Long, TurnoverOrderMapper> {
    @Autowired
    private TurnoverOrderMapper turnoverOrderMapper;
}
