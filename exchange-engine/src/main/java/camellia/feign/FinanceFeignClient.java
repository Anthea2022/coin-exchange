package camellia.feign;

import camellia.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 17:37
 */
@FeignClient(name = "finance-server", configuration = OAuth2FeignConfig.class)
public interface FinanceFeignClient {
    @PostMapping("/finance/account/deduct")
    BaseResponse<Object> deduct(@NotNull Long coinId, Long orderId, @NotNull BigDecimal num, @NotNull BigDecimal fee,
                                String remark, String businessType, @NotNull Byte direction);
}
