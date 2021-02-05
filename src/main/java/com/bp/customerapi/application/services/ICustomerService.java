package com.bp.customerapi.application.services;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.ResourceNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    void createCustumer(CreateCustomerCommand command);
    void updateCustomer(UpdateCustomerCommand command);
    void deleteCustomer(String id);
    List<CustomerResult> getAllCustomersWithoutPagination();
    PageResponse<CustomerResult> getAllCustomers(Pageable pageable);
    CustomerResult getCustomerById(String id);
    PageResponse<CustomerResult> findCustomerByName(String name, Pageable pageable);
}
