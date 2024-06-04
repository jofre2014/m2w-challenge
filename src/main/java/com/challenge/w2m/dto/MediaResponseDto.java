package com.challenge.w2m.dto;

import com.challenge.w2m.enums.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaResponseDto {

    private Long id;

    private String title;

    private MediaType mediaType;
}
