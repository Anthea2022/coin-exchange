package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Wallet;
import camellia.service.impl.WalletService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Object> deleteWallet(Long id) {
        if (BooleanUtils.isTrue(walletService.deleteWallet(id))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }
}
