package camellia.service;

import camellia.domain.Role;
import camellia.domain.VO.PrivilegeVo;
import camellia.domain.VO.RolePrivilegeVo;
import camellia.domain.VO.RoleVo;
import camellia.mapper.PrivilegeMapper;
import camellia.mapper.RoleMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 0:12
 */
@Service
public class RoleService extends BaseService<Role, Long, RoleMapper> {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    /**
     * 给角色授权
     * @param rid roleId
     * @param pid privilegeId
     * @return Boolean
     */
    public Boolean grantPrivilege(Long rid, Long pid) {
        //不存在该角色
        Role role = roleMapper.getByQuery(new Query().eq("id", rid));
        if (ObjectUtils.isEmpty(role)) {
            return false;
        }
        if (role.getStatus().equals(0)) {
            return false;
        }
        // 不存在该权限
        if (ObjectUtils.isEmpty(privilegeMapper.getByQuery(new Query().eq("id", pid)))) {
            System.out.println(2);
            return false;
        }
        return roleMapper.saveRolePrivilege(rid, pid) > 0;
    }

    /**
     * 取消权限
     * @param id
     * @return
     */
    public Boolean cancelPrivilege(Long id) {
        System.out.println(4);
        // 如果不存在该数据
        if (ObjectUtils.isEmpty(roleMapper.getRolePrivilegeById(id))) {
            return false;
        }
        return roleMapper.deleteRolePrivilege(id) > 0;
    }

    /**
     * 获取所有管理员对应的权限
     * @return
     */
    public List<RolePrivilegeVo> listRolePrivilege() {
        List<RoleVo> roleVos = roleMapper.getRoles();
        List<RolePrivilegeVo> rolePrivilegeVos = new ArrayList<>();
        for (RoleVo roleVo : roleVos) {
            List<PrivilegeVo> privileges = roleMapper.listRolePrivilege(roleVo.getId());
            RolePrivilegeVo rolePrivilegeVo = new RolePrivilegeVo(roleVo.getId(),roleVo.getName(), privileges);
            rolePrivilegeVos.add(rolePrivilegeVo);
        }
        return rolePrivilegeVos;
    }

    /**
     * 将角色的状态改为0
     * @param role
     * @return
     */
    public Boolean deleteRole(Role role) {
        return roleMapper.updateIgnoreNull(role) > 0;
    }
}
