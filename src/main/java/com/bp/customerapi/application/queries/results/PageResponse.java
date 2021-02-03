package com.bp.customerapi.application.queries.results;

import lombok.Value;

import java.util.List;

@Value
public class PageResponse<C>{

    private int size,totalPages,pageNumber;
    private long totalElements;
    private final List<C> elements;

}
