package camellia.controller;

import camellia.common.BaseResponse;
import camellia.service.EntrustOrderService;
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
 * @version 2024/1/3 21:18
 */
@Api(tags = "委托记录")
@RestController
@RequestMapping("/entrust/order")
public class EntrustOrderController {
    @Autowired
    private EntrustOrderService entrustOrderService;

    @ApiOperation("分页列举")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        return BaseResponse.success(entrustOrderService.page(query));
    }

    @ApiOperation("查询当前用户的委托记录")
    @GetMapping("/user/list")
    public BaseResponse<Object> listUserOrder(@NotNull Integer pageSize, @NotNull Integer pageNum, String symbol) {
        Long uid = TokenUtil.getUid();
        Query query = new Query();
        query.eq("user_id", uid);
        query.page(pageNum, pageSize);
        query.eq(StringUtils.hasText(symbol), "symbol", symbol);
        return BaseResponse.success(entrustOrderService.list(query));
    }
}
