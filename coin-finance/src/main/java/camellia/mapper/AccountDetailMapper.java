package camellia.mapper;

import camellia.domain.AccountDetail;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 10:55
 */
@Repository
public interface AccountDetailMapper extends CrudMapper<AccountDetail, Long> {
}
