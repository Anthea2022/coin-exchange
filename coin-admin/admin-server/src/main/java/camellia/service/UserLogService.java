package camellia.service;

import camellia.domain.UserLog;
import camellia.mapper.UserLogMapper;
import camellia.mapper.UserMapper;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 墨染盛夏
 * @version 2023/11/26 23:51
 */
@Service
public class UserLogService extends BaseService<UserLog, Long, UserLogMapper> {
    @Autowired
    private UserLogMapper userLogMapper;

    @Autowired
    private UserMapper userMapper;

    public Boolean saveUserLog(UserLog userLog) {
        return userLogMapper.saveIgnoreNull(userLog) > 0;
    }

    public PageInfo<UserLog> listPage(Query query, String username) {
        if (!StringUtils.isEmpty(username)) {
            query.eq("user_id", userMapper.getColumnValue("id", new Query().eq("username", username), Long.class));
        }
        return userLogMapper.page(query);
    }
}
