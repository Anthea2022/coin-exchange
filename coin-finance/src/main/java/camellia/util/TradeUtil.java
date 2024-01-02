package camellia.util;

import camellia.common.ResponseCodes;
import camellia.domain.param.CashSellParam;
import camellia.domain.param.TradeParam;
import camellia.exception.BusinessException;

import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2024/1/1 16:10
 */
public class TradeUtil {
    public static void check(TradeParam tradeParam) {
        if (tradeParam.getNum().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ResponseCodes.FAIL, "数量必须大于0");
        }
    }

    public static void sellCheck(CashSellParam cashSellParam) {
        if (cashSellParam.getNum().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ResponseCodes.FAIL, "数量必须大于0");
        }
    }
}
