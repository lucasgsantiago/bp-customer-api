package com.bp.customerapi.services.customer;

import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.InvalidCPFException;
import com.bp.customerapi.application.services.customer.CreateCustomerService;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Create Customer Service")
public class CreateCustomerServiceTest {

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomerService customerService;

    @Test
    @DisplayName("should create customer successfully when formatted valid cpf is given")
    void createCustomer_WithFormattedValidCpf_Success() {
        String formattedValidCpf = "109.948.220-81";
        var command = CustomerCreator.createCommandToBeSaved();
        command.cpf = formattedValidCpf;

        doNothing().when(customerRepository).save(any());
        when(customerRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.empty());

        try {
            customerService.execute(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("should create customer successfully when unformatted valid cpf is given")
    void createCustomer_WithUnformattedValidCpf_Success() {

        var command = CustomerCreator.createCommandToBeSaved();

        doNothing().when(customerRepository).save(any());
        when(customerRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.empty());

        try {
            customerService.execute(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("should thrown InvalidCPFException when try register a customer with invalid cpf")
    void createCustomer_ShouldThrownInvalidCPFException() {
        var command = CustomerCreator.createCommandToBeSaved();
        command.cpf = "1234567891";
        assertThrows(InvalidCPFException.class, () -> customerService.execute(command));
    }

    @Test
    @DisplayName("should thrown CustomerAlreadyRegisteredException when try register a customer with same cpf")
    void createCustomer_ShouldThrownCustomerAlreadyRegisteredException() {

        var command = CustomerCreator.createCommandToBeSaved();
        var customer = customerMapper.toDomain(command);

        when(customerRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyRegisteredException.class, () -> customerService.execute(command));
    }

}
