package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.service.impl.SmsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import static camellia.constant.CodeTypeConstant.PHONE_VERIFY_CODE;

/**
 * @author 墨染盛夏
 * @version 2023/12/14 22:56
 */
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    // 单发短信
    @ApiOperation("手机发送验证码")
    @PostMapping("/phone/verify_code/send")
    public BaseResponse<Object> sendPhoneVerifyCode(@NotBlank String phone) {
        if (BooleanUtils.isTrue(smsService.sendMsg(phone))) {
            return BaseResponse.success("发送成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "发送失败");
    }

    @ApiOperation("/给旧手机发送验证码")
    @PostMapping("/old/phone/verify_code/send")
    public BaseResponse<Object> sendOldPhoneVerifyCode() {
        if (BooleanUtils.isTrue(smsService.sendMsg())) {
            return BaseResponse.success("发送成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "发送失败");
    }


//    @ApiOperation("验证绑定手机号验证码")
//    @PostMapping("/phone/verify_code/check")
//    public BaseResponse<Object> checkPhoneVerifyCode(@NotBlank String phone, @NotBlank String code) {
//        if (BooleanUtils.isTrue(smsService.checkCode(PHONE_VERIFY_CODE, phone, code))) {
//            return BaseResponse.success("验证码正确");
//        }
//        return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
//    }

//    @ApiOperation("修改支付密码发送验证码")
//    @PostMapping("/updatePayPsw/verify_code/send")
//    public BaseResponse<Object> sendVerifyCodeWhileUpdatePayPsw() {
//        if (BooleanUtils.isTrue(smsService.sendMsgWhileUpdatePayPsw())) {
//            return BaseResponse.success("发送成功");
//        }
//        return BaseResponse.fail(ResponseCodes.FAIL, "发送失败");
//    }

//    @ApiOperation("验证修改密码的验证码")
//    @PostMapping("/updatePayPsw/verify_code/check")
//    public BaseResponse<Object> checkPswCode(@NotBlank String code) {
//        if (BooleanUtils.isTrue(smsService.checkPswCode(code))) {
//            return BaseResponse.success("验证码正确");
//        }
//        return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
//    }
}
