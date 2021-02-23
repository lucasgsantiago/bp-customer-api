package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.services.customer.GetAllCustomerWithoutPaginationService;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Get All Customer Without Pagination Service")
public class GetAllCustomerWithoutPaginationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private GetAllCustomerWithoutPaginationService getAllCustomerWithoutPaginationService;

    @Test
    @DisplayName("should return a list of customers successfully")
    void getAllCustomers_Success() {

        var customers = CustomerCreator.createListOfCustomers();
        var expectedNumberOfCustomers = customers.size();

        when(customerRepository.findAllCustomerWithoutPagintation()).thenReturn(customers);

        var expectedCustomers = getAllCustomerWithoutPaginationService.execute();

        assertThat(expectedCustomers, is(not(empty())));
        assertThat(expectedCustomers.size(), is(expectedNumberOfCustomers));
    }

    @Test
    @DisplayName("should return a empty list of customers successfully")
    void getAllCustomers_Empty_Success() {

        var emptyListOfCustomers = CustomerCreator.createEmptyListOfCustomers();
        var expectedNumberOfCustomers = 0;

        when(customerRepository.findAllCustomerWithoutPagintation()).thenReturn(emptyListOfCustomers);

        var expectedCustomers = getAllCustomerWithoutPaginationService.execute();

        assertThat(expectedCustomers, is(notNullValue()));
        assertThat(expectedCustomers.size(), is(expectedNumberOfCustomers));
    }

}