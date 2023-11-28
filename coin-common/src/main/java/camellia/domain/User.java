package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨染盛夏
 * @version 2023/11/19 21:28
 */
@Data
@Table(name = "sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String username;

    private String status;

    @Column(name = "fullname")
    private String fullName;
}
