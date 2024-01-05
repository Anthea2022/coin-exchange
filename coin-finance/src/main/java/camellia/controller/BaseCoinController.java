package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.BaseCoin;
import camellia.service.BaseCoinService;
import camellia.service.CoinService;
import cn.hutool.core.lang.Snowflake;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 11:37
 */
@Api(tags = "基础币配置")
@RestController
@RequestMapping("/baseCoin")
public class BaseCoinController {
    @Autowired
    private BaseCoinService baseCoinService;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private CoinService coinService;

    @ApiOperation("配置基础货币")
    @PostMapping("/set")
    @PreAuthorize("@coin.hasPermission('base_coin_set')")
    public BaseResponse<Object> setBaseCoin(@RequestBody BaseCoin baseCoin) {
        String name = coinService.getColumnValue("name", new Query().eq("id", baseCoin.getId()), String.class);
        if (StringUtils.isEmpty(name)) {
            return BaseResponse.fail(ResponseCodes.QUERY_NULL_ERROR, "无此币种");
        }
        if (BooleanUtils.isTrue(baseCoinService.saveBaseCoin(baseCoin))) {
            return BaseResponse.success("配置成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "配置失败");
    }

    @ApiOperation("撤销基础货币")
    @PostMapping("/cancel")
    @PreAuthorize("@coin.hasPermission('base_coin_cancel')")
    public BaseResponse<Object> cancelBaseCoin(@NotNull Long cid) {
        String name = coinService.getColumnValue("name", new Query().eq("id",cid), String.class);
        if (StringUtils.isEmpty(name)) {
            return BaseResponse.fail(ResponseCodes.QUERY_NULL_ERROR, "无此币种");
        }
        if (BooleanUtils.isTrue(baseCoinService.cancelBaseCoin(cid))) {
            return BaseResponse.success("撤销成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "撤销失败");
    }

    @ApiOperation("查看基础币")
    @GetMapping("/get")
    public BaseResponse<Object> getBaseCoin() {
        return BaseResponse.success(baseCoinService.getBaseCoin());
    }
}
