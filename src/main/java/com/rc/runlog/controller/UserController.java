package com.rc.runlog.controller;

import com.rc.runlog.dto.UserRequest;
import com.rc.runlog.dto.UserRespone;
import com.rc.runlog.service.UserService;
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