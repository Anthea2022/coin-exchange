package camellia.domain;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 21:51
 */
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数字货币提现记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coin_withdraw")
public class CoinWithdraw {
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
    @ExcelProperty(value = "用户名")
    @ApiModelProperty(value = "用户名")
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
    @ApiModelProperty(value = "币种id")
    private Long coinId;

    @Column(name = "coin_name")
    @ExcelProperty(value = "币种名")
    @ApiModelProperty(value = "币种名")
    private String coinName;

    /**
     * 币种类型
     */
    @Column(name = "coin_type_id")
    @ExcelIgnore
    @ApiModelProperty(value = "币种类型")
    private Long coinTypeId;

    @Column(name = "coin_type_name")
    @ExcelProperty(value = "币种类型")
    @ApiModelProperty(value = "币种类型")
    private String coinTypeName;

    /**
     * 钱包地址
     */
    @ExcelProperty(value = "钱包地址")
    @ApiModelProperty(value = "钱包地址")
    private String address;

    /**
     * 交易id
     */
    @ExcelIgnore
    @ApiModelProperty(value = "交易id")
    private String txid;

    /**
     * 提现量
     */
    @ExcelProperty(value = "提现量")
    @ApiModelProperty(value = "提现量")
    private BigDecimal num;

    /**
     * 手续费()
     */
    @ExcelProperty(value = "手续费")
    @ApiModelProperty(value = "手续费()")
    private BigDecimal fee;

    /**
     * 实际提现
     */
    @ExcelProperty(value = "实际提现")
    @ApiModelProperty(value = "实际提现")
    private BigDecimal mum;


    /**
     * 链上手续费花费
     */
    @Column(name = "chain_fee")
    @ExcelProperty(value = "链上手续费花费")
    @ApiModelProperty(value = "链上手续费花费")
    private BigDecimal chainFee;

    /**
     * 区块高度
     */
    @Column(name = "block_num")
    @ExcelProperty(value = "区块高度")
    @ApiModelProperty(value = "区块高度")
    private Integer blockNum;

    /**
     * 后台审核人员提币备注备注
     */
    @ExcelProperty(value = "审核备注")
    @ApiModelProperty(value = "后台审核人员提币备注备注")
    private String remark;

    /**
     * 钱包提币备注备注
     */
    @Column(name = "wallet_mark")
    @ExcelProperty(value = "钱包提币备注")
    @ApiModelProperty(value = "钱包提币备注备注")
    private String walletMark;

    /**
     * 当前审核级数
     */
    @ExcelProperty(value = "审核级别")
    @ApiModelProperty(value = "当前审核级数")
    private Byte step;

    /**
     * 状态：0-审核中；1-成功；2-拒绝；3-撤销；4-审核通过；5-打币中；
     */
    @ExcelProperty(value = "状态")
    @ApiModelProperty(value = "状态：0-审核中；1-成功；2-拒绝；3-撤销；4-审核通过；5-打币中；")
    private Boolean status;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    @ExcelIgnore
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    @ExcelIgnore
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ExcelIgnore
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}