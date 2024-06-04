package com.challenge.w2m.service;

import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    void create(UserRequest userRequest);
}
