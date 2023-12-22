package camellia.service;

import camellia.common.ResponseCodes;
import camellia.exception.BusinessException;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 22:01
 */
@Service
public class VerifyCodeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String sendVerifyCode(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(ResponseCodes.FAIL, "获取验证码失败");
        }
        stringRedisTemplate.opsForValue().set(ip, code, 5, TimeUnit.MINUTES);
        String imageBase64 = lineCaptcha.getImageBase64();
        return imageBase64;
    }

    public Boolean checkVerifyCode(String code, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(ip))) {
            throw new BusinessException(ResponseCodes.FAIL, "请重新获取验证码");
        }
        String verifyCode = stringRedisTemplate.opsForValue().get(ip);
        if (verifyCode.equals(code)) {
            return true;
        }
        return false;
    }
}
