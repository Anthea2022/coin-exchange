package camellia.service.impl;

import camellia.domain.UserAuthInfo;
import camellia.mapper.UserAuthInfoMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 0:03
 */
@Service
public class UserAuthInfoService extends BaseService<UserAuthInfo, Long, UserAuthInfoMapper> {
}
