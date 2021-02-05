package com.bp.customerapi.services;

import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.InvalidCPFException;
import com.bp.customerapi.application.services.CustomerService;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for Customer Service")
public class CustomerServiceTest {

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository clienteRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("should create customer successfully when formatted valid cpf is given")
    void createCustomer_WithFormattedValidCpf_Success() {
        String formattedValidCpf = "109.948.220-81";
        var command = CustomerCreator.createCommandToBeSaved();
        command.cpf = formattedValidCpf;

        doNothing().when(clienteRepository).save(any());
        when(clienteRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.empty());

        try {
            customerService.createCustumer(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("should create customer successfully when unformatted valid cpf is given")
    void createCustomer_WithUnformattedValidCpf_Success() {

        var command = CustomerCreator.createCommandToBeSaved();

        doNothing().when(clienteRepository).save(any());
        when(clienteRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.empty());

        try {
            customerService.createCustumer(command);
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
        assertThrows(InvalidCPFException.class, () -> customerService.createCustumer(command));
    }

    @Test
    @DisplayName("should thrown CustomerAlreadyRegisteredException when try register a customer with same cpf")
    void createCustomer_ShouldThrownCustomerAlreadyRegisteredException() {

        var command = CustomerCreator.createCommandToBeSaved();
        var customer = customerMapper.toDomain(command);

        when(clienteRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyRegisteredException.class, () -> customerService.createCustumer(command));
    }

    @Test
    @DisplayName("should delete customer successfully")
    void deleteCustomer_Success() {

        var customer = CustomerCreator.createValidCustomer();

        doNothing().when(clienteRepository).delete(any());
        when(clienteRepository.findCustomerById(any())).thenReturn(Optional.of(customer));

        try {
            customerService.deleteCustomer(customer.getId());
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    @DisplayName("should thrown CustomerNotFoundException when try delete a nonexistent customer")
    void deleteCustomer_ShouldThrownCustomerNotFoundException() {

        when(clienteRepository.findCustomerById(any())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("should update customer successfully")
    void updateCustomer_Sucesso() {
        var customer = CustomerCreator.createValidCustomer();

        var command = new UpdateCustomerCommand(customer.getId(),"another name","another address");

        when(clienteRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        doNothing().when(clienteRepository).save(any());

        try {
            customerService.updateCustomer(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

}
