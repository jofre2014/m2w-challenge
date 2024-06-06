package com.challenge.w2m.dto;

import com.challenge.w2m.enums.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaResponseDto {

    private Long id;

    private String title;

    @JsonProperty("media_type")
    private MediaType mediaType;
}
