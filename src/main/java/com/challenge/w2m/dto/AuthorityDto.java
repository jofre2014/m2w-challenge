package com.challenge.w2m.dto;

import com.challenge.w2m.utils.AuthorityName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDto {

    private Long id;
    private AuthorityName name;

}
