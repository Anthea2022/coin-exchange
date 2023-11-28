package camellia.mapper;

import camellia.domain.Role;
import camellia.domain.VO.PrivilegeVo;
import camellia.domain.VO.RoleVo;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 0:18
 */
@Repository
@Mapper
public interface RoleMapper extends CrudMapper<Role, Long> {
    /**
     * 给角色授权
     * @param rid
     * @param pid
     * @return
     */
    Integer saveRolePrivilege(@Param("rid") Long rid, @Param("pid") Long pid);

    /**
     * 取消授权
     * @param id
     * @return
     */
    Long getRolePrivilegeById(Long id);

    List<PrivilegeVo> listRolePrivilege(Long rid);

    @Select("select id,name from sys_role where status = 1")
    List<RoleVo> getRoles();

    Integer deleteRolePrivilege(Long id);

    Long queryRolePrivilegeByPid(Long pid);
}
