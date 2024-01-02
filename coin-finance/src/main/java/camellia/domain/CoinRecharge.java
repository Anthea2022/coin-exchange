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
 * 数字货币充值记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coin_recharge")
public class CoinRecharge {
    /**
     * 自增id
     */
    @ExcelProperty(value = "编号")
    @ApiModelProperty(value = "自增id")
    private Long id;

    /**
     * 用户id
     */
    @Column(name= "user_id")
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
     * 币种类型
     */
    @Column(name = "coin_type_id")
    @ExcelIgnore
    @ApiModelProperty(value = "币种类型")
    private Long coinTypeId;

    @Column(name = "coin_type_name")
    @ExcelProperty(value = "币种类型名")
    @ApiModelProperty(value = "币种类型名")
    private String coinTypeName;

    /**
     * 钱包地址
     */
    @ExcelProperty(value = "钱包地址")
    @ApiModelProperty(value = "钱包地址")
    private String address;

    /**
     * 充值确认数
     */
    @ExcelProperty(value = "充值确认数")
    @ApiModelProperty(value = "充值确认数")
    private Integer confirm;

    /**
     * 状态：0-待入帐；1-充值失败，2到账失败，3到账成功；
     */
    @ExcelProperty(value = "状态")
    @ApiModelProperty(value = "状态：0-待入帐；1-充值失败，2到账失败，3到账成功；")
    private Integer status;

    /**
     * 交易id
     */
    @ExcelIgnore
    @ApiModelProperty(value = "交易id")
    private String txid;

    @ExcelProperty(value = "amount")
    private BigDecimal amount;

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