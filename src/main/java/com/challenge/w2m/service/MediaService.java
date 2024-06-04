package com.challenge.w2m.service;

import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;

import java.util.List;

public interface MediaService {

    void create(MediaRequestDto mediaRequestDto);

    List<MediaResponseDto> findAll();
}
