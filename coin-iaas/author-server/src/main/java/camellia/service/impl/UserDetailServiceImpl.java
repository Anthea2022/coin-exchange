package camellia.service.impl;

import camellia.domain.Member;
import camellia.mapper.MemberMapper;
import camellia.mapper.PermissionMapper;
import camellia.mapper.UserMapper;
import com.gitee.fastmybatis.core.query.Query;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static camellia.constant.UserLoginConstant.*;

/**
 * @author anthea
 * @version  2023/10/28 23:41
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为空");
        }
        UserDetails userDetails;
        try {
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (REFRESH_TYPE.equals(grantType.toLowerCase())) {
                s = adjustName(s, loginType);
            }
            switch (loginType) {
                case ADMIN_TYPE:
                    userDetails = loadAdmin(s);
                    break;
                case USER_TYPE:
                    userDetails =loadUser(s);
                    break;
                default:
                    throw new AuthenticationServiceException("登录类型错误");
            }
        } catch (AuthenticationServiceException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }

    private String adjustName(String username, String loginType) {
        if (ADMIN_TYPE.equals(loginType)) {
            return userMapper.getUsername(Long.parseLong(username));
        }
        if (USER_TYPE.equals(loginType)) {
            return memberMapper.getEmailById(Long.parseLong(username));
        }
        return username;
    }

    private UserDetails loadUser(String username) {
        // 查询用户、角色、权限
        Member member = memberMapper.getLoginInfo(username);
        if (ObjectUtils.isEmpty(member)) {
            return null;
        }
        Long id = member.getId();
        return new User(
                id.toString(),
                member.getPassword(),
                member.getStatus() == 1,
                true,
                true,
                true,
                getAuthorities(id)
        );
    }

    private UserDetails loadAdmin(String username) {
        // 查询管理员、角色、权限
        camellia.domain.User user = userMapper.getByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        Long id = user.getId();
        return new User(
                id.toString(),
                user.getPassword(),
                user.isStatus(),
                true,
                true,
                true,
                getAuthorities(id)
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(long id) {
        List<String> permissions = permissionMapper.getPermissions(id);
        if (ObjectUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }
        return permissions.stream()
                .distinct()
                .map(perm -> new SimpleGrantedAuthority(perm))
                .collect(Collectors.toSet());
    }


}
