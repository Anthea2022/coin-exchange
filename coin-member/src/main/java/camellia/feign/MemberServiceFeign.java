package camellia.feign;

import camellia.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 18:41
 */
@FeignClient(name = "member-server", configuration = OAuth2FeignConfig.class, path = "/user")
public interface MemberServiceFeign {
    @GetMapping("/info/get")
    BaseResponse<Object> getBasicInfo();

    @GetMapping("/getByRealName")
    BaseResponse<Object> getUidByRealName(@NotBlank String realName);
}
