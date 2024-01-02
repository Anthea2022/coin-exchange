package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/27 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coin")
public class Coin {
    /**
     * 币种ID
     */
    @ApiModelProperty(value = "币种ID")
    private Long id;

    /**
     * 币种名称
     */
    @ApiModelProperty(value = "币种名称")
    @NotBlank
    private String name;

    /**
     * 币种标题
     */
    @ApiModelProperty(value = "币种标题")
    private String title;


    /**
     * xnb：人民币
     * default：比特币系列
     * ETH：以太坊
     * ethToken：以太坊代币
     */
    @ApiModelProperty(value = "xnb：人民币,default：比特币系列,ETH：以太坊,ethToken：以太坊代币,,")
    @NotNull
    private Long typeId;

    /**
     * rgb：认购币
     * qbb：钱包币
     */
    @ApiModelProperty(value = "rgb：认购币,qbb：钱包币,")
    @NotBlank
    private String wallet;

    /**
     * 小数位数
     */
    @ApiModelProperty(value = "小数位数")
    @NotNull
    private Byte round;

    /**
     * 最小提现单位
     */
    @Column(name = "base_amount")
    @ApiModelProperty(value = "最小提现单位")
    @NotNull
    private BigDecimal baseAmount;

    /**
     * 单笔最小提现数量
     */
    @Column(name = "min_amount")
    @ApiModelProperty(value = "单笔最小提现数量")
    @NotNull
    private BigDecimal minAmount;

    /**
     * 单笔最大提现数量
     */
    @Column(name = "max_amount")
    @ApiModelProperty(value = "单笔最大提现数量")
    @NotNull
    private BigDecimal maxAmount;

    /**
     * 当日最大提现数量
     */
    @Column(name = "day_max_amount")
    @ApiModelProperty(value = "当日最大提现数量")
    @NotNull
    private BigDecimal dayMaxAmount;

    /**
     * status=1：启用
     * 0：禁用
     */
    @ApiModelProperty(value = "status=1：启用,0：禁用")
    private Byte status;

    /**
     * 自动转出数量
     */
    @Column(name = "auto_out")
    @ApiModelProperty(value = "自动转出数量")
    private Double autoOut;

    /**
     * 手续费率
     */
    @Column(name = "rate")
    @ApiModelProperty(value = "手续费率")
    @NotNull
    private Double rate;

    /**
     * 最低收取手续费个数
     */
    @Column(name = "min_fee_num")
    @ApiModelProperty(value = "最低收取手续费个数")
    @NotNull
    private BigDecimal minFeeNum;

    /**
     * 提现开关
     */
    @Column(name = "withdraw_flag")
    @ApiModelProperty(value = "提现开关")
    @NotNull
    private Byte withdrawFlag;

    /**
     * 充值开关
     */
    @Column(name = "recharge_flag")
    @ApiModelProperty(value = "充值开关")
    @NotNull
    private Byte rechargeFlag;

    /**
     * 更新时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Coin(CoinParam coinParam) {
        this.id = coinParam.getId();
        this.name = coinParam.getName();
        this.rate = coinParam.getRate();
        this.round = coinParam.getRound();
        this.title = coinParam.getTitle();
        this.typeId = coinParam.getTypeId();
        this.wallet = coinParam.getWallet();
        this.autoOut = coinParam.getAutoOut();
        this.maxAmount = coinParam.getMaxAmount();
        this.minAmount = coinParam.getMinAmount();
        this.minFeeNum = coinParam.getMinFeeNum();
        this.updateTime = coinParam.getUpdateTime();
        this.createTime = coinParam.getCreateTime();
        this.baseAmount = coinParam.getBaseAmount();
        this.dayMaxAmount = coinParam.getDayMaxAmount();
        this.rechargeFlag = coinParam.getRechargeFlag();
        this.withdrawFlag = coinParam.getWithdrawFlag();
    }
}