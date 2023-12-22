package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.service.VerifyCodeService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 22:00
 */
@RestController
@RequestMapping("/verify/code")
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation("获取验证码")
    @GetMapping("/verify_code/send")
    public BaseResponse<Object> getVerifyCode(HttpServletRequest request) {
        return BaseResponse.success(verifyCodeService.sendVerifyCode(request));
    }

    @ApiOperation("验证验证码")
    @PostMapping("/verify_code/check")
    public BaseResponse<Object> checkVerifyCode(@NotBlank String code, HttpServletRequest request) {
        if (BooleanUtils.isTrue(verifyCodeService.checkVerifyCode(code, request))) {
            return BaseResponse.success("验证码正确");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
    }
}
