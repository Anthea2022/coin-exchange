package camellia.mapper;

import camellia.domain.CashWithdrawAudit;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/31 18:09
 */
@Repository
public interface CashWithdrawAuditMapper extends CrudMapper<CashWithdrawAudit, Long> {
}
