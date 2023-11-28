package camellia.service;

import camellia.domain.Privilege;
import camellia.mapper.PrivilegeMapper;
import camellia.mapper.RoleMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import com.gitee.fastmybatis.core.support.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/20 17:50
 */
@Service
public class PrivilegeService extends BaseService<Privilege, Long, PrivilegeMapper> {
    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private RoleMapper roleMapper;

    public List<Privilege> list() {
        return privilegeMapper.listAll();
    }

    public boolean addPrivilege(Privilege privilege) {
        // 是否有相同的值
        if (!ObjectUtils.isEmpty(privilegeMapper.getByQuery(new Query().eq("name", privilege.getName())))) {
            return false;
        }
        return privilegeMapper.addPrivilege(privilege) > 0;
    }

    public boolean deletePrivilege(Long pid) {
        // 是否存在该权限
        if (ObjectUtils.isEmpty(privilegeMapper.getByQuery(new Query().eq("id", pid)))) {
            return false;
        }
        // 是否存在外键的情况
        if (!ObjectUtils.isEmpty(roleMapper.queryRolePrivilegeByPid(pid))) {
            return false;
        }
        return privilegeMapper.deletePrivilege(pid) > 0;
    }
}
