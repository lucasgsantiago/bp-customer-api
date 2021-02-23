package com.bp.customerapi.utils;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

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

    public static Page<Customer> createValidPageCustomer(){
        var expecteCustomer = createValidCustomer();
        return new PageImpl<Customer>(asList(expecteCustomer));
    }

    public static List<Customer> createListOfCustomers(){
        return asList(createValidCustomer());
    }

    public static List<Customer> createEmptyListOfCustomers(){
        return asList();
    }

    public static Page<Customer> createEmptyPageCustomer(){
        return new PageImpl<Customer>(asList());
    }
}
