package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 0:06
 */
@Data
@Table(name = "notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Long id;

    private String title;

    private String description;

    private String author;

    private Integer status;

    private String sort;

    private String content;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
