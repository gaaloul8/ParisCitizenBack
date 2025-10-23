package com.municipalite.paris.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> data;
    private long total;
    private int page;
    private int limit;
    private int totalPages;
    
    public static <T> PageResponse<T> of(List<T> data, long total, int page, int limit) {
        return PageResponse.<T>builder()
                .data(data)
                .total(total)
                .page(page)
                .limit(limit)
                .totalPages((int) Math.ceil((double) total / limit))
                .build();
    }
}


