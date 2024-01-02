package camellia.domain.vo;

import com.gitee.fastmybatis.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeVo {
    @ApiModelProperty(value = "收款方户名")
    private String name;

    @ApiModelProperty(value = "收款方开户行")
    private String bankName;

    @ApiModelProperty(value = "收款方账号")
    private String bankCard;

    @ApiModelProperty(value = "转账金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "参考号")
    private String remark;

    @ApiModelProperty(value = "状态")
    private Byte status;
}
