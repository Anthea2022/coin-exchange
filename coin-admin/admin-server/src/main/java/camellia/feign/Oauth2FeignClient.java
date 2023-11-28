package camellia.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 墨染盛夏
 * @version 2023/11/20 12:21
 */
@FeignClient(value = "author-server")
public interface Oauth2FeignClient {
    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> login(
            @RequestParam("grant_type") String grantType,
            @RequestParam("login_type") String loginType,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestHeader("Authorization") String basicToken        //第三方客户端加密的值
    );
}
