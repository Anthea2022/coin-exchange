package camellia.service;

import camellia.domain.CoinWithdraw;
import camellia.mapper.CoinWithdrawMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 21:56
 */
@Service
public class CoinWithdrawService extends BaseService<CoinWithdraw, Long, CoinWithdrawMapper> {
    @Autowired
    private CoinWithdrawMapper coinWithdrawMapper;
}
