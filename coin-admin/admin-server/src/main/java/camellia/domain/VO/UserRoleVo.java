package camellia.domain.VO;

import camellia.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/22 22:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo {
    private User user;

    private List<RoleVo> roles;
}
