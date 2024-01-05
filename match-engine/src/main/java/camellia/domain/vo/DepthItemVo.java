package camellia.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepthItemVo implements Comparable<DepthItemVo> {

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price = BigDecimal.ZERO;;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal volume = BigDecimal.ZERO;


    @Override
    public int compareTo(DepthItemVo o) {
        return this.price.compareTo(o.getPrice());
    }
}