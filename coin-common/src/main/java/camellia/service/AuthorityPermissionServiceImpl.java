package camellia.service;

import camellia.mapper.PrivilegeMapper;
import camellia.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/25 11:34
 */
@Service("coin")
public class AuthorityPermissionServiceImpl {
    @Autowired
    private PrivilegeMapper privilegeMapper;

    public boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        Long uid = TokenUtil.getUid();
        if (uid == null) {
            return false;
        }
        List<String> permissions = privilegeMapper.getPermissions(uid);
        if (!permissions.isEmpty() && permissions.contains(permission)) {
            return true;
        }
        return false;
    }

//    public boolean hasPermissions(String ... permissions) {
//        for (String permission : permissions) {
//            if (!hasPermission(permission)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
