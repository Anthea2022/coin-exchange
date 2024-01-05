package camellia.domain.entity;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.math.BigDecimal;
import java.util.Date;

/**
* 委托订单信息
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entrust_order")
public class EntrustOrder {
    /**
     * 订单ID
     */
    @ApiModelProperty(value="订单ID")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 市场ID
     */
    @Column(name = "market_id")
    @ApiModelProperty(value="市场ID")
    private Long marketId;

    /**
     * 市场类型（1：币币交易，2：创新交易）
     */
    @Column(name = "market_type")
    @ApiModelProperty(value="市场类型（1：币币交易，2：创新交易）")
    private Byte marketType;

    /**
     * 交易对标识符
     */
    @Column(name = "symbol")
    @ApiModelProperty(value="交易对标识符")
    private String symbol;

    /**
     * 交易市场
     */
    @Column(name = "market_name")
    @ApiModelProperty(value="交易市场")
    private String marketName;

    /**
     * 委托价格
     */
    @Column(name = "price")
    @ApiModelProperty(value="委托价格")
    private BigDecimal price;

    /**
     * 合并深度价格1
     */
    @Column(name = "merge_low_price")
    @ApiModelProperty(value="合并深度价格1")
    private BigDecimal mergeLowPrice;

    /**
     * 合并深度价格2
     */
    @Column(name = "merge_high_price")
    @ApiModelProperty(value="合并深度价格2")
    private BigDecimal mergeHighPrice;

    /**
     * 委托数量
     */
    @Column(name = "volume")
    @ApiModelProperty(value="委托数量")
    private BigDecimal volume;

    /**
     * 委托总额
     */
    @Column(name = "amount")
    @ApiModelProperty(value="委托总额")
    private BigDecimal amount;

    /**
     * 手续费比率
     */
    @Column(name = "fee_rate")
    @ApiModelProperty(value="手续费比率")
    private BigDecimal feeRate;

    /**
     * 交易手续费
     */
    @Column(name = "fee")
    @ApiModelProperty(value="交易手续费")
    private BigDecimal fee;

    /**
     * 合约单位
     */
    @Column(name = "contract_unit")
    @ApiModelProperty(value="合约单位")
    private Integer contractUnit;

    /**
     * 成交数量
     */
    @Column(name = "deal")
    @ApiModelProperty(value="成交数量")
    private BigDecimal deal;

    /**
     * 冻结量
     */
    @Column(name = "freeze")
    @ApiModelProperty(value="冻结量")
    private BigDecimal freeze;

    /**
     * 保证金比例
     */
    @Column(name = "margin_rate")
    @ApiModelProperty(value="保证金比例")
    private BigDecimal marginRate;

    /**
     * 基础货币对（USDT/BTC）兑换率
     */
    @Column(name = "base_coin_rate")
    @ApiModelProperty(value="基础货币对（USDT/BTC）兑换率")
    private BigDecimal baseCoinRate;

    /**
     * 报价货币对（USDT/BTC)兑换率
     */
    @Column(name = "price_coin_rate")
    @ApiModelProperty(value="报价货币对（USDT/BTC)兑换率")
    private BigDecimal priceCoinRate;

    /**
     * 占用保证金
     */
    @Column(name = "lock_margin")
    @ApiModelProperty(value="占用保证金")
    private BigDecimal lockMargin;

    /**
     * 价格类型：1-市价；2-限价
     */
    @Column(name = "price_type")
    @ApiModelProperty(value="价格类型：1-市价；2-限价")
    private Byte priceType;

    /**
     * 交易类型：1-开仓；2-平仓
     */
    @Column(name = "trade_type")
    @ApiModelProperty(value="交易类型：1-开仓；2-平仓")
    private Byte tradeType;

    /**
     * 买卖类型：1-买入；2-卖出
2 卖出

     */
    @Column(name = "type")
    @ApiModelProperty(value="买卖类型：1-买入；2-卖出,2 卖出,")
    private Byte type;

    /**
     * 平仓委托关联单号
     */
    @Column(name = "open_order_id")
    @ApiModelProperty(value="平仓委托关联单号")
    private Long openOrderId;

    /**
     * status
0未成交
1已成交
2已取消
4异常单
     */
    @Column(name = "status")
    @ApiModelProperty(value="status,0未成交,1已成交,2已取消,4异常单")
    private Byte status;

    /**
     * 更新时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}