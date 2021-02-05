package com.bp.customerapi.services;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.services.CustomerService;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.infrastructure.database.repositories.CustomerRepository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
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
    @DisplayName("should create customer successfully")
    void createCustomer_Success() {

        var command = new CreateCustomerCommand("cliente","12345678901","rua tal");

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
    @DisplayName("should thrown CustomerAlreadyRegisteredException when try register a customer with same cpf")
    void createCustomer_ShouldThrownCustomerAlreadyRegisteredException() {

        var command = new CreateCustomerCommand("cliente","12345678901","rua tal");
        var customer = customerMapper.toDomain(command);

        when(clienteRepository.findFirstCustomerByCPF(any())).thenReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyRegisteredException.class, () -> customerService.createCustumer(command));
    }

    @Test
    @DisplayName("should delete customer successfully")
    void deleteCustomer_Success() {

        var customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .cpf("teste@gmail.com")
                .name("customer")
                .creationDate(LocalDateTime.now())
                .build();

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
        var customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .cpf("01234567890")
                .name("any customer")
                .address("any Address")
                .creationDate(LocalDateTime.now())
                .build();

        var command = new UpdateCustomerCommand(customer.getId(),"another name"," another address");

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
