package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.Coin;
import camellia.exception.BusinessException;
import camellia.mapper.CoinMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author 墨染盛夏
 * @version 2023/12/27 19:43
 */
@Service
public class CoinService extends BaseService<Coin, Long, CoinMapper> {
    @Autowired
    private CoinMapper coinMapper;

    public Boolean saveCoin(Coin coin) {
        // TODO: 2023/12/27 约束
        return coinMapper.saveIgnoreNull(coin) > 0;
    }

    public Boolean setStatus(Coin coin) {
        String name = coinMapper.getColumnValue("name", new Query().eq("id", coin.getId()), String.class);
        if (ObjectUtils.isEmpty(name)) {
            throw new BusinessException(ResponseCodes.FAIL, "无该币种");
        }
        return coinMapper.updateIgnoreNull(coin) > 0;
    }
}
