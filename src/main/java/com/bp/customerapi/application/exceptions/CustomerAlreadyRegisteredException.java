package com.bp.customerapi.application.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyRegisteredException extends RuntimeException{
    public CustomerAlreadyRegisteredException(String costumerCpf) {
        super(String.format("A customer already registered with cpf: %s.", costumerCpf));
    }
}
