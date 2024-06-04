package com.challenge.w2m.model;

import com.challenge.w2m.utils.AuthorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "authorities")
public class Authority {

    public Authority(AuthorityName authorityName) {
        this.name = authorityName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName name;
}
