package me.hajoo.swagger.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.hajoo.swagger.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user", description = "Operations about user")
@RestController
public class UserController {

    @Operation(summary = "create users", description = "create users example")
    @ApiResponse(code = 200, message = "successful operation")
    @PostMapping("/user/createWithList")
    public void createUserList(@RequestBody List<UserDto> userDtos) {
    }

    @Operation(summary = "update user", description = "update user example")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid user supplied"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PutMapping("/users/{username}")
    public void updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
    }

}
