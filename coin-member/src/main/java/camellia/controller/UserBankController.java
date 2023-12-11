package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.UserBank;
import camellia.service.impl.UserBankService;
import camellia.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/12/2 10:30
 */
@Api(tags = "会员银行卡管理")
@RestController
@RequestMapping("/bank")
public class UserBankController {
    @Autowired
    private UserBankService userBankService;

    @ApiOperation("添加银行卡")
    @PostMapping("/save")
    public BaseResponse<Object> addCard(@RequestBody UserBank userBank) {
        Long uid = TokenUtil.getUid();
        if (ObjectUtils.isEmpty(uid)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "无此用户信息，请尝试重新登录");
        }
        userBank.setUid(uid);
        userBankService.saveIgnoreNull(userBank);
        return BaseResponse.success("添加银行卡成功");
    }

    @ApiOperation("解除银行卡")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteCard(@NotNull Long id) {
        if (BooleanUtils.isTrue(userBankService.deleteCard(id))) {
            return BaseResponse.success("解除银行成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "解除银行卡失败");
    }

    @ApiOperation("设置银行卡状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setStatus(@RequestBody UserBank userBank) {
        if (ObjectUtils.isEmpty(userBank.getId()) || ObjectUtils.isEmpty(userBank.getStatus())) {
            return BaseResponse.fail(ResponseCodes.FAIL, "银行卡基本信息不能为空");
        }
        if (BooleanUtils.isTrue(userBankService.setStatus(userBank))) {
            return BaseResponse.success("禁用/启用银行卡成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "禁用/银行卡失败");
    }
}
