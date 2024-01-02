package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.AccountDetail;
import camellia.domain.CashRecharge;
import camellia.domain.CashRechargeAudit;
import camellia.exception.BusinessException;
import camellia.mapper.AccountMapper;
import camellia.mapper.CashRechargeAuditMapper;
import camellia.mapper.CashRechargeMapper;
import camellia.util.TokenUtil;
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
 * @version 2023/12/31 18:00
 */
@Service
public class CashRechargeAuditService extends BaseService<CashRechargeAudit, Long, CashRechargeAuditMapper> {
    @Autowired
    private CashRechargeAuditMapper cashRechargeAuditMapper;

    @Autowired
    private CashRechargeMapper cashRechargeMapper;

    @Autowired
    private AccountService accountService;

    @CreateCache(name = "CASH_RECHARGE_LOCK", expire = 100, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.BOTH)
    private Cache<String, String> cache;

    public Boolean saveAudit(CashRechargeAudit cashRechargeAudit) {
        Long orderId = cashRechargeAudit.getOrderId();
        boolean frag = cache.tryLockAndRun(orderId + "", 300, TimeUnit.SECONDS, () -> {
            // 是否已审核
            Long auditId = cashRechargeAuditMapper.getColumnValue("id", new Query().eq("order_id", orderId), Long.class);
            if (!ObjectUtil.isEmpty(auditId)) {
                throw new BusinessException(ResponseCodes.FAIL, "该记录已审核");
            }
            cashRechargeAudit.setAuditUid(TokenUtil.getUid());
            // TODO: 2024/1/1 管理员姓名 
            if (BooleanUtils.isFalse(cashRechargeAuditMapper.saveIgnoreNull(cashRechargeAudit) > 0)) {
                throw new BusinessException(ResponseCodes.FAIL, "记录保存失败");
            }
            CashRecharge cashRecharge = cashRechargeMapper.getById(orderId);
            cashRecharge.setAuditRemark(cashRechargeAudit.getRemark());
            cashRecharge.setStatus(cashRechargeAudit.getStatus());
            cashRecharge.setStep((byte) (cashRecharge.getStep() + 1));

            // 不同意
            if (cashRechargeAudit.getStatus().equals(2)) {
                cashRechargeMapper.updateIgnoreNull(cashRecharge);
            } else {
                if (BooleanUtils.isFalse(accountService.increase(cashRecharge.getUserId(), cashRecharge.getCoinId(), orderId, cashRecharge.getNum(), cashRecharge.getFee(), "充值", "recharge_into", (byte) 1))) {
                    throw new BusinessException(ResponseCodes.FAIL, "出账失败");
                }
                cashRechargeMapper.updateIgnoreNull(cashRecharge);
            }
        });
        return frag;
    }
}
