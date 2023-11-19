package com.example.lease_by.dto.pagination;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements());

        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    private static class Metadata {
        int page;
        int size;
        int totalPages;
        long totalElements;
    }
}
