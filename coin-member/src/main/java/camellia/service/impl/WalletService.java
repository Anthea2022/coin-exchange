package camellia.service.impl;

import camellia.common.ResponseCodes;
import camellia.domain.UserInfo;
import camellia.domain.Wallet;
import camellia.exception.BusinessException;
import camellia.feign.MemberServiceFeign;
import camellia.mapper.WalletMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 0:30
 */
@Service
public class WalletService extends BaseService<Wallet, Long, WalletMapper> {
    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    public Boolean addWallet(Wallet wallet) {
        return walletMapper.saveIgnoreNull(wallet) > 0;
    }

    public Boolean deleteWallet(Long id, String payPsw) {
        Long userId = walletMapper.getColumnValue("user_id", new Query().eq("id", id), Long.class);
        Long uid = TokenUtil.getUid();
        if (!userId.equals(uid)) {
            return false;
        }
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        if (!payPsw.equals(userInfo.getPaypassword())) {
            throw new BusinessException(ResponseCodes.FAIL, "支付密码错误");
        }
        return walletMapper.deleteById(id) > 0;
    }
}
