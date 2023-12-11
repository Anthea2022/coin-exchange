package camellia.mapper;

import camellia.domain.UserAuthInfo;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/10 11:34
 */
@Repository
public interface UserAuthInfoMapper extends CrudMapper<UserAuthInfo, Long> {
}
