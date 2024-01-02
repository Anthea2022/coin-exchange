package camellia.service.impl;

import camellia.common.ResponseCodes;
import camellia.domain.UserAuthInfo;
import camellia.domain.vo.SeniorAuthImgVo;
import camellia.exception.BusinessException;
import camellia.mapper.UserAuthInfoMapper;
import camellia.util.ImgUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 0:03
 */
@Service
public class UserAuthInfoService extends BaseService<UserAuthInfo, Long, UserAuthInfoMapper> {
    @Autowired
    private UserAuthInfoMapper userAuthInfoMapper;

    public Boolean saveSeniorAuth(MultipartFile file, Long uid, Integer serialno, Long authCode) {
        // 如果有记录
        if (StringUtils.hasText(userAuthInfoMapper.getColumnValue("iamge_url",
                new Query().eq("user_id", uid).eq("serialno", serialno), String.class))) {
            return false;
        }// todo set auth_code
        UserAuthInfo userAuthInfo = new UserAuthInfo();
        userAuthInfo.setUserId(uid);
        userAuthInfo.setImageUrl(ImgUtil.storeImg(file));
        userAuthInfo.setSerialno(serialno);
        userAuthInfo.setCreateTime(new Date());
        userAuthInfo.setAuthCode(authCode);
        userAuthInfoMapper.saveIgnoreNull(userAuthInfo);
        return true;
    }

    /**
     * 获取最新的高级认证authCode
     * @param uid
     * @return
     */
    public String getLatestAuthCode(Long uid) {
        String authCode = userAuthInfoMapper.getColumnValue("auth_code", new Query().eq("user_id", uid), String.class);
        if (StringUtils.isEmpty(authCode)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "请先进行高级认证");
        }
        return authCode;
    }
}
