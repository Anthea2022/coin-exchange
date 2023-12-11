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


    public UserAuthInfoVo getUserAuthInfo(Long uid) {
        UserAuthInfoVo userAuthInfoVo = null;
        List<UserAuthInfo> userAuthInfos = null;
        List<UserAuthAuditRecord> userAuthAuditRecords = null;
        UserInfo userInfo = userInfoMapper.getBySpecifiedColumns(USER_INFO, new Query().eq("id", uid));

        if (ObjectUtils.isEmpty(userInfo)) {
            return userAuthInfoVo;
        }
        if (userInfo.getReviewsStatus() == null || userInfo.getReviewsStatus() == 0) {
            userAuthInfoVo = new UserAuthInfoVo(userInfo, null, null);
            return userAuthInfoVo;
        }
        userAuthInfos = userAuthInfoMapper.list(new Query().eq("user_id", uid));
        UserAuthInfo userAuthInfo = userAuthInfos.get(0);
        UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecordMapper.getByQuery(new Query().eq("auth_code", userAuthInfo.getAuthCode()));
        userAuthInfoVo = new UserAuthInfoVo(userInfo, userAuthInfo, userAuthAuditRecord);
        return userAuthInfoVo;
    }
}
