package com.challenge.w2m.integrationTest.service;

import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;
import com.challenge.w2m.enums.MediaType;
import com.challenge.w2m.integrationTest.AbstractIntegrationTest;
import com.challenge.w2m.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class MediaServiceTest extends AbstractIntegrationTest {

    @Autowired
    private MediaService mediaService;

    @Test
    void findAllMediaTest() {

        List<MediaResponseDto> mediaList = mediaService.findAll();
        assertThat(mediaList).isNotEmpty();
    }

    @Test
    void createMediaTest(){
        MediaRequestDto mediaRequestDto = MediaRequestDto.builder()
                .title("Star Trek")
                .mediaType(MediaType.MOVIE)
                .build();

        mediaService.create(mediaRequestDto);

        List<MediaResponseDto> responses = mediaService.findAll();

        Optional<MediaResponseDto> createdMediaOptional = responses.stream()
                .filter(response -> "Star Trek".equals(response.getTitle()) && MediaType.MOVIE.equals(response.getMediaType()))
                .findFirst();

        assertThat(createdMediaOptional).isPresent();
        MediaResponseDto createdMedia = createdMediaOptional.get();
        assertThat(createdMedia.getTitle()).isEqualTo("Star Trek");
        assertThat(createdMedia.getMediaType()).isEqualTo(MediaType.MOVIE);

    }
}
