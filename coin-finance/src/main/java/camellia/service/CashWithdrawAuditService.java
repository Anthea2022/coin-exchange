package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.CashWithdraw;
import camellia.domain.CashWithdrawAudit;
import camellia.exception.BusinessException;
import camellia.mapper.AccountMapper;
import camellia.mapper.CashWithdrawAuditMapper;
import camellia.mapper.CashWithdrawMapper;
import cn.hutool.core.util.ObjectUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 墨染盛夏
 * @version 2023/12/31 18:09
 */
@Service
public class CashWithdrawAuditService extends BaseService<CashWithdrawAudit, Long, CashWithdrawAuditMapper> {
    @Autowired
    private CashWithdrawAuditMapper cashWithdrawAuditMapper;

    @Autowired
    private CashWithdrawMapper cashWithdrawMapper;

    @Autowired
    private AccountService accountService;

    @CreateCache(name = "CASH_WITHDRAW_LOCK", expire = 100, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.BOTH)
    private Cache<String, String> cache;

    public Boolean saveAudit(CashWithdrawAudit cashWithdrawAudit) {
        Long orderId = cashWithdrawAudit.getOrderId();
        boolean frag = cache.tryLockAndRun(orderId + "", 300, TimeUnit.SECONDS, () -> {
            Long id = cashWithdrawAuditMapper.getColumnValue("id", new Query().eq("order_id", orderId), Long.class);

            if (!ObjectUtil.isEmpty(id)) {
                throw new BusinessException(ResponseCodes.FAIL, "该记录已审核");
            }
            int result = cashWithdrawAuditMapper.saveIgnoreNull(cashWithdrawAudit);
            if (result <= 0) {
                throw new BusinessException(ResponseCodes.SYSTEM_ERROR, "记录保存失败");
            }
            CashWithdraw cashWithdraw = new CashWithdraw();
            cashWithdraw.setId(orderId);
            cashWithdraw.setStatus(cashWithdrawAudit.getStatus());
            cashWithdraw.setAuditRemark(cashWithdrawAudit.getRemark());
            cashWithdraw.setStep(cashWithdrawAudit.getStep());

            // 不同意审核
            if (cashWithdrawAudit.getStatus().equals(2)) {
                cashWithdrawMapper.updateIgnoreNull(cashWithdraw);
            } else {
                if (BooleanUtils.isFalse(accountService.decrease(cashWithdraw.getUserId(), cashWithdraw.getCoinId(), orderId, cashWithdraw.getNum(),
                        cashWithdraw.getFee(), "充值", "recharge_into", (byte) 1))) {
                    throw new BusinessException(ResponseCodes.FAIL, "入转失败");
                }
                cashWithdrawMapper.updateIgnoreNull(cashWithdraw);
            }
        });
        return frag;
    }
}
