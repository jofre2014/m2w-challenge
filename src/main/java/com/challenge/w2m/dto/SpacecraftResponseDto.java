package com.challenge.w2m.dto;

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

    private LocalDate manufactureDate;

    private Integer maxCrew;

    private Double maxSpeed;

    private MediaResponseDto media;
}
