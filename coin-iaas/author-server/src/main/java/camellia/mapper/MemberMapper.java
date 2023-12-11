package camellia.mapper;

import camellia.domain.Member;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 18:34
 */
@Repository
public interface MemberMapper extends CrudMapper<Member, Long> {
}
