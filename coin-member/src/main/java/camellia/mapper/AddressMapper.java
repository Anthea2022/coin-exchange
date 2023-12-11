package camellia.mapper;

import camellia.domain.Address;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/8 0:23
 */
@Repository
public interface AddressMapper extends CrudMapper<Address ,Long> {
}
