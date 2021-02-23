package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.services.customer.FindCustomerByNameService;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Get Customer By Name Service")
public class FindCustomerByNameServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerByNameService findCustomerByNameService;

    @Test
    @DisplayName("should return a list of customer inside page successfully given a valid name")
    void findCustomerByName_Success() {

        var pageCustomer = CustomerCreator.createValidPageCustomer();
        var givenName = pageCustomer.getContent().get(0).getName();
        var expectedNumberOfCustomers = pageCustomer.getSize();

        when(customerRepository.findCustomerByName(any(),any())).thenReturn(pageCustomer);

        var expectedPageCustomer = findCustomerByNameService.execute(givenName, PageRequest.of(1,1 ));

        assertThat(expectedPageCustomer, is(notNullValue()));
        assertThat(expectedPageCustomer.getSize(), is(expectedNumberOfCustomers));
        assertThat(expectedPageCustomer.getElements().get(0).getName(), is(equalTo(givenName)));
    }

    @Test
    @DisplayName("should return a empty list of customer inside page successfully given a invalid name")
    void findCustomerByName_Empty_Success() {

        var emptyPageCustomer = CustomerCreator.createEmptyPageCustomer();
        var expectedNumberOfCustomers = 0;
        var anyName = "Test";

        when(customerRepository.findCustomerByName(any(),any())).thenReturn(emptyPageCustomer);

        var expectedCustomers = findCustomerByNameService.execute(anyName,PageRequest.of(1,1 ));

        assertThat(expectedCustomers, is(notNullValue()));
        assertThat(expectedCustomers.getSize(), is(expectedNumberOfCustomers));
    }

}
