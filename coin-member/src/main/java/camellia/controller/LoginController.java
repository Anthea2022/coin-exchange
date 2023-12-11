package camellia.controller;

import camellia.common.BaseResponse;
import camellia.domain.JwtToken;
import camellia.domain.LoginResult;
import camellia.service.Oauth2FeignClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static camellia.constant.LoginConstant.*;

/**
 * @author 墨染盛夏
 * @version 2023/11/30 20:26
 */
@Api(tags = "会员登录")
@RestController
public class LoginController {
    @Autowired
    private Oauth2FeignClient oauth2FeignClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("登录")
    @PostMapping("/login")
    public BaseResponse<Object> login(@NotBlank String email, @NotBlank String password){
        ResponseEntity<JwtToken> jwtTokenResponseEntity = oauth2FeignClient.login(PASSWORD_GRANT_TYPE, USER_LOGIN_TYPE, email, password, BASIC_TOKEN);
        JwtToken jwtToken = jwtTokenResponseEntity.getBody();
        String accessToken = jwtToken.getAccessToken();
        JSONObject jwtJson = JSON.parseObject(JwtHelper.decode(accessToken).getClaims());
        JSONArray authorities = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(authorities)) {
            authorityList = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                    .collect(Collectors.toList());
            for (SimpleGrantedAuthority simpleGrantedAuthority : authorityList) {
                System.out.println(simpleGrantedAuthority.getAuthority());
            }
        }
        stringRedisTemplate.opsForValue().set(accessToken, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return BaseResponse.success(new LoginResult(accessToken, authorityList));
    }
}
