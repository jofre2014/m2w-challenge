package com.challenge.w2m.dto;

import com.challenge.w2m.enums.MediaType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaRequestDto {

    private String title;

    private MediaType mediaType;
}
