package camellia.mapper;

import camellia.domain.WorkIssue;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 20:54
 */
@Repository
public interface WorkIssueMapper extends CrudMapper<WorkIssue, Long> {
}
