package camellia.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.gitee.fastmybatis.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/29 13:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * 自增id
     */
    @ExcelProperty("编号")
    @ApiModelProperty(value = "自增id")
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ExcelIgnore
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @Column(name = "username")
    @ExcelProperty(value = "用户姓名")
    @ApiModelProperty(value = "用户姓名")
    private String username;

    /**
     * 币种id
     */
    @Column(name = "coin_id")
    @ExcelIgnore
    private Long coinId;

    @Column(name = "coin_name")
    @ExcelProperty(value = "币种名")
    @ApiModelProperty(value = "币种名")
    private String coinName;

    /**
     * 账号状态：1，正常；2，冻结；
     */
    @ExcelProperty(value = "账号状态")
    @ApiModelProperty(value = "账号状态：1，正常；2，冻结；")
    private Byte status;

    /**
     * 币种可用金额
     */
    @ExcelProperty(value = "可用金额")
    @Column(name = "balance_amount")
    @ApiModelProperty(value = "币种可用金额")
    private BigDecimal balanceAmount;

    /**
     * 币种冻结金额
     */
    @ExcelProperty(value = "冻结金额")
    @Column(name = "freeze_amount")
    @ApiModelProperty(value = "币种冻结金额")
    private BigDecimal freezeAmount;

    /**
     * 累计充值金额
     */
    @ExcelProperty(value = "充值金额")
    @Column(name = "recharge_amount")
    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 累计提现金额
     */
    @ExcelProperty(value = "提现金额")
    @Column(name = "withdrawals_amount")
    @ApiModelProperty(value = "累计提现金额")
    private BigDecimal withdrawalsAmount;

    /**
     * 净值
     */
    @ExcelProperty(value = "净值")
    @Column(name = "net_value")
    @ApiModelProperty(value = "净值")
    private BigDecimal netValue;

    /**
     * 占用保证金
     */
    @ExcelProperty(value = "占用保证金")
    @Column(name = "lock_margin")
    @ApiModelProperty(value = "占用保证金")
    private BigDecimal lockMargin;

    /**
     * 持仓盈亏/浮动盈亏
     */
    @ExcelProperty(value = "持仓盈亏/浮动盈亏")
    @Column(name = "float_profit")
    @ApiModelProperty(value = "持仓盈亏/浮动盈亏")
    private BigDecimal floatProfit;

    /**
     * 总盈亏
     */
    @ExcelProperty(value = "总盈亏")
    @Column(name = "total_profit")
    @ApiModelProperty(value = "总盈亏")
    private BigDecimal totalProfit;

    /**
     * 充值地址
     */
    @ExcelProperty(value = "充值地址")
    @Column(name = "rec_addr")
    @ApiModelProperty(value = "充值地址")
    private String recAddr;

    /**
     * 版本号
     */
    @ExcelIgnore
    @ApiModelProperty(value = "版本号")
    private Long version;

    /**
     * 更新时间
     */
    @ExcelIgnore
    @Column(name = "last_update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @Column(name = "created")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
