package camellia.domain.entity;

import camellia.domain.param.MarketParam;
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
* 交易对配置信息
*/
@Table(name = "market")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Market {
    /**
     * 市场ID
     */
    @ApiModelProperty(value="市场ID")
    private Long id;

    /**
     * 类型：1-数字货币；2：创新交易
     */
    @ApiModelProperty(value="类型：1-数字货币；2：创新交易")
    private Byte type;

    /**
     * 交易区域ID
     */
    @Column(name = "trade_area_id")
    @ApiModelProperty(value="交易区域ID")
    @NotNull
    private Long tradeAreaId;

    /**
     * 卖方市场ID
     */
    @Column(name = "sell_coin_id")
    @ApiModelProperty(value="卖方市场ID")
    @NotBlank
    private Long sellCoinId;

    /**
     * 买方币种ID
     */
    @Column(name = "buy_coin_id")
    @ApiModelProperty(value="买方币种ID")
    @NotNull
    private Long buyCoinId;

    /**
     * 交易对标识
     */
    @Column(name = "symbol")
    @ApiModelProperty(value="交易对标识")
    @NotNull
    private String symbol;

    /**
     * 名称
     */
    @Column(name = "name")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 标题
     */
    @Column(name = "title")
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 市场logo
     */
    @Column(name = "img")
    @ApiModelProperty(value="市场logo")
    private String img;

    /**
     * 开盘价格
     */
    @Column(name = "open_price")
    @ApiModelProperty(value="开盘价格")
    @NotNull
    private BigDecimal openPrice;

    /**
     * 买入手续费率
     */
    @Column(name = "fee_buy")
    @ApiModelProperty(value="买入手续费率")
    @NotNull
    private BigDecimal feeBuy;

    /**
     * 卖出手续费率
     */
    @Column(name = "fee_sell")
    @ApiModelProperty(value="卖出手续费率")
    @NotNull
    private BigDecimal feeSell;

    /**
     * 保证金占用比例
     */
    @Column(name = "margin_rate")
    @ApiModelProperty(value="保证金占用比例")
    private BigDecimal marginRate;

    /**
     * 单笔最小委托量
     */
    @Column(name = "num_min")
    @ApiModelProperty(value="单笔最小委托量")
    @NotNull
    private BigDecimal numMin;

    /**
     * 单笔最大委托量
     */
    @Column(name = "num_max")
    @ApiModelProperty(value="单笔最大委托量")
    @NotNull
    private BigDecimal numMax;

    /**
     * 单笔最小成交额
     */
    @Column(name = "trade_min")
    @ApiModelProperty(value="单笔最小成交额")
    private BigDecimal tradeMin;

    /**
     * 单笔最大成交额
     */
    @Column(name = "trade_max")
    @ApiModelProperty(value="单笔最大成交额")
    private BigDecimal tradeMax;

    /**
     * 价格小数位
     */
    @Column(name = "price_scale")
    @ApiModelProperty(value="价格小数位")
    @NotNull
    private Byte priceScale;

    /**
     * 数量小数位
     */
    @Column(name = "num_scale")
    @ApiModelProperty(value="数量小数位")
    private Byte numScale;

    /**
     * 合约单位
     */
    @Column(name = "contract_unit")
    @ApiModelProperty(value="合约单位")
    private Integer contractUnit;

    /**
     * 点
     */
    @Column(name = "point_value")
    @ApiModelProperty(value="点")
    private BigDecimal pointValue;

    /**
     * 合并深度（格式：4,3,2）4:表示为0.0001；3：表示为0.001
     */
    @Column(name = "merge_depth")
    @ApiModelProperty(value="合并深度（格式：4,3,2）4:表示为0.0001；3：表示为0.001")
    @NotNull
    private String mergeDepth;

    /**
     * 交易时间
     */
    @Column(name = "trade_time")
    @ApiModelProperty(value="交易时间")
    @NotNull
    private String tradeTime;

    /**
     * 交易周期
     */
    @Column(name = "trade_week")
    @ApiModelProperty(value="交易周期")
    @NotNull
    private String tradeWeek;

    /**
     * 排序列
     */
    @Column(name = "sort")
    @ApiModelProperty(value="排序列")
    private Integer sort;

    /**
     * 状态
0禁用
1启用
     */
    @Column(name = "status")
    @ApiModelProperty(value="状态,0禁用,1启用")
    private Byte status;

    /**
     * 福汇API交易对
     */
    @Column(name = "fxcm_symbol")
    @ApiModelProperty(value="福汇API交易对")
    private String fxcmSymbol;

    /**
     * 对应雅虎金融API交易对
     */
    @Column(name = "yahoo_symbol")
    @ApiModelProperty(value="对应雅虎金融API交易对")
    private String yahooSymbol;

    /**
     * 对应阿里云API交易对
     */
    @Column(name = "aliyun_symbol")
    @ApiModelProperty(value="对应阿里云API交易对")
    private String aliyunSymbol;

    /**
     * 更新时间
     */
    @Column(name= "last_update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    public Market(MarketParam marketParam) {
        setType(marketParam.getType());
        setTitle(marketParam.getTitle());
        setTradeAreaId(marketParam.getTradeAreaId());
        setSellCoinId(marketParam.getSellCoinId());
        setBuyCoinId(marketParam.getBuyCoinId());
        setSymbol(marketParam.getSymbol());
        setName(marketParam.getName());
        setFeeBuy(marketParam.getFeeBuy());
        setFeeSell(marketParam.getFeeSell());
        setMarginRate(marketParam.getMarginRate());
        setFxcmSymbol(marketParam.getFxcmSymbol());
        setYahooSymbol(marketParam.getYahooSymbol());
        setAliyunSymbol(marketParam.getAliyunSymbol());
        setNumScale(marketParam.getNumScale());
        setContractUnit(marketParam.getContractUnit());
        setMergeDepth(marketParam.getMergeDepth());
        setTradeWeek(marketParam.getTradeWeek());
        setSort(marketParam.getSort());
        setStatus(marketParam.getStatus());
        setNumMin(marketParam.getNumMin());
        setNumMax(marketParam.getNumMax());
        setTradeMin(marketParam.getTradeMin());
        setTradeMax(marketParam.getTradeMax());
    }
}