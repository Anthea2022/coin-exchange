package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.entity.Market;
import camellia.exception.BusinessException;
import camellia.feign.CoinFeignServer;
import camellia.mapper.MarketMapper;
import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 12:15
 */
@Slf4j
@Service
public class MarketService extends BaseService<Market, Long, MarketMapper> {
    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private CoinFeignServer coinFeignServer;

    public Boolean saveMarket(Market market) {
        checkCoin(market.getBuyCoinId());
        checkCoin(market.getSellCoinId());
        return marketMapper.saveIgnoreNull(market) > 0;
    }

    private void checkCoin(Long cid) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(coinFeignServer.getById(cid));
        JSONObject data = (JSONObject) jsonObject.get("data");
        String coinName = (String) data.get("coinName");
        if (StringUtils.isEmpty(coinName)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此种币种");
        }
        Byte status = (Byte) data.get("status");
        if (status.equals((byte) 0)) {
            log.info(cid + " " + coinName +" 为禁用状态");
            throw new BusinessException(ResponseCodes.FAIL, "此币种为禁用状态");
        }
    }

    public Boolean deleteMarket(Long mid) {
        String name = marketMapper.getColumnValue("name", new Query().eq("id", mid), String.class);
        if (StringUtils.hasText(name)) {
            throw new BusinessException(ResponseCodes.FAIL, "无此市场");
        }
        return marketMapper.deleteById(mid) > 0;
    }

    public Boolean updateMarket(Market market) {
        String name = marketMapper.getColumnValue("name", new Query().eq("id", market.getId()), String.class);
        if (StringUtils.hasText(name)) {
            throw new BusinessException(ResponseCodes.FAIL, "无此市场");
        }
        return marketMapper.updateIgnoreNull(market) > 0;
    }
}
