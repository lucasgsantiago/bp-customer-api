package com.bp.customerapi.utils;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerCreator {

    public static CustomerResult createCustomerToBeReturned(){
        return CustomerResult.builder()
                .id("asdf-1234-fdsa-4123-a1s2")
                .name("Any Customer")
                .address("Any Address")
                .cpf("10994822081")
                .creationDate(LocalDateTime.now().minusDays(1))
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static CreateCustomerCommand createCommandToBeSaved() {
        return new CreateCustomerCommand("Any Customer", "10994822081", "Any Address");
    }

    public static Customer createValidCustomer(){
        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .name("Any Customer")
                .address("Any Address")
                .cpf("10994822081")
                .creationDate(LocalDateTime.now().minusDays(1))
                .updateDate(LocalDateTime.now())
                .build();
    }
}
