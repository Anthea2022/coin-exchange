package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 10:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_detail")
public class AccountDetail {
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 币种id
     */
    @Column(name = "coin_id")
    @ApiModelProperty(value = "币种id")
    private Long coinId;

    /**
     * 账户id
     */
    @Column(name = "account_id")
    @ApiModelProperty(value = "账户id")
    private Long accountId;

    /**
     * 该笔流水资金关联方的账户id
     */
    @Column(name = "ref_account_id")
    @ApiModelProperty(value = "该笔流水资金关联方的账户id")
    private Long refAccountId;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 入账为1，出账为2
     */
    @Column(name = "direction")
    @ApiModelProperty(value = "入账为1，出账为2")
    private Byte direction;

    /**
     * 业务类型:
     * 充值(recharge_into)
     * 提现审核通过(withdrawals_out)
     * 下单(order_create)
     * 成交(order_turnover)
     * 成交手续费(order_turnover_poundage)
     * 撤单(order_cancel)
     * 注册奖励(bonus_register)
     * 提币冻结解冻(withdrawals)
     * 充人民币(recharge)
     * 提币手续费(withdrawals_poundage)
     * 兑换(cny_btcx_exchange)
     * 奖励充值(bonus_into)
     * 奖励冻结(bonus_freeze)
     */
    @Column(name = "business_type")
    @ApiModelProperty(value = "业务类型:,充值(recharge_into) ,提现审核通过(withdrawals_out) ,下单(order_create) ,成交(order_turnover),成交手续费(order_turnover_poundage)  ,撤单(order_cancel)  ,注册奖励(bonus_register),提币冻结解冻(withdrawals),充人民币(recharge),提币手续费(withdrawals_poundage)   ,兑换(cny_btcx_exchange),奖励充值(bonus_into),奖励冻结(bonus_freeze)")
    private String businessType;

    /**
     * 资产数量
     */
    @ApiModelProperty(value = "资产数量")
    private BigDecimal amount;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    /**
     * 流水状态：
     * 充值
     * 提现
     * 冻结
     * 解冻
     * 转出
     * 转入
     */
    @ApiModelProperty(value = "流水状态：,充值,提现,冻结,解冻,转出,转入")
    private String remark;

    /**
     * 日期
     */
    @Column(name = "created")
    @ApiModelProperty(value = "日期")
    private Date createTime;

    @ApiModelProperty(value = "用户的名称")
    private String username;

    @ApiModelProperty(value = "用户的真实名称")
    private String realName;

    public AccountDetail(Long accountId, Long userId, Long coinId, Long orderId, BigDecimal num, BigDecimal fee, String remark, String businessType, Byte direction, String realName, String username) {
        this.setAccountId(accountId);
        this.setAmount(num);
        this.setFee(fee);
        this.setCoinId(coinId);
        this.setDirection(direction);
        this.setRefAccountId(refAccountId);
        this.setBusinessType(businessType);
        this.setRemark(remark);
        this.setUserId(userId);
        this.setRealName(realName);
        this.setUsername(username);
        this.setOrderId(orderId);
    }
}