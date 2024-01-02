package camellia.mapper;

import camellia.domain.Account;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/29 13:29
 */
@Repository
public interface AccountMapper extends CrudMapper<Account, Long> {
}
