package camellia.mapper;

import camellia.domain.Privilege;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/20 17:05
 */
@Mapper
public interface PrivilegeMapper extends CrudMapper<Privilege, Long> {
    List<Privilege> listAll();

    int addPrivilege(Privilege privilege);

    int deletePrivilege(Long pid);

    List<String> getPermissions(Long uid);
}
