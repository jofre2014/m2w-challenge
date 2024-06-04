package com.challenge.w2m.security;

import com.challenge.w2m.model.Authority;
import com.challenge.w2m.model.User;
import com.challenge.w2m.repository.AuthorityRepository;
import com.challenge.w2m.repository.UserRepository;
import com.challenge.w2m.utils.AuthorityName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

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
                            new User("admin", encoders.encode("admin"), List.of(this.authorityRepository.findByName(AuthorityName.ADMIN).get())),
                            new User("user1", encoders.encode("user123"), List.of(this.authorityRepository.findByName(AuthorityName.READ).get())),
                            new User("user2", encoders.encode("user999"), List.of(this.authorityRepository.findByName(AuthorityName.WRITE).get()))
                    )
            );
        }
    }
}
