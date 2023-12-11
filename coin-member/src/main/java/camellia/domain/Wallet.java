package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 0:22
 */
@Data
@Table(name = "user_wallet")
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private Long id;

    @Column(name = "user_id")
    private Long uid;

    @Column(name = "coin_id")
    private Long cid;

    @Column(name = "coin_name")
    private String coinName;

    private String name;

    private String addr;

    private Integer sort;

    private Integer status;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
