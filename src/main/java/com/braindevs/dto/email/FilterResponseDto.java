package com.braindevs.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterResponseDto<T> {
    private List<T> content;
    private Long totalCount;
}
