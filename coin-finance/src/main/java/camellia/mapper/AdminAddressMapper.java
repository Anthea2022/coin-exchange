package camellia.mapper;

import camellia.domain.AdminAddress;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 15:14
 */
@Repository
public interface AdminAddressMapper extends CrudMapper<AdminAddress, Long> {
}
