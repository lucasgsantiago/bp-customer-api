package com.bp.customerapi.application.queries.results;

import lombok.Value;

import java.util.Date;

@Value
public class CustomerResult {
    private final String id,name,cpf,address;
    private final Date creationDate, updateDate;
}
