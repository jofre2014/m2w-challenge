package com.challenge.w2m.service;

import com.challenge.w2m.dto.PaginatedResponseDto;
import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;

import java.util.List;

public interface SpacecraftService {

    PaginatedResponseDto<SpacecraftResponseDto> findAll(int page, int limit);

    SpacecraftResponseDto findById(Long id);

    List<SpacecraftResponseDto> findByName(String value);

    void create(SpacecraftRequestDto spacecraftRequestDto);

    SpacecraftResponseDto update(Long id, SpacecraftRequestDto spacecraftRequestDto);

    void delete(Long id);

}
