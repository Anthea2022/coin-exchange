package camellia.mapper;

import camellia.domain.Coin;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/27 19:43
 */
@Repository
public interface CoinMapper extends CrudMapper<Coin, Long> {
}
