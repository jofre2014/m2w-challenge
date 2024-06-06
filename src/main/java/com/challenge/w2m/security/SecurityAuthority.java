package com.challenge.w2m.security;

import com.challenge.w2m.model.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final transient Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName().toString();
    }
}
