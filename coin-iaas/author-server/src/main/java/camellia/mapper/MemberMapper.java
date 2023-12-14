package camellia.mapper;

import camellia.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 18:34
 */
@Mapper
public interface MemberMapper {
    @Select("select * from user where email = #{email}")
    Member getLoginInfo(String email);

    @Select("select email from user where id = #{id}")
    String getEmailById(Long id);
}
