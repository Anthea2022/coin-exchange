package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.entity.FavoriteMarket;
import camellia.domain.entity.Market;
import camellia.domain.param.MarketParam;
import camellia.domain.vo.DepthItemVo;
import camellia.domain.vo.DepthsVo;
import camellia.feign.MatchFeignClient;
import camellia.service.FavoriteMarketService;
import camellia.service.MarketService;
import camellia.util.ImgUtil;
import camellia.util.TokenUtil;
import cn.hutool.http.HttpStatus;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 12:16
 */
@Api(tags = "市场")
@RestController
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    @Autowired
    private FavoriteMarketService favoriteMarketService;

    @Autowired
    private MatchFeignClient matchFeignClient;

    @ApiOperation("列举分页")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, Byte status, String name, String title) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.like(StringUtils.hasText(name), "name", name);
        query.like(StringUtils.hasText(title), "title", title);
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        return BaseResponse.success(marketService.page(query));
    }

    @ApiOperation("新增")
    @PostMapping("/saveMarket")
    @PreAuthorize("@coin.hasPermission('market_save')")
    public BaseResponse<Object> save(@RequestBody MarketParam marketParam) {
        Market market = new Market(marketParam);
        market.setImg(ImgUtil.storeImg(marketParam.getFile()));
        if (BooleanUtils.isTrue(marketService.saveMarket(market))) {
            return BaseResponse.success("创建成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "创建失败");
    }

    @ApiOperation("删除")
    @PostMapping("/deleteMarket")
    @PreAuthorize(("@coin.hasPermission('market_delete')"))
    public BaseResponse<Object> deleteMarket(@NotNull Long mid) {
        if (BooleanUtils.isTrue(marketService.deleteMarket(mid))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }

    @ApiOperation("禁用/启用")
    @PostMapping("/status/set")
    @PreAuthorize(("@coin.hasPermission('market_status/set')"))
    public BaseResponse<Object> setStatus(@NotNull Long mid, @NotNull Byte status) {
        Market market = new Market();
        market.setId(mid);
        market.setStatus(status);
        if (BooleanUtils.isTrue(marketService.updateMarket(market))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }

    @ApiOperation("收藏市场")
    @PostMapping("/favorite/save")
    public BaseResponse<Object> setFavorite(@NotNull Long mid) {
        Market market = marketService.getById(mid);
        if (ObjectUtils.isEmpty(market)) {
            return BaseResponse.fail(ResponseCodes.QUERY_NULL_ERROR, "无此市场");
        }
        if (market.getStatus().equals((byte) 0)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "该市场被禁用");
        }
        FavoriteMarket favoriteMarket = new FavoriteMarket();
        favoriteMarket.setMarketId(mid);
        favoriteMarket.setType(market.getType());
        favoriteMarket.setUid(TokenUtil.getUid());
        if (BooleanUtils.isTrue(favoriteMarketService.saveFavorite(favoriteMarket))) {
            return BaseResponse.success("收藏成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "收藏失败");
    }

    @ApiOperation("获取收藏市场")
    @GetMapping("/favorite/list")
    public BaseResponse<Object> listFavoriteMarket() {
        List<Long> marketIds = favoriteMarketService.listColumnValues("market_id", new Query().eq("user_id", TokenUtil.getUid()), Long.class);
        List<Market> markets = marketService.listByCollection("id", marketIds);
        return BaseResponse.success(markets);
    }

    @ApiOperation("取消收藏")
    @PostMapping("/favorite/delete")
    public BaseResponse<Object> deleteFavorite(@NotNull Long mid) {
        String marketName = marketService.getColumnValue("name", new Query().eq("id", mid), String.class);
        if (StringUtils.isEmpty(marketName)) {
            return BaseResponse.fail(ResponseCodes.QUERY_NULL_ERROR, "无此市场");
        }
        Query query = new Query();
        query.eq("market_id", mid);
        query.eq("user_id", TokenUtil.getUid());
        if (BooleanUtils.isTrue(favoriteMarketService.deleteFavorite(query))) {
            return BaseResponse.success("取消收藏成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "取消收藏失败");
    }

    @ApiOperation("市场的深度数据")
    @GetMapping("/depth")
    public BaseResponse<Object> getDepthData(@NotBlank String symbol, @NotBlank String depth) {
        Market market = marketService.getByQuery(new Query().eq("symbol", symbol));
        DepthsVo depthsVo = new DepthsVo();
        BaseResponse<Object> matchFeignClientPlateData = matchFeignClient.getPlateData(symbol);
        if (matchFeignClientPlateData.getCode() != HttpStatus.HTTP_OK) {
            return BaseResponse.fail(ResponseCodes.FAIL, "撮合引擎调用失败");
        }
        Map<String, List<DepthItemVo>> plateMap = (Map<String, List<DepthItemVo>>) matchFeignClientPlateData.getData();
        depthsVo.setAsks(plateMap.get("asks"));
        depthsVo.setBids(plateMap.get("bids"));
        depthsVo.setPrice(market.getOpenPrice());
        depthsVo.setCnyPrice(market.getOpenPrice());
        return BaseResponse.success(depthsVo);
    }
}
