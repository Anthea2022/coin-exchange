package camellia.mapper;

import camellia.domain.User;
import camellia.domain.VO.RoleVo;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/19 21:30
 */
@Mapper
public interface UserMapper extends CrudMapper<User, Long> {
    User queryById(Long id);

    Integer addUserRole(@Param("uid") Long uid, @Param("rid") Long rid);

    List<RoleVo> getUserRole(Long uid);

    Integer deleteUserRole(Long id);

    Long queryUserRoleByRid(Long rid);
}
