package camellia.domain.entity;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
* 成交数据
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turnover_record")
public class TurnoverRecord {
    private Long id;

    /**
     * 市场ID
     */
    @Column(name = "market_id")
    @ApiModelProperty(value="市场ID")
    private Long marketId;

    /**
     * 成交价
     */
    @ApiModelProperty(value="成交价")
    private BigDecimal price;

    /**
     * 成交数量
     */
    @ApiModelProperty(value="成交数量")
    private BigDecimal volume;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}