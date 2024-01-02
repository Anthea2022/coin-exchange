package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.Account;
import camellia.domain.AccountDetail;
import camellia.domain.UserInfo;
import camellia.exception.BusinessException;
import camellia.feign.MemberServiceFeign;
import camellia.mapper.AccountDetailMapper;
import camellia.mapper.AccountMapper;
import cn.hutool.core.util.ObjectUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author 墨染盛夏
 * @version 2023/12/29 13:33
 */
@Service
public class AccountService extends BaseService<Account, Long, AccountMapper> {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountDetailMapper accountDetailMapper;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    public Boolean increase(Long userId, Long coinId, Long orderId, BigDecimal num, BigDecimal fee, String remark, String businessType, Byte direction) {
        Long accountId = accountMapper.getColumnValue("id", new Query().eq("user_id", userId).eq("coin_id", coinId), Long.class);
        if (ObjectUtil.isEmpty(accountId)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此账户");
        }
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        AccountDetail accountDetail = new AccountDetail(accountId, userId, coinId, orderId, num, fee, remark, businessType, direction, userInfo.getRealName(), userInfo.getUsername());
        if (BooleanUtils.isFalse(accountDetailMapper.saveIgnoreNull(accountDetail) > 0)) {
            throw new BusinessException(ResponseCodes.FAIL, "充值记录保存失败");
        }
        Account account = accountMapper.getById(accountId);
        BigDecimal balanceAmount = account.getBalanceAmount();
        BigDecimal result = balanceAmount.add(num);
        if (result.compareTo(BigDecimal.ONE) > 0) {
            account.setBalanceAmount(result);
             return accountMapper.updateIgnoreNull(account) > 0;
        }
        throw new BusinessException(ResponseCodes.FAIL, "余额不足");
    }

    public Boolean decrease(Long userId, Long coinId, Long orderId, BigDecimal num, BigDecimal fee, String remark, String businessType, Byte direction) {
        Long accountId = accountMapper.getColumnValue("id", new Query().eq("user_id", userId).eq("coin_id", coinId), Long.class);
        if (ObjectUtil.isEmpty(accountId)) {
            throw new BusinessException(ResponseCodes.QUERY_NULL_ERROR, "无此账户");
        }
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        AccountDetail accountDetail = new AccountDetail(accountId, userId, coinId, orderId, num, fee, remark, businessType, direction, userInfo.getRealName(), userInfo.getUsername());
        if (BooleanUtils.isFalse(accountDetailMapper.saveIgnoreNull(accountDetail) > 0)) {
            throw new BusinessException(ResponseCodes.FAIL, "提现记录保存失败");
        }
        Account account = accountMapper.getById(accountId);
        BigDecimal balanceAmount = account.getBalanceAmount();
        BigDecimal result = balanceAmount.add(num);
    }
}
