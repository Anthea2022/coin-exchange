package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/2 10:09
 */
@Data
@Table(name = "user_bank")
@NoArgsConstructor
@AllArgsConstructor
public class UserBank {
    private Long id;

    @Column(name = "user_id")
    private Long uid;

    /**
     * 银行卡类别
     */
    private String remark;

    @Column(name = "real_name")
    private String realName;

    private String bank;

    @Column(name = "bank_prov")
    private String bankProv;

    @Column(name = "bank_city")
    private String bankCity;

    @Column(name = "bank_addr")
    private String bankAddr;

    @Column(name = "bank_card")
    private String bankCard;

    private Integer status;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
