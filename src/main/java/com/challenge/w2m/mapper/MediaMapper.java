package com.challenge.w2m.mapper;

import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;
import com.challenge.w2m.model.Media;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MediaMapper {

    Media toEntity(MediaRequestDto mediaRequestDto);

    MediaResponseDto toDto(Media media);

}
