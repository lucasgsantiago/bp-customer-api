package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.services.customer.DeleteCustomerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Delete Customer Service")
public class DeleteCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DeleteCustomerService customerService;

    @Test
    @DisplayName("should delete customer successfully")
    void deleteCustomer_Success() {

        var customer = CustomerCreator.createValidCustomer();

        doNothing().when(customerRepository).delete(any());
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));

        try {
            customerService.execute(customer.getId());
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("should thrown CustomerNotFoundException when try delete a nonexistent customer")
    void deleteCustomer_ShouldThrownCustomerNotFoundException() {

        when(customerRepository.findCustomerById(any())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.execute(UUID.randomUUID().toString()));
    }

}
