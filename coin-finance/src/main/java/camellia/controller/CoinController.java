package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Coin;
import camellia.domain.CoinParam;
import camellia.service.CoinService;
import camellia.util.ImgUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2023/12/27 19:44
 */
@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @ApiOperation("列举")
    @GetMapping("/list/page")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum,
                                         String coinName, Long coinType, Byte status) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.like(StringUtils.hasText(coinName), "name", coinName);
        query.eq(!ObjectUtils.isEmpty(coinType), "type", coinType);
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        return BaseResponse.success(coinService.page(query));
    }

    @ApiOperation("添加")
    @PostMapping("/save")
    public BaseResponse<Object> saveCoin(@RequestBody CoinParam coinParam) {
        String imgUrl = ImgUtil.storeImg(coinParam.getFile());
        Coin coin = new Coin(coinParam);
        if (BooleanUtils.isTrue(coinService.saveCoin(coin))) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "添加失败");
    }

    @ApiOperation("多项删除")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteCoin(Map<String, HashSet<Long>> map) {
        // TODO: 2023/12/27 删除的条件和额外的删除
        HashSet<Long> ids = map.get("ids");
        coinService.deleteByIds(ids);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation("设置状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setStatus(@NotNull Long coinId, @NotNull Byte status) {
        Coin coin = new Coin();
        coin.setId(coinId);
        coin.setStatus(status);
        if (BooleanUtils.isTrue(coinService.setStatus(coin))) {
            return BaseResponse.success("设置成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "设置失败");
    }

    @ApiOperation("通过id获取")
    @GetMapping("/getById")
    public BaseResponse<Object> getById(@NotNull Long cid) {
        return BaseResponse.success(coinService.getBySpecifiedColumns(Arrays.asList("coin_name", "id"), new Query().eq("id", cid)));
    }
}
