package camellia.mapper;

import camellia.domain.CoinWithdraw;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 21:55
 */
@Repository
public interface CoinWithdrawMapper extends CrudMapper<CoinWithdraw, Long> {
}
