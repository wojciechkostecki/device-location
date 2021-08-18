package pl.wojciechkostecki.devicelocation.mapper;

import org.mapstruct.Mapper;
import pl.wojciechkostecki.devicelocation.model.User;
import pl.wojciechkostecki.devicelocation.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{
}
