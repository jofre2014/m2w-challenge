package com.challenge.w2m.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class SpacecraftRequestDto {
    private String name;

    private String model;

    private LocalDate manufactureDate;

    @Min(value = 1, message = "Max crew must be at least 1")
    private Integer maxCrew;

    @NotNull(message = "Max speed is mandatory")
    @Min(value = 0, message = "Max speed must be non-negative")
    private Double maxSpeed;

    private Long mediaId;

}
