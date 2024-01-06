package camellia.mapper;

import camellia.domain.entity.TurnoverRecord;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 0:13
 */
@Repository
public interface TurnoverRecordMapper extends CrudMapper<TurnoverRecord, Long> {
}
