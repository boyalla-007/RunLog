package com.rc.runlog.Controller;

import com.rc.runlog.Dto.UserRequest;
import com.rc.runlog.Dto.UserRespone;
import com.rc.runlog.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public UserRespone createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }
}
