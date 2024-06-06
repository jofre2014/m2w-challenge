package com.challenge.w2m.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_elements")
    private Long totalElements;
    private Integer page;
    private Integer limit;
    @JsonProperty("number_of_elements")
    private Integer numberOfElements;
}
