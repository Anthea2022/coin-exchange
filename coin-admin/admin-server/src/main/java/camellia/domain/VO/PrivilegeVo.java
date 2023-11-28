package camellia.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨染盛夏
 * @version 2023/11/22 22:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeVo {
    /**
     * sys_role_privilege的主键, 便于删除
     */
    private Long id;

    private String name;
}
