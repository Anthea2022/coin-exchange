package camellia.mapper;

import camellia.domain.entity.FavoriteMarket;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 20:20
 */
@Repository
public interface FavoriteMarketMapper extends CrudMapper<FavoriteMarket, Long> {
}
