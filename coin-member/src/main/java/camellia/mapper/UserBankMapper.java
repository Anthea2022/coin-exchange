package camellia.mapper;

import camellia.domain.UserBank;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/2 10:53
 */
@Repository
public interface UserBankMapper extends CrudMapper<UserBank, Long> {
}
