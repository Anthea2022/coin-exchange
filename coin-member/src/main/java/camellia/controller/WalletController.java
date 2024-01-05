package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.UserInfo;
import camellia.domain.Wallet;
import camellia.feign.MemberServiceFeign;
import camellia.service.impl.WalletService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 0:21
 */
@Api(tags = "钱包管理")
@RequestMapping("/wallet")
@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @ApiOperation("列举分页")
    @GetMapping("/listPage")
    @PreAuthorize("@coin.hasPermission('user_wallet_list')")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, Long cid, String realName) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        if (StringUtils.hasText(realName)) {
            UserInfo userInfo = (UserInfo) memberServiceFeign.getUidByRealName(realName).getData();
            query.eq("user_id", userInfo.getId());
        }
        query.eq(!ObjectUtils.isEmpty(cid), "coin_id", cid);
        return BaseResponse.success(walletService.page(query));
    }

    @ApiOperation("钱包查看")
    @GetMapping("/list")
    public BaseResponse<Object> listAll(Long cid) {
        Long uid = TokenUtil.getUid();
        Query query = new Query();
        query.eq("user_id", uid);
        query.eq(!ObjectUtils.isEmpty(cid), "coin_id", cid);
        return BaseResponse.success(walletService.list(query));
    }

    @ApiOperation("新增地址")
    @PostMapping("/save")
    public BaseResponse<Object> addWallet(@RequestBody Wallet wallet) {
        wallet.setUid(TokenUtil.getUid());
        if (BooleanUtils.isTrue(walletService.addWallet(wallet))) {
            return BaseResponse.success("新增成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "新增失败");
    }

    @ApiOperation("删除地址")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteWallet(@NotNull Long id, @NotBlank String payPsw) {
        if (BooleanUtils.isTrue(walletService.deleteWallet(id, payPsw))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }
}
