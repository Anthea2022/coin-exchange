package camellia.service;

import camellia.domain.AdminAddress;
import camellia.mapper.AdminAddressMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 15:15
 */
@Service
public class AdminAddressService extends BaseService<AdminAddress, Long, AdminAddressMapper> {
    @Autowired
    private AdminAddressMapper adminAddressMapper;

    public Boolean saveAddress(AdminAddress adminAddress) {
        return adminAddressMapper.saveIgnoreNull(adminAddress) > 0;
    }

    public Boolean deleteAddress(Long addressId) {
        // 如果不存在是否会返回true
        return adminAddressMapper.deleteById(addressId) > 0;
    }
}
