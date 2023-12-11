package camellia.mapper;

import camellia.domain.UserInfo;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 1:43
 */
@Repository
public interface UserInfoMapper extends CrudMapper<UserInfo, Long> {
}
