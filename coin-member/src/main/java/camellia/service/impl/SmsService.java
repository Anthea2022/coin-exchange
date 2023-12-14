package camellia.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
    private StringRedisTemplate stringRedisTemplate;

    // 单发短信
    public Boolean sendMsg(String phone) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("");
        // 模板
        request.setTemplateCode("");
        String code = RandomUtil.randomNumbers(6);
        request.setTemplateParam("验证码为：" + code);
        try {
            iSmsService.sendSmsRequest(request);
            stringRedisTemplate.opsForValue().set("code" + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkCode(String phone, String code) {
        if (!stringRedisTemplate.hasKey("code" + phone)) {
            return false;
        }
        String codeInRedis = stringRedisTemplate.opsForValue().get("code" + phone);
        return codeInRedis.equals(code);
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
}
