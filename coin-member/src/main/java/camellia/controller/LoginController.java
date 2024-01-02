package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.JwtToken;
import camellia.domain.LoginResult;
import camellia.domain.UserInfo;
import camellia.domain.param.RegisterParam;
import camellia.service.AuthorMemberLoginFeign;
import camellia.service.impl.SmsgService;
import camellia.service.impl.UserInfoService;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static camellia.constant.LoginConstant.*;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 19:37
 */
@RestController
public class LoginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsgService smsgService;

    @Autowired
    private AuthorMemberLoginFeign authorMemberLoginFeign;

    @Autowired
    private UserInfoService userInfoService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


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

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<Object> login(@NotBlank String email, @NotBlank String password) {
        ResponseEntity<JwtToken> jwtTokenResponseEntity = authorMemberLoginFeign.login(PASSWORD_GRANT_TYPE, USER_LOGIN_TYPE, email, password, BASIC_TOKEN);
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
//            for (SimpleGrantedAuthority simpleGrantedAuthority : authorityList) {
//                System.out.println(simpleGrantedAuthority.getAuthority());
//            }
        }
        stringRedisTemplate.opsForValue().set(accessToken, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return BaseResponse.success(new LoginResult(accessToken, authorityList));
    }

    /**
     * 填写手机号
     * 验证人际 发送验证码
     * 校验人际 验证验证码
     * @param registerParam
     * @return
     */
    @ApiOperation("会员通过手机号注册")
    @PostMapping("/register/phone")
    public BaseResponse<Object> registerByPhone(@RequestBody RegisterParam registerParam) {
        String phone = registerParam.getAccount();
        if (BooleanUtils.isFalse(smsgService.checkPhone(phone, registerParam.getCode()))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
        }
        // 此前是否注册
        Long phoneId = userInfoService.getColumnValue("id", new Query().eq("phone", phone), Long.class);
        if (!ObjectUtils.isEmpty(phoneId)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "该手机号已经绑定账号");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setPassword(bCryptPasswordEncoder.encode(registerParam.getPassword()));
        if (BooleanUtils.isTrue(userInfoService.saveUser(userInfo))) {
            return BaseResponse.success("注册成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "注册失败");
    }

    @ApiOperation("会员通过邮箱注册")
    @PostMapping("/register/email")
    public BaseResponse<Object> registerByEmail(@RequestBody RegisterParam registerParam) {
        String email = registerParam.getAccount();
        if (BooleanUtils.isFalse(smsgService.checkEmail(email, registerParam.getCode()))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
        }
        Long emailId = userInfoService.getColumnValue("id", new Query().eq("email", email), Long.class);
        if (!ObjectUtils.isEmpty(emailId)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "该手机号已经绑定账号");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(bCryptPasswordEncoder.encode(registerParam.getPassword()));
        if (BooleanUtils.isTrue(userInfoService.saveUser(userInfo))) {
            return BaseResponse.success("注册成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "注册失败");
    }
}
