package com.bp.customerapi.application.queries.results;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResult {
    private String correlationId;
    private String message = "Customer saved successfully!";
    private URI href;

    public SuccessResult(String correlationId, URI uri) {
        this.correlationId = correlationId;
        this.href = uri;
    }
}
