package com.bp.customerapi.utils;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.customer.Customer;

import java.time.LocalDateTime;

public class CustomerCreator {

    public static CustomerResult createCustomerToBeReturned(){
        return CustomerResult.builder()
                .id("asdf-1234-fdsa-4123-a1s2")
                .name("Any Customer")
                .address("Any Address")
                .cpf("01234597890")
                .creationDate(LocalDateTime.now().minusDays(1))
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static CreateCustomerCommand createCommandToBeSaved() {
        return new CreateCustomerCommand("Any Customer", "01234597890", "Any Address");
    }

    public static Customer createCustomerToBeSaved(){
        return Customer.builder()
                .id("asdf-1234-fdsa-4123-a1s2")
                .name("Any Customer")
                .address("Any Address")
                .cpf("01234597890")
                .creationDate(LocalDateTime.now().minusDays(1))
                .updateDate(LocalDateTime.now())
                .build();
    }
}
