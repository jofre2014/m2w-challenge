package com.challenge.w2m.service.impl;

import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;
import com.challenge.w2m.mapper.MediaMapper;
import com.challenge.w2m.repository.MediaRepository;
import com.challenge.w2m.service.MediaService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper = Mappers.getMapper(MediaMapper.class);

    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public void create(MediaRequestDto mediaRequestDto) {

        mediaRepository.save(mediaMapper.toEntity(mediaRequestDto));

    }

    @Override
    public List<MediaResponseDto> findAll() {
        return mediaRepository.findAll()
                .stream()
                .map(mediaMapper::toDto)
                .toList();
    }
}
