package camellia.mapper;

import camellia.domain.BaseCoin;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 11:36
 */
@Repository
public interface BaseCoinMapper extends CrudMapper<BaseCoin, Long> {
}
