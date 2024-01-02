package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.CoinType;
import camellia.service.CoinTypeService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/12/23 0:17
 */
@Api("财务系统货币类型")
@RestController("/coin/type")
public class CoinTypeController {
    @Autowired
    private CoinTypeService coinTypeService;

    @ApiOperation("分页获取货币类型")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPageCoinType(@NotNull Integer pageSize, @NotNull Integer pageNum) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        return BaseResponse.success(coinTypeService.listPageCoinType(query));
    }

    @ApiOperation("添加货币类型")
    @PostMapping("/save")
    public BaseResponse<Object> saveCoinType(@RequestBody CoinType coinType) {
        if (BooleanUtils.isTrue(coinTypeService.hasSame(coinType))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "存在相同的货币");
        }
        if (BooleanUtils.isTrue(coinTypeService.saveCoinType(coinType))) {
            return BaseResponse.success("添加货币类型成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "添加货币类型失败");
    }

    @ApiOperation("删除货币类型")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteCoinType(@NotNull Long coinTypeId) {
        if (BooleanUtils.isTrue(coinTypeService.deleteCoinType(coinTypeId))) {
            // TODO: 2023/12/23 删除相关记录
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }

    @ApiOperation("修改货币类型状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setCoinTypeStatus(@NotNull Long coinTypeId, @NotNull Byte status) {
        CoinType coinType = new CoinType();
        coinType.setId(coinTypeId);
        coinType.setStatus(status);
        if (BooleanUtils.isTrue(coinTypeService.setCoinTypeStatus(coinType))) {
            return BaseResponse.success("设置成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "设置失败");
    }
}
