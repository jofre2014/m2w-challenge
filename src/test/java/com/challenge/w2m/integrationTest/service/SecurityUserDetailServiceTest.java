package com.challenge.w2m.integrationTest.service;

import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;
import com.challenge.w2m.integrationTest.AbstractIntegrationTest;
import com.challenge.w2m.model.Authority;
import com.challenge.w2m.repository.AuthorityRepository;
import com.challenge.w2m.repository.UserRepository;
import com.challenge.w2m.service.UserService;
import com.challenge.w2m.service.impl.SecurityUserDetailService;
import com.challenge.w2m.utils.AuthorityName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class SecurityUserDetailServiceTest extends AbstractIntegrationTest {

    @Autowired
    private SecurityUserDetailService securityUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void loadUserByUsernameTest() {
        String username = "admin";
        assertThat(securityUserDetailService.loadUserByUsername(username)).isNotNull();

        assertThatThrownBy(() -> securityUserDetailService.loadUserByUsername("nonexistent"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found: nonexistent");
    }

    @Test
    void getAllUsersTest() {
        List<UserDto> users = securityUserDetailService.getAllUsers();
        assertThat(users).isNotEmpty();
    }

    @Test
    void createUserTest() {
        UserRequest userRequest = UserRequest.builder()
                .username("newuser")
                .password("newpassword")
                .authorities(List.of(AuthorityName.WRITE))
                .build();

        securityUserDetailService.create(userRequest);

        Optional<UserDto> createdUserOptional = securityUserDetailService.getAllUsers().stream()
                .filter(userDto -> "newuser".equals(userDto.getUsername()))
                .findFirst();

        assertThat(createdUserOptional).isPresent();
        UserDto createdUser = createdUserOptional.get();
        assertThat(createdUser.getUsername()).isEqualTo("newuser");
        assertThat(createdUser.getAuthorities().stream().findFirst().map(a -> a.getName())).contains(AuthorityName.WRITE);
    }
}
