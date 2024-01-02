package camellia.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * @version 2023/12/28 20:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cash_withdrawals")
public class CashWithdraw {
    /**
     * 自增id
     */
    @ExcelProperty(value = "编号")
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

    @Column(name = "real_name")
    @ExcelProperty(value = "用户实名")
    @ApiModelProperty(value = "用户实名")
    private String realName;

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
     * 数量（充值金额）
     */
    @ExcelProperty(value = "充值金额")
    @ApiModelProperty(value = "数量（充值金额）")
    private BigDecimal num;

    /**
     * 手续费
     */
    @ExcelProperty(value = "手续费")
    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    /**
     * 手续费币种
     */
    @ExcelProperty(value = "手续费币种")
    @ApiModelProperty(value = "手续费币种")
    private String feecoin;

    /**
     * 成交量（到账金额）
     */
    @ExcelProperty(value = "成交量")
    @ApiModelProperty(value = "成交量（到账金额）")
    private BigDecimal mum;

    /**
     * 类型：alipay，支付宝；cai1pay，财易付；bank，银联；
     */
    @ExcelProperty(value = "类型")
    @ApiModelProperty(value = "类型：alipay，支付宝；cai1pay，财易付；bank，银联；")
    private String type;

    /**
     * 充值订单号
     */
    @ExcelProperty(value = "充值订单号")
    @ApiModelProperty(value = "充值订单号")
    private String tradeno;

    /**
     * 第三方订单号
     */
    @ExcelProperty(value = "第三方订单号")
    @ApiModelProperty(value = "第三方订单号")
    private String outtradeno;

    /**
     * 充值备注备注
     */
    @ExcelProperty(value = "充值备注")
    @ApiModelProperty(value = "充值备注")
    private String remark;

    /**
     * 审核备注
     */
    @Column(name = "audit_remark")
    @ExcelProperty(value = "审核备注")
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    /**
     * 当前审核级数
     */
    @ExcelProperty(value = "当前审核级数")
    @ApiModelProperty(value = "当前审核级数")
    private Byte step;

    /**
     * 状态：0-待审核；1-审核通过；2-拒绝；3-充值成功；
     */
    @ExcelProperty(value = "状态")
    @ApiModelProperty(value = "状态：0-待审核；1-审核通过；2-拒绝；3-充值成功；")
    private Byte status;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @Column(name = "created")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelIgnore
    @Column(name = "last_update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 银行卡账户名
     */
    @ExcelProperty(value = "银行卡账户名")
    @ApiModelProperty(value = "银行卡账户名")
    private String name;

    /**
     * 开户行
     */
    @Column(name = "bank_name")
    @ExcelProperty(value = "开户行")
    @ApiModelProperty(value = "开户行")
    private String bankName;

    /**
     * 银行卡号
     */
    @Column(name = "bank_card")
    @ExcelProperty(value = "银行卡号")
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;

    /**
     * 最后确认到账时间。
     */
    @Column(name = "last_time")
    @ExcelProperty(value = "最后到账时间")
    @ApiModelProperty(value = "最后确认到账时间")
    private Date lastTime;
}