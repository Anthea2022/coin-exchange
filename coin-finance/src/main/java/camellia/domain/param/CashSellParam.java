package camellia.domain.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 21:44
 */
@Data
@ApiModel(value = "Cash购买时的表单参数")
public class CashSellParam {

    @ApiModelProperty(value = "币种的id")
    @NotNull
    private Long coinId;

    @ApiModelProperty(value = "币种的数量")
    @NotNull
    private BigDecimal num;

    @ApiModelProperty(value = "支付密码")
    @NotBlank
    private String payPassword;

    @ApiModelProperty(value = "验证码")
    @NotBlank
    private String verifyCode;
}
