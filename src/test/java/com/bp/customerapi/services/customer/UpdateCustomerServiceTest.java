package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.services.customer.UpdateCustomerService;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Update Customer Service")
public class UpdateCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UpdateCustomerService customerService;

    @Test
    @DisplayName("should update customer successfully")
    void updateCustomer_Sucesso() {
        var customer = CustomerCreator.createValidCustomer();

        var command = new UpdateCustomerCommand(customer.getId(),"another name","another address");

        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).save(any());

        try {
            customerService.execute(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

}
