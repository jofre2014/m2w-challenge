package com.challenge.w2m.service.impl;

import com.challenge.w2m.config.cache.CacheName;
import com.challenge.w2m.dto.PaginatedResponseDto;
import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;
import com.challenge.w2m.exception.NotFoundException;
import com.challenge.w2m.exception.ServerException;
import com.challenge.w2m.mapper.SpacecraftMapper;
import com.challenge.w2m.model.Media;
import com.challenge.w2m.model.Spacecraft;
import com.challenge.w2m.record.SpacecraftEventRecord;
import com.challenge.w2m.repository.SpacecraftRepository;
import com.challenge.w2m.service.SpacecraftService;
import com.challenge.w2m.utils.JsonUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacecraftServiceImpl implements SpacecraftService {

    private static final Logger log = LoggerFactory.getLogger(SpacecraftServiceImpl.class);
    private final SpacecraftRepository spacecraftRepository;

    private final SpacecraftMapper spacecraftMapper = Mappers.getMapper(SpacecraftMapper.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SpacecraftServiceImpl(SpacecraftRepository spacecraftRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.spacecraftRepository = spacecraftRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PaginatedResponseDto<SpacecraftResponseDto> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<SpacecraftResponseDto> spacecraftList = spacecraftRepository.findAll(pageable)
                .map(spacecraftMapper::toDto);

        return new PaginatedResponseDto<>(spacecraftList.getContent(),
                spacecraftList.getTotalPages(),
                spacecraftList.getTotalElements(),
                page,
                limit,
                spacecraftList.getNumberOfElements()
                );
    }


    @Override
    @Cacheable(value = CacheName.FIND_ALL_CACHE, key = "#id")
    public SpacecraftResponseDto findById(Long id) {
        return spacecraftRepository.findById(id)
                .map(spacecraftMapper::toDto) // Use method reference
                .orElseThrow(() -> new NotFoundException("Spacecraft not found with ID: " + id));
    }

    @Override
    public List<SpacecraftResponseDto> findByName(String value) {
        return spacecraftRepository.findByNameContainingIgnoreCase(value)
                .stream().map(spacecraftMapper::toDto)
                .toList();
    }

    @Override
    public void create(SpacecraftRequestDto spacecraftRequest) {

        var spacecraftModel = Spacecraft.builder()
                .name(spacecraftRequest.getName())
                .model(spacecraftRequest.getModel())
                .manufactureDate(spacecraftRequest.getManufactureDate())
                .maxCrew(spacecraftRequest.getMaxCrew())
                .maxSpeed(spacecraftRequest.getMaxSpeed())
                .media(createMedia(spacecraftRequest.getMediaId()))
                .build();

        try {
            spacecraftRepository.save(spacecraftModel);
        } catch (Exception exception){
            throw new ServerException("Error saving spacecraft in BD");
        }
    }

    private Media createMedia(Long mediaId) {
        return Media.builder()
                .id(mediaId)
                .build();
    }

    @Override
    public SpacecraftResponseDto update(Long id, SpacecraftRequestDto spacecraftRequestDto) {


        Spacecraft existingSpacecraft = spacecraftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Spacecraft not found with ID: " + id));


        existingSpacecraft.setName(spacecraftRequestDto.getName());
        existingSpacecraft.setModel(spacecraftRequestDto.getModel());
        existingSpacecraft.setManufactureDate(spacecraftRequestDto.getManufactureDate());
        existingSpacecraft.setMaxCrew(spacecraftRequestDto.getMaxCrew());
        existingSpacecraft.setMaxSpeed(spacecraftRequestDto.getMaxSpeed());
        existingSpacecraft.setMedia(createMedia(spacecraftRequestDto.getMediaId()));


        Spacecraft updatedSpacecraft;
        try {
            updatedSpacecraft = spacecraftRepository.save(existingSpacecraft);
            updateDeleteSpacecraft(id);
        }catch (Exception exception){
            throw new ServerException("Error updating spacecraft in BD");
        }
        return spacecraftMapper.toDto(updatedSpacecraft);
    }

    @Override
    public void delete(Long id) {
        try {
            spacecraftRepository.deleteById(id);
            updateDeleteSpacecraft(id);
        }catch (Exception e){
            throw new ServerException("Error deleting spacecraft");
        }
    }

    private void updateDeleteSpacecraft(Long id){

        this.kafkaTemplate.send("spacecraft-topic", JsonUtils.toJson(
                new SpacecraftEventRecord(id)
        ));

        log.info("Send event to kafka for ID: {}", id);

    }

}
