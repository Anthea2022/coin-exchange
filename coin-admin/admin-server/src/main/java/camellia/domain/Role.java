package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 0:12
 */
@Data
@Table(name = "sys_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long id;

    private String name;

    private String description;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "modify_by")
    private Long modifyBy;

    private Integer status;

    private Date created;

    @Column(name = "last_update_time")
    private Date updateTime;
}
