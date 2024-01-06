package camellia.domain.model;

import camellia.enums.OrderDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 20:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;

    private Long uid;

    private String symbol;

    /**
     * 买入或卖出量
     */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 成交量
     */
    private BigDecimal tradeAmount = BigDecimal.ZERO;

    /**
     * 成交额
     */
    private BigDecimal turnover = BigDecimal.ZERO;

    /**
     * 币单位
     */
    private String coinSymbol;

    /**
     * 结算单位
     */
    private String baseSymbol;

    private Byte status;

    private OrderDirection orderDirection;

    private BigDecimal price = BigDecimal.ZERO;

    private Date createTime;

    /**
     * 完结时间
     */
    private Date completedTime;

    private boolean isCancel;

    /**
     * 交易取消时间
     */
    private Date cancelTime;

    private boolean completed;

    private List<OrderDetail> details;
}
