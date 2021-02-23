package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.services.customer.GetAllCustomersService;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Get All Customer Service")
public class GetAllCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GetAllCustomersService getAllCustomersService;

    @Test
    @DisplayName("should return a list of customer inside page successfully")
    void getAllCustomers_Success() {

        var customers = CustomerCreator.createValidPageCustomer();
        var expectedNumberOfCustomers = customers.getSize();

        when(customerRepository.findAllCustomer(any())).thenReturn(customers);

        var expectedCustomers = getAllCustomersService.execute(PageRequest.of(1,1 ));

        assertThat(expectedCustomers, is(notNullValue()));
        assertThat(expectedCustomers.getSize(), is(expectedNumberOfCustomers));
    }

    @Test
    @DisplayName("should return a empty list of customer inside page successfully")
    void getAllCustomers_Empty_Success() {

        var emptyPageCustomer = CustomerCreator.createEmptyPageCustomer();
        var expectedNumberOfCustomers = 0;

        when(customerRepository.findAllCustomer(any())).thenReturn(emptyPageCustomer);

        var expectedCustomers = getAllCustomersService.execute(PageRequest.of(1,1 ));

        assertThat(expectedCustomers, is(notNullValue()));
        assertThat(expectedCustomers.getSize(), is(expectedNumberOfCustomers));
    }

}