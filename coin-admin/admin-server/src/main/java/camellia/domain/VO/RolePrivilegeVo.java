package camellia.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 21:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePrivilegeVo {
    private Long id;

    private String name;

    private List<PrivilegeVo> privileges;
}
