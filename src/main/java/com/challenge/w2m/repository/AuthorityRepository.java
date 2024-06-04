package com.challenge.w2m.repository;

import com.challenge.w2m.model.Authority;
import com.challenge.w2m.utils.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}