package com.challenge.w2m.dto;

import com.challenge.w2m.enums.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaRequestDto {

    private String title;

    @JsonProperty("media_type")
    private MediaType mediaType;
}
