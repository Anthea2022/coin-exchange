package camellia.mapper;

import camellia.domain.Notice;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 0:09
 */
@Repository
public interface NoticeMapper extends CrudMapper<Notice, Long> {
    List<Notice> listTitleAndContent();
}
