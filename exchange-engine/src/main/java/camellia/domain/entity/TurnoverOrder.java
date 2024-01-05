package camellia.domain.entity;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 成交订单
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turnover_order")
@Accessors(chain = true)
public class TurnoverOrder {
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 市场ID
     */
    @Column(name = "market_id")
    @ApiModelProperty(value="市场ID")
    private Long marketId;

    /**
     * 交易对类型：1-币币交易；2-创新交易；
     */
    @Column(name = "market_type")
    @ApiModelProperty(value="交易对类型：1-币币交易；2-创新交易；")
    private Integer marketType;

    /**
     * 交易类型:1 买 2卖
     */
    @Column(name = "trade_type")
    @ApiModelProperty(value="交易类型:1 买 2卖")
    private int tradeType;

    /**
     * 交易对标识符
     */
    @Column(name = "symbol")
    @ApiModelProperty(value="交易对标识符")
    private String symbol;

    /**
     * 交易对名称
     */
    @Column(name = "market_name")
    @ApiModelProperty(value="交易对名称")
    private String marketName;

    /**
     * 卖方用户ID
     */
    @Column(name = "sell_user_id")
    @ApiModelProperty(value="卖方用户ID")
    private Long sellUserId;

    /**
     * 卖方币种ID
     */
    @Column(name = "sell_coin_id")
    @ApiModelProperty(value="卖方币种ID")
    private Long sellCoinId;

    /**
     * 卖方委托订单ID
     */
    @Column(name = "sell_order_id")
    @ApiModelProperty(value="卖方委托订单ID")
    private Long sellOrderId;

    /**
     * 卖方委托价格
     */
    @Column(name = "sell_price")
    @ApiModelProperty(value="卖方委托价格")
    private BigDecimal sellPrice;

    /**
     * 卖方手续费率
     */
    @Column(name = "sell_fee_rate")
    @ApiModelProperty(value="卖方手续费率")
    private BigDecimal sellFeeRate;

    /**
     * 卖方委托数量
     */
    @Column(name = "sell_volume")
    @ApiModelProperty(value="卖方委托数量")
    private BigDecimal sellVolume;

    /**
     * 买方用户ID
     */
    @Column(name = "buy_user_id")
    @ApiModelProperty(value="买方用户ID")
    private Long buyUserId;

    /**
     * 买方币种ID
     */
    @Column(name = "buy_coin_id")
    @ApiModelProperty(value="买方币种ID")
    private Long buyCoinId;

    /**
     * 买方委托订单ID
     */
    @Column(name = "buy_order_id")
    @ApiModelProperty(value="买方委托订单ID")
    private Long buyOrderId;

    /**
     * 买方委托数量
     */
    @Column(name = "buy_volume")
    @ApiModelProperty(value="买方委托数量")
    private BigDecimal buyVolume;

    /**
     * 买方委托价格
     */
    @Column(name = "buy_price")
    @ApiModelProperty(value="买方委托价格")
    private BigDecimal buyPrice;

    /**
     * 买方手续费率
     */
    @Column(name = "buy_fee_rate")
    @ApiModelProperty(value="买方手续费率")
    private BigDecimal buyFeeRate;

    /**
     * 委托订单ID
     */
    @Column(name = "order_id")
    @ApiModelProperty(value="委托订单ID")
    private Long orderId;

    /**
     * 成交总额
     */
    @Column(name = "amount")
    @ApiModelProperty(value="成交总额")
    private BigDecimal amount;

    /**
     * 成交价格
     */
    @Column(name = "price")
    @ApiModelProperty(value="成交价格")
    private BigDecimal price;

    /**
     * 成交数量
     */
    @Column(name = "volume")
    @ApiModelProperty(value="成交数量")
    private BigDecimal volume;

    /**
     * 成交卖出手续费
     */
    @Column(name = "deal_sell_fee")
    @ApiModelProperty(value="成交卖出手续费")
    private BigDecimal dealSellFee;

    /**
     * 成交卖出手续费率
     */
    @Column(name = "deal_sell_fee_rate")
    @ApiModelProperty(value="成交卖出手续费率")
    private BigDecimal dealSellFeeRate;

    /**
     * 成交买入手续费
     */
    @Column(name = "deal_buy_fee")
    @ApiModelProperty(value="成交买入手续费")
    private BigDecimal dealBuyFee;

    /**
     * 成交买入成交率费
     */
    @Column(name = "deal_buy_fee_rate")
    @ApiModelProperty(value="成交买入成交率费")
    private BigDecimal dealBuyFeeRate;

    /**
     * 状态0待成交，1已成交，2撤销，3.异常
     */
    @Column(name = "status")
    @ApiModelProperty(value="状态0待成交，1已成交，2撤销，3.异常")
    private Boolean status;

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