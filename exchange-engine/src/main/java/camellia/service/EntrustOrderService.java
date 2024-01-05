package camellia.service;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.entity.EntrustOrder;
import camellia.domain.entity.Market;
import camellia.domain.param.OrderParam;
import camellia.exception.BusinessException;
import camellia.feign.FinanceFeignClient;
import camellia.mapper.EntrustOrderMapper;
import camellia.mapper.MarketMapper;
import camellia.util.TokenUtil;
import cn.hutool.http.HttpStatus;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 21:17
 */
@Slf4j
@Service
public class EntrustOrderService extends BaseService<EntrustOrder, Long, EntrustOrderMapper> {
    @Autowired
    private EntrustOrderMapper entrustOrderMapper;

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private FinanceFeignClient financeFeignClient;

    public EntrustOrder saveOrder(OrderParam orderParam) {
        Market market = marketMapper.getByQuery(new Query().eq("symbol", orderParam.getSymbol()));
        if (ObjectUtils.isEmpty(market)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此交易对");
        }
        BigDecimal price = orderParam.getPrice().setScale(market.getPriceScale(), RoundingMode.HALF_UP);
        BigDecimal volume = orderParam.getVolume().setScale(market.getNumScale(), RoundingMode.HALF_UP);

        if (volume.compareTo(market.getNumMin()) < 0 || volume.compareTo(market.getNumMax()) > 0) {
            throw new BusinessException(ResponseCodes.FAIL, "交易量不在范围内");
        }
        // 计算成交额度
        BigDecimal mum = price.multiply(volume);
        if(mum.compareTo(market.getTradeMin()) < 0 || mum.compareTo(market.getTradeMax()) > 0) {
            throw new BusinessException(ResponseCodes.FAIL, "交易金额不在范围内");
        }

        // 计算手续费
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal feeRate = BigDecimal.ZERO;
        Byte type = orderParam.getType();
        if (type.equals((byte) 1)) { // 买入
            feeRate = market.getFeeBuy();
            fee = mum.multiply(market.getFeeBuy());
        } else if (type.equals((byte) 2)) { //卖出
            feeRate = market.getFeeSell();
            fee = mum.multiply(market.getFeeSell());
        } else {
            throw new BusinessException(ResponseCodes.FAIL, "类型错误");
        }

        Long uid = TokenUtil.getUid();
        EntrustOrder entrustOrder = new EntrustOrder();
        entrustOrder.setUserId(uid);
        entrustOrder.setPrice(price);
        entrustOrder.setVolume(volume);
        entrustOrder.setAmount(mum);
        entrustOrder.setFee(fee);
        entrustOrder.setFeeRate(feeRate);
        entrustOrder.setStatus((byte) 0);
        entrustOrder.setMarketId(market.getId());
        entrustOrder.setMarketType(market.getType());
        entrustOrder.setMarketName(market.getName());
        entrustOrder.setSymbol(orderParam.getSymbol());
        entrustOrder.setFreeze(entrustOrder.getAmount().add(entrustOrder.getFee())); //冻结余额

        if(entrustOrderMapper.saveIgnoreNull(entrustOrder) > 0) {
            // 用户余额扣减
            BaseResponse<Object> response = financeFeignClient.deduct(market.getBuyCoinId(), null, mum, fee, "扣款", "", type);
            if (response.getCode() != HttpStatus.HTTP_OK) {
                throw new BusinessException(ResponseCodes.FAIL, "调取finance_server错误");
            }
        }
        return entrustOrder;
    }
}
