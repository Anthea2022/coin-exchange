package camellia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 成交数量
     */
    private BigDecimal amount;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 费率
     */
    private BigDecimal fee;

    /**
     * 成功时间
     */
    private Long dealTime;
}
