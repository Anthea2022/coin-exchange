package camellia.mappers;

import camellia.domain.UserInfo;
import camellia.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 14:44
 */
@Mapper(componentModel = "spring")
public interface UserDtoMappers {
    UserDtoMappers INSTANCE = Mappers.getMapper(UserDtoMappers.class);

    UserDto convertDto(UserInfo source);

    List<UserDto> convertDtoList(List<UserInfo> sources);
}
