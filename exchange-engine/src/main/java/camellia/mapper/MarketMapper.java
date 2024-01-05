package camellia.mapper;

import camellia.domain.entity.Market;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 12:14
 */
@Repository
public interface MarketMapper extends CrudMapper<Market, Long> {
}
