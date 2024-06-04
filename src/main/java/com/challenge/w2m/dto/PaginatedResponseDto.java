package com.challenge.w2m.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDto<T> {
    private List<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer page;
    private Integer limit;
    private Integer numberOfElements;
}
