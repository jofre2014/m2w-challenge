package com.challenge.w2m.mapper;

import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;
import com.challenge.w2m.model.Spacecraft;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = MediaMapper.class)
public interface SpacecraftMapper {
    SpacecraftResponseDto toDto(Spacecraft spacecraft);

    Spacecraft toEntity(SpacecraftRequestDto spacecraftRequestDto);
}
