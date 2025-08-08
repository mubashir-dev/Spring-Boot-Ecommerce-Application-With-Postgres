package com.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class PageResponse<T> {
    private String message;
    private Object data;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;

    public PageResponse(String message) {
        this.message = message;
        this.data = null;
        this.page = null;
        this.size = null;
        this.totalElements = null;
        this.totalPages = null;
        this.last = null;
    }

    public PageResponse(Object data) {
        this.data = data;
    }

    public PageResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }
}
