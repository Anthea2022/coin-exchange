package camellia.mapper;

import camellia.domain.UserLog;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/11/26 23:50
 */
@Repository
public interface UserLogMapper extends CrudMapper<UserLog, Long> {
}
