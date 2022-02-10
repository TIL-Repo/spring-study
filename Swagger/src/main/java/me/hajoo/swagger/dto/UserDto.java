package me.hajoo.swagger.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel("User")
@Getter
public class UserDto {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;
}
