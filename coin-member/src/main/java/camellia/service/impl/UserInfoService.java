package camellia.service.impl;

import camellia.common.ResponseCodes;
import camellia.domain.UserInfo;
import camellia.exception.BusinessException;
import camellia.mapper.UserInfoMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static camellia.constant.CodeTypeConstant.UPDATE_PSW_CODE;


/**
 * @author 墨染盛夏
 * @version 2023/12/9 1:36
 */
@Service
public class UserInfoService extends BaseService<UserInfo, Long, UserInfoMapper> {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean updateInfo(UserInfo userInfo) {
        return userInfoMapper.updateIgnoreNull(userInfo) > 0;
    }

    public UserInfo getInfo(List<String> list) {
        Long uid = TokenUtil.getUid();
        return userInfoMapper.getBySpecifiedColumns(list, new Query().eq("id", uid));
    }

    public Boolean updatePayPsw(String newPayPsw) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(TokenUtil.getUid());
        userInfo.setPaypassword(newPayPsw);
        return userInfoMapper.updateIgnoreNull(userInfo) > 0;
    }

    public Boolean updatePsw(UserInfo userInfo) {
        return userInfoMapper.updateIgnoreNull(userInfo) > 0;
    }

    public Boolean matchPsw(String oldPsw) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Long uid = TokenUtil.getUid();
        String password = userInfoMapper.getColumnValue("password", new Query().eq("id", uid), String.class);
        return bCryptPasswordEncoder.matches(oldPsw, password);
    }

    public Boolean saveUser(UserInfo userInfo) {
        return userInfoMapper.saveIgnoreNull(userInfo) > 0;
    }
}
