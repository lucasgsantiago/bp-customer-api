package com.bp.customerapi.application.services;

import com.bp.customerapi.application.commands.CreateCustomerCommand;
import com.bp.customerapi.application.commands.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.ResourceNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    void createCustumer(CreateCustomerCommand command) throws CustomerAlreadyRegisteredException;
    void updateCustomer(UpdateCustomerCommand command) throws BusinessException, ResourceNotFoundException, CustomerNotFoundException;
    void deleteCustomer(String id) throws ResourceNotFoundException, CustomerNotFoundException;
    List<CustomerResult> getAllCustomersWithoutPagination();
    PageResponse<CustomerResult> getAllCustomers(Pageable pageable);
    CustomerResult getCustomerById(String id) throws CustomerNotFoundException;
    PageResponse<CustomerResult> findCustomerByName(String name, Pageable pageable);
}
