package com.challenge.w2m.security;

import com.challenge.w2m.exception.AuthorityNotFoundException;
import com.challenge.w2m.model.Authority;
import com.challenge.w2m.model.User;
import com.challenge.w2m.repository.AuthorityRepository;
import com.challenge.w2m.repository.UserRepository;
import com.challenge.w2m.utils.AuthorityName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Value("${user.admin.password}")
    private String adminPassword;

    @Value("${user.user1.password}")
    private String user1Password;

    @Value("${user.user2.password}")
    private String user2Password;

    public Runner(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (this.authorityRepository.count() == 0) {
            this.authorityRepository.saveAll(List.of(
                    new Authority(AuthorityName.ADMIN),
                    new Authority(AuthorityName.READ),
                    new Authority(AuthorityName.WRITE)
            ));
        }

        if (this.userRepository.count() == 0) {
            var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.userRepository.saveAll(List.of(
                            new User("admin", encoders.encode(adminPassword), List.of(getAuthorityByName(AuthorityName.ADMIN))),
                            new User("user1", encoders.encode(user1Password), List.of(getAuthorityByName(AuthorityName.READ))),
                            new User("user2", encoders.encode(user2Password), List.of(getAuthorityByName(AuthorityName.WRITE)))
                    )
            );
        }
    }

    private Authority getAuthorityByName(AuthorityName name) {
        return this.authorityRepository.findByName(name)
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found: " + name));
    }
}
