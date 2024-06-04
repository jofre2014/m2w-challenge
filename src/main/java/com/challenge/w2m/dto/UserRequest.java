package com.challenge.w2m.dto;

import com.challenge.w2m.utils.AuthorityName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String username;
    private String password;
    private List<AuthorityName> authorities;
}
