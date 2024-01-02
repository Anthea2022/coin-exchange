package camellia.controller;

import camellia.common.BaseResponse;
import camellia.service.AccountService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
