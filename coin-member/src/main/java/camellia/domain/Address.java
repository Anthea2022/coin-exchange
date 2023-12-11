package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/8 0:17
 */
@Data
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;

    @Column(name = "user_id")
    private Long uid;

    @Column(name = "coin_id")
    private Long cid;

    private String address;

    private String keystore;

    private String pwd;

    @Column(name = "markid")
    private Long mid;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
