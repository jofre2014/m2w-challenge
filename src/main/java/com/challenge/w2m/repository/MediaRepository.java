package com.challenge.w2m.repository;

import com.challenge.w2m.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MediaRepository extends JpaRepository<Media, Long> {
}
