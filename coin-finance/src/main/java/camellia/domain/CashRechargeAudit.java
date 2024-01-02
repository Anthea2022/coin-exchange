package camellia.domain;

import camellia.domain.param.Audit;
import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author 墨染盛夏
 * @version 2023/12/31 17:45
 */
@Table(name = "cash_recharge_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRechargeAudit {
    private Long id;

    @ApiModelProperty(value = "状态")
    @NotNull
    private Byte status;

    @ApiModelProperty(value = "备注")
    @NotBlank
    private String remark;

    @ApiModelProperty(value = "级别")
    private Byte step;

    @Column(name = "audit_user_id")
    @ApiModelProperty("审核人编号")
    private Long auditUid;

    @Column(name = "audit_user_name")
    @ApiModelProperty(value = "审核人姓名")
    private String auditUsername;

    @Column(name = "created")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单编号")
    private Long orderId;
}
