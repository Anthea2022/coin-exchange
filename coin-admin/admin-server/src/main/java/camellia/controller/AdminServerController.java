package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.LoginResult;
import camellia.feign.AuthorAdminLoginFeign;
import camellia.feign.JwtToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static camellia.constant.LoginConstant.*;

/**
 * @author anthea
 * @date 2023/10/24 15:09
 */
@RestController
@Api(tags = "admin测试接口")
public class AdminServerController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthorAdminLoginFeign authorAdminLoginFeign;

    @ApiOperation("测试解口")
    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "OK");
        return map;
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<Object> login(@NotBlank String username, @NotBlank String password) {
        ResponseEntity<JwtToken> jwtTokenResponseEntity = authorAdminLoginFeign.login(PASSWORD_GRANT_TYPE, ADMIN_LOGIN_TYPE, username, password, BASIC_TOKEN);
        if (jwtTokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            return BaseResponse.fail(ResponseCodes.FAIL, "账号或密码错误");
        }
        JwtToken jwtToken = jwtTokenResponseEntity.getBody();
        String accessToken = jwtToken.getAccessToken();
        JSONObject jwtJson = JSON.parseObject(JwtHelper.decode(accessToken).getClaims());
        JSONArray authorities = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(authorities)) {
            authorityList = authorities.stream()
                    .map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
                    .collect(Collectors.toList());
            for (SimpleGrantedAuthority simpleGrantedAuthority : authorityList) {
                System.out.println(simpleGrantedAuthority.getAuthority());
            }
        }
        stringRedisTemplate.opsForValue().set(accessToken, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return BaseResponse.success(new LoginResult(accessToken, authorityList));
    }
}
