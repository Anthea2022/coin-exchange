package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.Role;
import camellia.domain.User;
import camellia.domain.VO.RoleVo;
import camellia.domain.VO.UserRoleVo;
import camellia.exception.AccountException;
import camellia.mapper.RoleMapper;
import camellia.mapper.UserMapper;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author 墨染盛夏
 * @version 2023/11/19 21:27
 */
@Service
public class UserService extends BaseService<User, Long, UserMapper> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     *
     * @param id
     * @return
     */
    public User getInfo(Long id) {
        return userMapper.getById(id);
    }

    /**
     * 给用户添加角色
     * @param uid
     * @param rid
     * @return
     */
    public Boolean addUserRole(Long uid, Long rid) {
        User user = userMapper.queryById(uid);
        if (ObjectUtils.isEmpty(user)) {
            throw new AccountException(ResponseCodes.FAIL, "无此用户");
        }
        if (user.getStatus().equals(0)) {
            throw new AccountException(ResponseCodes.FAIL, "用户状态错误");
        }
        Role role = roleMapper.getById(rid);
        if (ObjectUtils.isEmpty(role)) {
            throw new AccountException(ResponseCodes.FAIL, "无此角色");
        }
        if (role.getStatus().equals(0)) {
            throw new AccountException(ResponseCodes.FAIL, "角色状态错误");
        }
        return userMapper.addUserRole(uid, rid) > 0;
    }

    public Boolean deleteUserRole(Long id) {
        return userMapper.deleteUserRole(id) > 0;
    }

    public List<UserRoleVo> listUserRole(Integer pageSize, Integer pageNum, String fullname, String role) {
        List<UserRoleVo> userRoleVos = new ArrayList<>();
        Integer status = roleMapper.getColumnValue("status", new Query(), Integer.class);
        // 如果角色状态为删除
        if (status.equals(0)) {
            return userRoleVos;
        }
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.like(!StringUtils.isEmpty(fullname), "fullname", fullname);
        PageInfo<User> userPageInfo = userMapper.page(query);
        for (User user : userPageInfo.getList()) {
            List<RoleVo> roleVos = userMapper.getUserRole(user.getId());
            Map<String, Object> map = new HashMap<>();
            for (RoleVo roleVo : roleVos) {
                if (roleVo.getName().equals(role)) {
                    UserRoleVo userRoleVo = new UserRoleVo(user, roleVos);
                    userRoleVos.add(userRoleVo);
                }
            }
        }
        return userRoleVos;
    }

    public Boolean addUser(User user) {
        return userMapper.saveIgnoreNull(user) > 0;
    }

    public Boolean deleteUser(Long uid) {
        if (ObjectUtils.isEmpty(userMapper.getById(uid))) {
            return false;
        }
        return userMapper.deleteById(uid) > 0;
    }
}
