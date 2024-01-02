package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/20 17:02
 */
@Data
@Table(name = "sys_privilege")
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    private Long id;

    private String name;

    private String description;

    private String url;

    private String method;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "modify_by")
    private Long modifyBy;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;

    @Column(name = "last_update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
}
