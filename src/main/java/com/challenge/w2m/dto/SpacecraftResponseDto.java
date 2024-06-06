package com.challenge.w2m.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpacecraftResponseDto {

    private Long id;

    private String name;

    private String model;

    @JsonProperty("manufacture_date")
    private LocalDate manufactureDate;

    @JsonProperty("max_crew")
    private Integer maxCrew;

    @JsonProperty("max_speed")
    private Double maxSpeed;

    private MediaResponseDto media;
}
