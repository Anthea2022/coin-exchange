package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.entity.Market;
import camellia.domain.entity.TradeArea;
import camellia.domain.vo.TradeAreaMarketVo;
import camellia.service.MarketService;
import camellia.service.TradeAreaService;
import cn.hutool.core.lang.Snowflake;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 11:11
 */

@Api(tags = "交易场所")
@RestController
@RequestMapping("/tradeArea")
public class TradeAreaController {
    @Autowired
    private TradeAreaService tradeAreaService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private Snowflake snowflake;

    @ApiOperation("分页列举")
    @GetMapping("listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, String coinName, String name,
                                         Byte sort, Byte status) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.like(StringUtils.hasText(name), "name", name);
        query.like(StringUtils.hasText(coinName), "coin_name", coinName);
        query.eq(!ObjectUtils.isEmpty(sort), "sort", sort);
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        return BaseResponse.success(tradeAreaService.page(query));
    }

    @ApiOperation("新增交易地址")
    @PostMapping("/saveTradeArea")
    @PreAuthorize("@coin.hasPermission('trade_area_save')")
    public BaseResponse<Object> saveTradeArea(@RequestBody TradeArea tradeArea) {
        tradeArea.setStatus((byte) 1);
        tradeArea.setCode(snowflake.nextIdStr());
        if(BooleanUtils.isTrue(tradeAreaService.saveTradeArea(tradeArea))) {
            return BaseResponse.success("创建成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "创建失败");
    }

    /**
     * @param tid 地址id
     * @return
     */
    @ApiOperation("删除交易地址")
    @PostMapping("/deleteTradeArea")
    @PreAuthorize("@coin.hasPermission('tarde_area_delete')")
    public BaseResponse<Object> deleteTradeArea(@NotNull Long tid) {
        if (BooleanUtils.isTrue(tradeAreaService.deleteTradeArea(tid))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }

    @ApiOperation("禁用/使用")
    @PostMapping("/status/set")
    @PreAuthorize("@coin.hasPermission('trade_area_status_set')")
    public BaseResponse<Object> setStatus(@NotNull Long tid, @NotNull Byte status) {
        TradeArea tradeArea = new TradeArea();
        tradeArea.setId(tid);
        tradeArea.setStatus(status);
        if (BooleanUtils.isTrue(tradeAreaService.updateTradeArea(tradeArea))) {
            return BaseResponse.success("设置成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "设置失败");
    }

    @ApiOperation("查询交易区域一下的市场")
    @GetMapping("/markets")
    public BaseResponse<Object> listMarkets() {
        List<TradeArea> tradeAreas= tradeAreaService.list(new Query());
        if (CollectionUtils.isEmpty(tradeAreas)) {
            return BaseResponse.success(Collections.emptyList());
        }
        List<TradeAreaMarketVo> tradeAreaMarketVos = new ArrayList<>();
        for (TradeArea tradeArea : tradeAreas) {
            List<Market> markets = marketService.list(new Query().eq("trade_area_id", tradeArea.getId()));
            TradeAreaMarketVo tradeAreaMarketVo = new TradeAreaMarketVo(tradeArea.getName(), markets);
            tradeAreaMarketVos.add(tradeAreaMarketVo);
        }
        return BaseResponse.success(tradeAreaMarketVos);
    }
}
