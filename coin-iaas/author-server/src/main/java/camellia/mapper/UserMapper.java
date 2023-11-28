package camellia.mapper;

import camellia.domain.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author anthea
 * @date 2023/11/17 14:03
 */
@Mapper
public interface UserMapper {
    User getByUsername(String username);

    User getByEmail(String email);

    String getUsername(Long id);

    String getEmail(Long id);
}
