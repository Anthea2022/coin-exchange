package camellia.controller;

import camellia.common.BaseResponse;
import camellia.service.TurnoverOrderService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 21:46
 */
@Api(tags = "成交记录")
@RestController
@RequestMapping("/turnover/order")
public class TurnoverOrderController {
    @Autowired
    private TurnoverOrderService turnoverOrderService;

    @ApiOperation("分页列举")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        return BaseResponse.success(turnoverOrderService.page(query));
    }

    @ApiOperation("获取当前用户作为卖方成交记录")
    @GetMapping("/user/list")
    public BaseResponse<Object> listSellUserOrder(@NotNull Integer pageSize, @NotNull Integer pageNum, String symbol) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.eq("sellUserId", TokenUtil.getUid());
        query.eq(StringUtils.hasText(symbol), "symbol", symbol);
        return BaseResponse.success(turnoverOrderService.page(query));
    }

    @ApiOperation("获取当前用户作为买方成交记录")
    @GetMapping("/user/list")
    public BaseResponse<Object> listBuyUserOrder(@NotNull Integer pageSize, @NotNull Integer pageNum, String symbol) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.eq("buyUserId", TokenUtil.getUid());
        query.eq(StringUtils.hasText(symbol), "symbol", symbol);
        return BaseResponse.success(turnoverOrderService.page(query));
    }
}
