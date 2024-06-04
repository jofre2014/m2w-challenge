package com.challenge.w2m.integrationTest.service;

import com.challenge.w2m.dto.PaginatedResponseDto;
import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;
import com.challenge.w2m.exception.NotFoundException;
import com.challenge.w2m.exception.ServerException;
import com.challenge.w2m.integrationTest.AbstractIntegrationTest;
import com.challenge.w2m.mapper.SpacecraftMapper;
import com.challenge.w2m.model.Media;
import com.challenge.w2m.model.Spacecraft;
import com.challenge.w2m.repository.SpacecraftRepository;
import com.challenge.w2m.service.SpacecraftService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class SpacecraftServiceTest extends AbstractIntegrationTest {

    private static final int PAGE = 0;
    private static final int LIMIT= 10;

    @Autowired
    private SpacecraftRepository spacecreaftRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SpacecraftService spacecraftService;


    private final SpacecraftMapper spacecraftMapper = Mappers.getMapper(SpacecraftMapper.class);

    @BeforeEach
    void setUpTestData() {

        Media media = Media.builder()
                .id(1l)
                .build();

        Spacecraft spacecraftToSave = Spacecraft.builder()
                .name("Millennium Falcon")
                .model("MF-001")
                .manufactureDate(LocalDate.of(1900, 5, 20))
                .media(media)
                .build();


        spacecreaftRepository.save(spacecraftToSave);
    }

    @AfterEach
    void cleanUpDatabase() {
        spacecreaftRepository.deleteAll();
    }


    @Test
    void testCreate_ServerException() {
        SpacecraftRequestDto spacecraftRequestDto = new SpacecraftRequestDto();
        spacecraftRequestDto.setName("Enterprise");
        spacecraftRequestDto.setModel("NCC-1701");

        assertThrows(ServerException.class, () -> spacecraftService.create(spacecraftRequestDto));
    }

    @Test
    void testCreate() {
        SpacecraftRequestDto spacecraftRequestDto = new SpacecraftRequestDto();
        spacecraftRequestDto.setName("Enterprise");
        spacecraftRequestDto.setModel("NCC-1701");
        spacecraftRequestDto.setMediaId(1l);

        spacecraftService.create(spacecraftRequestDto);

        SpacecraftResponseDto responseByName = spacecraftService.findByName("Enterprise").getFirst();

        assertThat(responseByName.getName()).isEqualTo("Enterprise");
    }

    @Test
    void testFindAll() {

        PaginatedResponseDto<SpacecraftResponseDto> spacecraftPaginated = spacecraftService.findAll(PAGE, LIMIT);

        assertThat(spacecraftPaginated.getContent()).isNotEmpty();
        assertThat(spacecraftPaginated.getContent().get(0).getName()).isEqualTo("Millennium Falcon");
    }

    @Test
    void testFindById_NotFound() {
        assertThrows(NotFoundException.class, () -> spacecraftService.findById(999L));
    }

    @Test
    void testFindById() {
        SpacecraftResponseDto responseDtoExpected = getResponseDtoExpected();

        SpacecraftResponseDto response = spacecraftService.findById(responseDtoExpected.getId());
        assertThat(response.getName()).isEqualTo(responseDtoExpected.getName());
    }


    @Test
    void testFindByName() {

        List<SpacecraftResponseDto> response = spacecraftService.findByName("Falcon");

        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getName()).isEqualTo("Millennium Falcon");
    }

    @Test
    void testUpdate_NotFound() {
        SpacecraftRequestDto spacecraftRequestDto = new SpacecraftRequestDto();
        spacecraftRequestDto.setName("Enterprise-D");
        spacecraftRequestDto.setModel("NCC-1701-D");
        spacecraftRequestDto.setMediaId(1l);

        assertThrows(NotFoundException.class, () -> spacecraftService.update(999L, spacecraftRequestDto));
    }

    @Test
    void testUpdate() {

        SpacecraftResponseDto responseDtoExpected = getResponseDtoExpected();

        SpacecraftRequestDto updateRequest = new SpacecraftRequestDto();
        updateRequest.setName("Enterprise-D");
        updateRequest.setModel("NCC-1701-D");
        updateRequest.setMediaId(1l);

        SpacecraftResponseDto response = spacecraftService.update(responseDtoExpected.getId(), updateRequest);

        assertThat(response.getName()).isEqualTo("Enterprise-D");
    }

    @Test
    void testDelete() {

        SpacecraftResponseDto responseDtoExpected = getResponseDtoExpected();

        spacecraftService.delete(responseDtoExpected.getId());

        assertThrows(NotFoundException.class, () -> spacecraftService.findById(responseDtoExpected.getId()));
    }

    private SpacecraftResponseDto getResponseDtoExpected() {
        Page<SpacecraftResponseDto> spacecraftPage = spacecreaftRepository.findAll(PageRequest.of(0, 10))
                .map(spacecraft -> spacecraftMapper.toDto(spacecraft));

        SpacecraftResponseDto responseDtoExpected = spacecraftPage.stream().findFirst().get();
        return responseDtoExpected;
    }

}
