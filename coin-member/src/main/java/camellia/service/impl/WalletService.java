package camellia.service.impl;

import camellia.domain.Wallet;
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

    public Boolean addWallet(Wallet wallet) {
        return walletMapper.saveIgnoreNull(wallet) > 0;
    }

    public Boolean deleteWallet(Long id) {
        Long userId = walletMapper.getColumnValue("user_id", new Query().eq("id", id), Long.class);
        Long uid = TokenUtil.getUid();
        if (!userId.equals(uid)) {
            return false;
        }
        return walletMapper.deleteById(id) > 0;
    }
}
