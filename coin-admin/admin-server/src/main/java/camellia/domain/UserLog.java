package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/26 23:34
 */
@Data
@Table(name = "sys_user_log")
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {
    private Long id;

    private String group;

    @Column(name = "user_id")
    private Long uid;

    private short type;

    private String method;

    private String params;

    private Long time;

    private String ip;

    private String description;

    private String remark;

    @Column(name = "created")
    private Date createTime;
}
