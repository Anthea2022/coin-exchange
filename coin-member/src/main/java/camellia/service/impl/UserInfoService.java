package camellia.service.impl;

import camellia.domain.UserAuthAuditRecord;
import camellia.domain.UserAuthInfo;
import camellia.domain.UserInfo;
import camellia.domain.vo.UserAuthInfoVo;
import camellia.mapper.UserAuthAuditRecordMapper;
import camellia.mapper.UserAuthInfoMapper;
import camellia.mapper.UserInfoMapper;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import com.gitee.fastmybatis.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static camellia.constant.DBConstant.USER_INFO;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 1:36
 */
@Service
public class UserInfoService extends BaseService<UserInfo, Long, UserInfoMapper> {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAuthInfoMapper userAuthInfoMapper;

    @Autowired
    private UserAuthAuditRecordMapper userAuthAuditRecordMapper;

    public Boolean updateInfo(UserInfo userInfo) {
        return userInfoMapper.updateIgnoreNull(userInfo) > 0;
    }

    public Boolean setReviewStatus(UserInfo userInfo) {
        if (ObjectUtils.isEmpty(userInfoMapper.getColumnValue("id", new Query().eq("id", userInfo.getId()), Long.class))) {
            return false;
        }
        return userInfoMapper.updateIgnoreNull(userInfo) > 0;
    }
}
