package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.AdminBank;
import camellia.domain.CashWithdraw;
import camellia.domain.UserInfo;
import camellia.domain.param.CashSellParam;
import camellia.domain.param.TradeParam;
import camellia.domain.vo.TradeVo;
import camellia.exception.BusinessException;
import camellia.feign.MemberServiceFeign;
import camellia.mapper.AdminBankMapper;
import camellia.mapper.CashWithdrawMapper;
import camellia.mapper.CoinMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 20:09
 */
@Service
public class CashWithdrawService extends BaseService<CashWithdraw, Long, CashWithdrawMapper> {
    @Autowired
    private CashWithdrawMapper cashWithdrawMapper;

    @Autowired
    private CoinMapper coinMapper;

    @Autowired
    private AdminBankMapper adminBankMapper;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    public TradeVo withdraw(CashSellParam cashSellParam) {
        Long uid = TokenUtil.getUid();
        // TODO: 2024/1/1 验证码验证 
        // 验证支付密码
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        if (!userInfo.getPaypassword().equals(cashSellParam.getPayPassword())) {
            throw new BusinessException(ResponseCodes.FAIL, "支付密码错误");
        }
        
        
        CashWithdraw cashWithdraw = new CashWithdraw();
        cashWithdraw.setStep((byte) 1);
        cashWithdraw.setStatus((byte) 0);
        cashWithdraw.setCoinId(userInfo.getId());
        cashWithdraw.setName(userInfo.getUsername());
        cashWithdraw.setRealName(userInfo.getRealName());
        cashWithdraw.setCoinId(cashSellParam.getCoinId());
        cashWithdraw.setCoinName(coinMapper.getColumnValue("name", new Query().eq("id", cashSellParam.getCoinId()), String.class));
        cashWithdrawMapper.saveIgnoreNull(cashWithdraw);
        AdminBank adminBank = loadBalance();
        TradeVo tradeVo = new TradeVo();
//        tradeVo.setAmount();
        tradeVo.setName(adminBank.getHolderName());
        tradeVo.setBankName(adminBank.getBankName());
        tradeVo.setBankCard(adminBank.getBankCard());
        tradeVo.setStatus((byte) 0);
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
