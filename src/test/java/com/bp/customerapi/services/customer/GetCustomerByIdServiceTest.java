package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.services.customer.GetCustomerByIdService;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Get Customer By Id Service")
public class GetCustomerByIdServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GetCustomerByIdService getCustomerByIdService;

    @Test
    @DisplayName("should return customer successfully given a valid Id")
    void getCustomerById_Success() {

        var customer = CustomerCreator.createValidCustomer();

        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));

        var expectedCustomer = getCustomerByIdService.execute(customer.getId());

        assertThat(expectedCustomer, is(notNullValue()));
        assertThat(expectedCustomer.getId(), is(equalTo(customer.getId())));
    }

    @Test
    @DisplayName("should thrown CustomerNotFoundException when given a invalid id")
    void getCustomerById_ThrowsCustomerNotFoundException() {

        var invalidCustomerId = UUID.randomUUID().toString();

        when(customerRepository.findCustomerById(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(CustomerNotFoundException.class, () -> getCustomerByIdService.execute(invalidCustomerId));
    }


}
