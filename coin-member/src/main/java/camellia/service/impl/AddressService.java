package camellia.service.impl;

import camellia.domain.Address;
import camellia.mapper.AddressMapper;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/12/8 0:25
 */
@Service
public class AddressService extends BaseService<Address, Long, AddressMapper> {
    @Autowired
    private AddressMapper addressMapper;

    public Boolean addAddress(Address address) {
        // 如果存在重复地址
        return addressMapper.saveIgnoreNull(address) > 0;
    }
}
