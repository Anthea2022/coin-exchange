package camellia.mapper;

import camellia.domain.CoinType;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/23 0:14
 */
@Repository
public interface CoinTypeMapper extends CrudMapper<CoinType, Long> {
}
