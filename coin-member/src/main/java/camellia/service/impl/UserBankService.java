package camellia.service.impl;

import camellia.domain.UserBank;
import camellia.mapper.UserBankMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/2 10:52
 */
@Service
public class UserBankService extends BaseService<UserBank, Long, UserBankMapper> {
    @Autowired
    private UserBankMapper userBankMapper;

    public Boolean deleteCard(Long id) {
        if (BooleanUtils.isFalse(isMyCard(id))) {
            return false;
        }
        return userBankMapper.deleteById(id) > 0;
    }

    private Boolean isMyCard(Long id) {
        Long uid = userBankMapper.getColumnValue("user_id", new Query().eq("id", id), Long.class);
        if (!uid.equals(TokenUtil.getUid())) {
            return false;
        }
        return true;
    }

    public Boolean setStatus(UserBank userBank) {
        if (BooleanUtils.isFalse(isMyCard(userBank.getId()))) {
            return false;
        }
        return userBankMapper.updateIgnoreNull(userBank) > 0;
    }

    public List<UserBank> listBank() {
        Long uid = TokenUtil.getUid();
        return userBankMapper.listBySpecifiedColumns(Arrays.asList("id", "remark", "bank_card", "status"), new Query().eq("user_id", uid));
    }

    public UserBank getDetail(Long bankId) {
        return userBankMapper.getBySpecifiedColumns(Arrays.asList("bank_prov", "bank_city", "bank_addr"), new Query().eq("id", bankId));
    }
}
