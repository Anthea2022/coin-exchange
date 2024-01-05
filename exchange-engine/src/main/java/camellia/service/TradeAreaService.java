package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.entity.TradeArea;
import camellia.exception.BusinessException;
import camellia.mapper.TradeAreaMapper;
import com.alibaba.fastjson.JSONObject;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 10:59
 */
@Slf4j
@Service
public class TradeAreaService extends BaseService<TradeArea, Long, TradeAreaMapper> {
    @Autowired
    private TradeAreaMapper tradeAreaMapper;

    public Boolean saveTradeArea(TradeArea tradeArea) {
        checkCoin(tradeArea.getCoinId());
        checkCoin(tradeArea.getBaseCoin());
        return tradeAreaMapper.saveIgnoreNull(tradeArea) > 0;
    }

    private void checkCoin(Long cid) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(cid);
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

    public Boolean deleteTradeArea(Long tid) {
        String name = tradeAreaMapper.getColumnValue("name", new Query().eq("id", tid), String.class);
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此交易地址");
        }
        // TODO: 2024/1/3 是否作为其他表的外键有记录
        return tradeAreaMapper.deleteById(tid) > 0;
    }

    public Boolean updateTradeArea(TradeArea tradeArea) {
        String name = tradeAreaMapper.getColumnValue("name", new Query().eq("id", tradeArea.getId()), String.class);
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此交易地址");
        }
        return tradeAreaMapper.updateIgnoreNull(tradeArea) > 0;
    }
}
