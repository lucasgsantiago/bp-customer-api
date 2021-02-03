package com.bp.customerapi.application.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String id) {
        super(String.format("Customer not found with this id: %s.", id));
    }
}
