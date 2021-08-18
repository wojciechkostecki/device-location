package pl.wojciechkostecki.devicelocation.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wojciechkostecki.devicelocation.model.AppUser;
import pl.wojciechkostecki.devicelocation.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, AppUser>{

    default UserDTO toDto(UserDetails user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }
}
