package camellia.service.impl;

import camellia.domain.UserAuthAuditRecord;
import camellia.mapper.UserAuthAuditRecordMapper;
import camellia.mapper.UserInfoMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 0:04
 */
@Service
public class UserAuthAuditRecordService extends BaseService<UserAuthAuditRecord, Long, UserAuthAuditRecordMapper> {
    @Autowired
    private UserAuthAuditRecordMapper userAuthAuditRecordMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional
    public Boolean checkAuth(UserAuthAuditRecord userAuthAuditRecord) {
        //调用admin-server获取管理员项目;
//        userAuthAuditRecord.setAuditUserName(auditName);
        return userAuthAuditRecordMapper.saveIgnoreNull(userAuthAuditRecord) > 0;
    }
}
