package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.*;
import camellia.domain.param.TradeParam;
import camellia.domain.vo.TradeVo;
import camellia.exception.BusinessException;
import camellia.feign.MemberServiceFeign;
import camellia.mapper.AdminAddressMapper;
import camellia.mapper.AdminBankMapper;
import camellia.mapper.CashRechargeMapper;
import camellia.mapper.CoinMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 18:01
 */
@Service
public class CashRechargeService extends BaseService<CashRecharge, Long, CashRechargeMapper> {
    @Autowired
    private CashRechargeMapper cashRechargeMapper;

    @Autowired
    private CoinMapper coinMapper;

    @Autowired
    private AdminBankMapper adminBankMapper;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    public TradeVo recharge(TradeParam tradeParam) {
        CashRecharge cashRecharge = new CashRecharge();
        cashRecharge.setCoinId(tradeParam.getCoinId());
        Coin coin = coinMapper.getById(tradeParam.getCoinId());
        cashRecharge.setCoinName(coin.getName());
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        cashRecharge.setStep((byte) 1);
        cashRecharge.setUserId(userInfo.getId());
        cashRecharge.setName(userInfo.getUsername());
        cashRecharge.setRealName(userInfo.getRealName());
        // TODO: 2023/12/30 其他属性的计算
        cashRechargeMapper.saveIgnoreNull(cashRecharge);
        TradeVo tradeVo = new TradeVo();
        AdminBank adminBank = loadBalance();
        tradeVo.setStatus((byte) 0);
        tradeVo.setRemark("");
//        tradeVo.setAmount();
        tradeVo.setBankCard(adminBank.getBankCard());
        tradeVo.setBankName(adminBank.getBankName());
        tradeVo.setBankCard(adminBank.getBankCard());
        return tradeVo;
    }

    private AdminBank loadBalance() {
        Long uid = TokenUtil.getUid();
        List<AdminBank> adminBanks = adminBankMapper.list(new Query().eq("user_id", uid));
        if (CollectionUtils.isEmpty(adminBanks)) {
            throw new BusinessException(ResponseCodes.FAIL, "没有发现可用的银行卡");
        }
        int size = adminBanks.size();
        if (size == 1) {
            return adminBanks.get(0);
        }
        return adminBanks.get(new Random().nextInt(size));
    }
}
