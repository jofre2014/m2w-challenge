package com.challenge.w2m.repository;

import com.challenge.w2m.model.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {

    List<Spacecraft> findByNameContainingIgnoreCase(String value);
}
