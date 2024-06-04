package com.challenge.w2m.service.impl;

import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;
import com.challenge.w2m.mapper.UserMapper;
import com.challenge.w2m.model.Authority;
import com.challenge.w2m.model.User;
import com.challenge.w2m.repository.AuthorityRepository;
import com.challenge.w2m.repository.UserRepository;
import com.challenge.w2m.security.SecurityUser;
import com.challenge.w2m.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityUserDetailService implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public SecurityUserDetailService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByUsername(username);

        if(user.isPresent()){
            return new SecurityUser(user.get());
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }


    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();

    }

    @Override
    public void create(UserRequest userRequest) {
        var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        List<Authority> authorities = userRequest.getAuthorities().stream()
                .map(authorityName -> this.authorityRepository.findByName(authorityName).get())
                .toList();

        User newUser = new User(userRequest.getUsername(),
                encoders.encode(userRequest.getPassword()),
                authorities);

        this.userRepository.save(newUser);


    }
}
