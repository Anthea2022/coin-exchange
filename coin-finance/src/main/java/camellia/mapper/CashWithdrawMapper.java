package camellia.mapper;

import camellia.domain.CashWithdraw;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 20:08
 */
@Repository
public interface CashWithdrawMapper extends CrudMapper<CashWithdraw, Long> {
}
