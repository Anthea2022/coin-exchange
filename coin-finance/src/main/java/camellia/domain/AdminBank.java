package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 20:49
 * 银行卡
 */
@Table(name = "admin_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBank {
    private Long id;

    @Column(name = "holder_id")
    private Long holderId;

    @Column(name = "holder_name")
    @ApiModelProperty(value = "收款人姓名")
    private String holderName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_card")
    private String bankCard;

    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name")
    private String coinName;

    private Byte status;
}
