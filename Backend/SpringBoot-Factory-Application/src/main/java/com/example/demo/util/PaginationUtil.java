package com.example.demo.util;

import org.springframework.data.domain.Page;

import com.example.demo.dto.PageResponse;

public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static <T> PageResponse<T> toResponse(
            Page<T> page) {

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}