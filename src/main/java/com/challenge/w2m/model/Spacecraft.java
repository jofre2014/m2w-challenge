package com.challenge.w2m.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "spacecraft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spacecraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String model;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "max_crew")
    private Integer maxCrew;

    @Column(name = "max_speed")
    private Double maxSpeed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id")
    private Media media;

}
