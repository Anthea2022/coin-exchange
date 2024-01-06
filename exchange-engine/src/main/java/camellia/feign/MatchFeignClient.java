package camellia.feign;

import camellia.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 23:40
 */
@FeignClient(value = "match-engine", configuration = OAuth2FeignConfig.class)
public interface MatchFeignClient {
    @GetMapping("/match/plate/data")
    BaseResponse<Object> getPlateData(@NotBlank String symbol);
}
