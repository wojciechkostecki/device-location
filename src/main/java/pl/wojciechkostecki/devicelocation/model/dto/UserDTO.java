package pl.wojciechkostecki.devicelocation.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 5, message = "Username requires a minimum of 5 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, message = "Password requires a minimum of 5 characters")
    private String password;
}
