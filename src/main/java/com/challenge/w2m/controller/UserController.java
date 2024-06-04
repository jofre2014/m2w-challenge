package com.challenge.w2m.controller;

import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;
import com.challenge.w2m.service.impl.SecurityUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final SecurityUserDetailService userDetailService;

    public UserController(SecurityUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return new ResponseEntity<>(userDetailService.getAllUsers(), HttpStatus.OK);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('WRITE')")
    public void createUser(@RequestBody UserRequest userRequest){
        userDetailService.create(userRequest);

    }


}
