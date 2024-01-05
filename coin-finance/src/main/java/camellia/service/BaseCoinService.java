package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.BaseCoin;
import camellia.exception.BusinessException;
import camellia.mapper.BaseCoinMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 11:37
 */
@Service
public class BaseCoinService extends BaseService<BaseCoin, Long, BaseCoinMapper> {
    @Autowired
    private BaseCoinMapper baseCoinMapper;

    public Boolean saveBaseCoin(BaseCoin baseCoin) {
        if (!CollectionUtils.isEmpty(baseCoinMapper.list(new Query()))) {
            throw new BusinessException(ResponseCodes.FAIL, "已配置基础币");
        }
        return baseCoinMapper.saveIgnoreNull(baseCoin) > 0;
    }

    public Boolean cancelBaseCoin(Long cid) {
        // 是否是基础币
        String name = baseCoinMapper.getColumnValue("name", new Query().eq("value", cid), String.class);
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "该币种不是基础币");
        }
        return baseCoinMapper.deleteByColumn("value", cid) > 0;
    }

    public BaseCoin getBaseCoin() {
        List<BaseCoin> baseCoinList = baseCoinMapper.list(new Query());
        if (CollectionUtils.isEmpty(baseCoinList)) {
            throw new BusinessException(ResponseCodes.FAIL, "请先配置基本币");
        }
        return baseCoinList.get(0);
    }
}
