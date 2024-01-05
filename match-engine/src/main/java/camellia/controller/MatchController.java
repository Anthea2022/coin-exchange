package camellia.controller;


import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.disruptor.OrderEvent;
import camellia.disruptor.OrderEventHandler;
import camellia.domain.model.OrderBook;
import camellia.enums.OrderDirection;
import com.lmax.disruptor.EventHandler;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 16:31
 */
@Api(tags = "撮合order")
@RestController
public class MatchController {
    @Autowired
    private EventHandler<OrderEvent>[] eventHandlers;

    @GetMapping("/order")
    public BaseResponse<Object> getTradeData(@NotBlank String symbol, @NotNull Byte orderDirection) {
        for (EventHandler<OrderEvent> eventHandler : eventHandlers) {
            OrderEventHandler orderEventHandler = (OrderEventHandler) eventHandler;
            OrderBook orderBook = orderEventHandler.getOrderBook();
            if (orderBook.getSymbol().equals(symbol)) {
                return BaseResponse.success(orderBook.getCurrentOrder(OrderDirection.getOrderDirection(orderDirection)));
            }
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "系统繁忙");
    }
}
