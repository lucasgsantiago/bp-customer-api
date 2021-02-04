package com.bp.customerapi.application.queries.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class CustomerResult {
    private final String id,name,cpf,address;
    private final LocalDateTime creationDate, updateDate;
}
