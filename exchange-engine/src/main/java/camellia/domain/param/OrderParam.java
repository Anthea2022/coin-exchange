package camellia.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 14:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderParam {
    @NotBlank
    private String symbol;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal volume;

    @NotNull
    private Byte type;
}
