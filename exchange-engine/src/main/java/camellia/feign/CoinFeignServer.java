package camellia.feign;

import camellia.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 14:59
 */
@FeignClient(value = "finance-server", configuration = OAuth2FeignConfig.class)
public interface CoinFeignServer {
    @GetMapping("/finance/coin/getById")
    BaseResponse<Object> getById(@NotNull Long cid);
}
