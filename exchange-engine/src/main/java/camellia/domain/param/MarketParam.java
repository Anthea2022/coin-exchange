package camellia.domain.param;

import com.gitee.fastmybatis.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 15:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketParam {
    private Byte type;

    /**
     * 交易区域ID
     */
    private Long tradeAreaId;

    /**
     * 卖方市场ID
     */
    @ApiModelProperty(value="卖方市场ID")
    @NotBlank
    private Long sellCoinId;

    /**
     * 买方币种ID
     */
    @ApiModelProperty(value="买方币种ID")
    @NotNull
    private Long buyCoinId;

    /**
     * 交易对标识
     */
    @ApiModelProperty(value="交易对标识")
    @NotNull
    private String symbol;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 市场logo
     */
    @ApiModelProperty(value="市场logo")
    private MultipartFile file;

    /**
     * 开盘价格
     */
    @ApiModelProperty(value="开盘价格")
    @NotNull
    private BigDecimal openPrice;

    /**
     * 买入手续费率
     */
    @ApiModelProperty(value="买入手续费率")
    @NotNull
    private BigDecimal feeBuy;

    /**
     * 卖出手续费率
     */
    @ApiModelProperty(value="卖出手续费率")
    @NotNull
    private BigDecimal feeSell;

    /**
     * 保证金占用比例
     */
    @ApiModelProperty(value="保证金占用比例")
    private BigDecimal marginRate;

    /**
     * 单笔最小委托量
     */
    @ApiModelProperty(value="单笔最小委托量")
    @NotNull
    private BigDecimal numMin;

    /**
     * 单笔最大委托量
     */
    @ApiModelProperty(value="单笔最大委托量")
    @NotNull
    private BigDecimal numMax;

    /**
     * 单笔最小成交额
     */
    @ApiModelProperty(value="单笔最小成交额")
    private BigDecimal tradeMin;

    /**
     * 单笔最大成交额
     */
    @ApiModelProperty(value="单笔最大成交额")
    private BigDecimal tradeMax;

    /**
     * 价格小数位
     */
    @ApiModelProperty(value="价格小数位")
    @NotNull
    private Byte priceScale;

    /**
     * 数量小数位
     */
    @ApiModelProperty(value="数量小数位")
    private Byte numScale;

    /**
     * 合约单位
     */
    @ApiModelProperty(value="合约单位")
    private Integer contractUnit;

    /**
     * 点
     */
    @ApiModelProperty(value="点")
    private BigDecimal pointValue;

    /**
     * 合并深度（格式：4,3,2）4:表示为0.0001；3：表示为0.001
     */
    @ApiModelProperty(value="合并深度（格式：4,3,2）4:表示为0.0001；3：表示为0.001")
    @NotNull
    private String mergeDepth;

    /**
     * 交易周期
     */
    @ApiModelProperty(value="交易周期")
    @NotNull
    private String tradeWeek;

    /**
     * 排序列
     */
    @ApiModelProperty(value="排序列")
    private Integer sort;

    /**
     * 状态
     0禁用
     1启用
     */
    @ApiModelProperty(value="状态,0禁用,1启用")
    private Byte status;

    /**
     * 福汇API交易对
     */
    @ApiModelProperty(value="福汇API交易对")
    private String fxcmSymbol;

    /**
     * 对应雅虎金融API交易对
     */
    @ApiModelProperty(value="对应雅虎金融API交易对")
    private String yahooSymbol;

    /**
     * 对应阿里云API交易对
     */
    @ApiModelProperty(value="对应阿里云API交易对")
    private String aliyunSymbol;
}
