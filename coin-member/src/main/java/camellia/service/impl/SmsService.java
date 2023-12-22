package camellia.service.impl;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.exception.BusinessException;
import camellia.mapper.UserInfoMapper;
import camellia.util.TokenUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.gitee.fastmybatis.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 墨染盛夏
 * @version 2023/12/14 23:10
 */
@Service
public class SmsService {
    @Autowired
    private ISmsService iSmsService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 单发短信
    public Boolean sendMsg(String phone) {
        try {
            String code = RandomUtil.randomNumbers(6);
            SendSmsRequest request = buildMySmsRequest(phone, code);
            iSmsService.sendSmsRequest(request);
            stringRedisTemplate.opsForValue().set("code" + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean sendMsg() {
        String phone = getMyPhone();
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return sendMsg(phone);
    }

    private SendSmsRequest buildMySmsRequest(String phone, String code) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        // 签名
        request.setSignName("");
        // 模板
        request.setTemplateCode("");
        request.setTemplateParam("验证码为：" + code);
        return request;
    }

    public Boolean checkPhone(String phone, String code) {
        if (!stringRedisTemplate.hasKey("code" + phone)) {
            return false;
        }
        String codeInRedis = stringRedisTemplate.opsForValue().get("code" + phone);
        return codeInRedis.equals(code);
    }

    /**
     * 验证旧手机号码的验证码
     * @param code
     * @return
     */
    public Boolean checkMyPhone(String code) {
        String phone = getMyPhone();
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return checkPhone(phone, code);
    }

    private String getMyPhone() {
        Long uid = TokenUtil.getUid();
        String phone = userInfoMapper.getColumnValue("phone", new Query().eq("id", uid), String.class);
        return phone;
    }

    // 批量发送短信
    public boolean batchSendMsg(String msg, List<String> phones) {
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        request.setMethod(MethodType.GET);
        StringBuilder phoneJson = new StringBuilder("[");
        StringBuilder templateCodeJson = new StringBuilder("[");
        for (int i=0;i<phones.size();i++) {
            String code = RandomUtil.randomNumbers(6);
            if (i == phones.size()-1) {
                phoneJson.append(phones.get(i) + "]");
                templateCodeJson.append(code + "]");
            } else {
                phoneJson.append(phones.get(i) + ",");
                templateCodeJson.append(code + ",");
            }
        }
        request.setPhoneNumberJson(phoneJson.toString());
        request.setSignNameJson(templateCodeJson.toString());
        request.setTemplateCode("");
        request.setTemplateParamJson("");
        try {
            iSmsService.sendSmsBatchRequest(request);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendEmail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(email);
        String code = RandomUtil.randomNumbers(6);
        simpleMailMessage.setSubject("数字贸易商城");
        simpleMailMessage.setText("您正在注册数字货币交易商城，验证码为：" + code);
        try {
            javaMailSender.send(simpleMailMessage);
            stringRedisTemplate.opsForValue().set("email code" + email, code, 5, TimeUnit.MINUTES);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public Boolean checkEmail(String email, String code) {
        if (!stringRedisTemplate.hasKey("email code" + email)) {
            throw new BusinessException(ResponseCodes.FAIL, "未发送验证码或验证码已过期");
        }
        String codeInRedis = stringRedisTemplate.opsForValue().get("email code" + email);
        if (code.equals(codeInRedis)) {
            return true;
        }
        return false;
    }

    private String getMyEmail() {
        Long uid = TokenUtil.getUid();
        return userInfoMapper.getColumnValue("email", new Query().eq("id", uid), String.class);
    }

    public void sendMyEmail() {
        sendEmail(getMyEmail());
    }

    public Boolean checkMyEmail(String code) {
        return checkEmail(getMyEmail(), code);
    }
}
