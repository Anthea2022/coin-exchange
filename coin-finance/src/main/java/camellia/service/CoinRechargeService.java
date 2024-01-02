package camellia.service;

import camellia.domain.CoinRecharge;
import camellia.mapper.CoinRechargeMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 21:21
 */
@Service
public class CoinRechargeService extends BaseService<CoinRecharge, Long, CoinRechargeMapper> {
    @Autowired
    private CoinRechargeMapper coinRechargeMapper;
}
