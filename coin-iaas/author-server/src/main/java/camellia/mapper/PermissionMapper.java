package camellia.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/17 14:29
 */
@Mapper
public interface PermissionMapper {
    List<String> getPermissions(Long uid);
}
