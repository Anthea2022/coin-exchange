package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 20:50
 */
@Data
@Table(name = "work_issue")
@NoArgsConstructor
@AllArgsConstructor
public class WorkIssue {
    private Long id;

    @Column(name = "user_id")
    private Long uid;

    @Column(name = "answer_user_id")
    private Long answerUid;

    @Column(name = "answer_name")
    private String answerName;

    private String question;

    private String answer;

    private Integer status;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
