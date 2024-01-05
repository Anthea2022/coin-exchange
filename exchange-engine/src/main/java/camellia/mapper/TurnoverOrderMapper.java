package camellia.mapper;

import camellia.domain.entity.TurnoverOrder;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 21:43
 */
@Repository
public interface TurnoverOrderMapper extends CrudMapper<TurnoverOrder, Long> {
}
