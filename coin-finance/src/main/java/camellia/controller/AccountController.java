package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Account;
import camellia.service.AccountService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2023/12/29 13:34
 */
@Api("账号")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ApiOperation("当前用户的资产情况")
    @GetMapping("/asset/get")
    public BaseResponse<Object> getAsset() {
        Long uid = TokenUtil.getUid();
        return BaseResponse.success(accountService.list(new Query().eq("user_id", uid)));
    }

    // TODO: 2023/12/29 总资产

    // 交易市场调用 充值币
    @ApiOperation("扣钱")
    @PostMapping("/deduct")
    public BaseResponse<Object> deduct(@NotNull Long coinId, Long orderId, @NotNull BigDecimal num, @NotNull BigDecimal fee,
                                       String remark, String businessType, @NotNull Byte direction) {
        if (BooleanUtils.isTrue(accountService.decrease(TokenUtil.getUid(), coinId, orderId, num, fee, remark, businessType, direction))) {
            return BaseResponse.success("扣款成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "扣款失败");
    }

    // 兑换币
    @ApiOperation("入账")
    @PostMapping("/income")
    public BaseResponse<Object> income(@NotNull Long coinId, Long orderId, @NotNull BigDecimal num, @NotNull BigDecimal fee,
                                       String remark, String businessType, @NotNull Byte direction) {
        if (BooleanUtils.isTrue(accountService.increase(TokenUtil.getUid(), coinId, orderId, num, fee, remark, businessType, direction))) {
            return BaseResponse.success("进账成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "进账失败");
    }
}
