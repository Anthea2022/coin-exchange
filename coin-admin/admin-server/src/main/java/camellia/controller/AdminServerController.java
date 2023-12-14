package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.LoginResult;
import camellia.feign.JwtToken;
import camellia.feign.Oauth2FeignClient;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private Oauth2FeignClient oauth2FeignClient;

    @ApiOperation("测试解口")
    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "OK");
        return map;
    }
//
//    @ApiOperation("获取用户信息")
//    @GetMapping("/getInfo")
//    public BaseResponse<Object> getById() {
//        return BaseResponse.success(userService.getInfo(TokenUtil.getUid()));
//    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<Object> login(@NotBlank String username, @NotBlank String password) {
        ResponseEntity<JwtToken> jwtTokenResponseEntity = oauth2FeignClient.login(PASSWORD_GRANT_TYPE, ADMIN_LOGIN_TYPE, username, password, BASIC_TOKEN);
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

    @ApiOperation("获取验证码")
    @GetMapping("/verify_code/get")
    public BaseResponse<Object> getVerifyCode(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();
        if (StringUtils.isEmpty(code)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "获取验证码失败");
        }
        stringRedisTemplate.opsForValue().set(ip, code, 5, TimeUnit.MINUTES);
        String imageBase64 = lineCaptcha.getImageBase64();
        return BaseResponse.success(imageBase64);
    }

    @ApiOperation("验证验证码")
    @PostMapping("/verify_code/check")
    public BaseResponse<Object> checkVerifyCode(@NotBlank String code, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(ip))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "请重新获取验证码");
        }
        String verifyCode = stringRedisTemplate.opsForValue().get(ip);
        if (verifyCode.equals(code)) {
            return BaseResponse.success("验证成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "验证失败");
    }
}
