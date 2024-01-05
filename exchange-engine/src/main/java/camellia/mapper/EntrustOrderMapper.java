package camellia.mapper;

import camellia.domain.entity.EntrustOrder;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 21:16
 */
@Repository
public interface EntrustOrderMapper extends CrudMapper<EntrustOrder, Long> {
}
