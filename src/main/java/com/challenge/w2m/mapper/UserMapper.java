package com.challenge.w2m.mapper;

import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);
}
