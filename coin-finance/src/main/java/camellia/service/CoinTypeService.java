package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.CoinType;
import camellia.exception.BusinessException;
import camellia.mapper.CoinTypeMapper;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/23 0:15
 */
@Service
public class CoinTypeService {
    @Autowired
    private CoinTypeMapper coinTypeMapper;

    public PageInfo<CoinType> listPageCoinType(Query query) {
        return coinTypeMapper.page(query);
    }

    public Boolean saveCoinType(CoinType coinType) {
        return coinTypeMapper.saveIgnoreNull(coinType) > 0;
    }

    public Boolean hasSame(CoinType coinType) {
        Long codeId = coinTypeMapper.getColumnValue("id", new Query().eq("code", coinType.getCode()), Long.class);
        if (!ObjectUtils.isEmpty(codeId)) {
            return true;
        }
        Long descriptionId = coinTypeMapper.getColumnValue("id", new Query().eq("description", coinType.getDescription()), Long.class);
        if (!ObjectUtils.isEmpty(descriptionId)) {
            return true;
        }
        return false;
    }

    public Boolean deleteCoinType(Long coinTypeId) {
        if (BooleanUtils.isTrue(hasThis(coinTypeId))) {
            return coinTypeMapper.deleteById(coinTypeId) > 0;
        }
        return false;
    }

    private Boolean hasThis(Long coinTypeId) {
        String code = coinTypeMapper.getColumnValue("code", new Query().eq("id", coinTypeId), String.class);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(ResponseCodes.DELETE_ERROR, "无此数据");
        }
        return true;
    }

    public Boolean setCoinTypeStatus(CoinType coinType) {
        if (BooleanUtils.isTrue(hasThis(coinType.getId()))) {
            return coinTypeMapper.updateIgnoreNull(coinType) > 0;
        }
        return false;
    }
}
