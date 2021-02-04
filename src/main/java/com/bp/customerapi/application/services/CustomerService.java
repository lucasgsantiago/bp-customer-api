package com.bp.customerapi.application.services;

import com.bp.customerapi.application.commands.CreateCustomerCommand;
import com.bp.customerapi.application.commands.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.domain.Customer;
import com.bp.customerapi.domain.ICustomerRespository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRespository respository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Override
    public void createCustumer(CreateCustomerCommand command) throws CustomerAlreadyRegisteredException {
        checkIfCustomerIsAlreadyRegisteredByCpf(command.cpf);
        var customer = new Customer(command.id, command.name, command.cpf, command.address);
        respository.save(customer);
    }

    @Override
    public void updateCustomer(UpdateCustomerCommand command) throws BusinessException, CustomerNotFoundException {
        var customer = checkIfCustomerExistsAndReturnHim(command.id);
        customer.update(command.name, command.address);
        respository.save(customer);
    }

    @Override
    public void deleteCustomer(String id) throws CustomerNotFoundException {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        respository.delete(customer);
    }

    @Override
    public CustomerResult getCustomerById(String id) throws CustomerNotFoundException {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        return mapper.toResult(customer);
    }

    @Override
    public List<CustomerResult> getAllCustomersWithoutPagination(){
        return mapper.toResult(respository.findAllCustomerWithoutPagintation());
    }

    @Override
    public PageResponse<CustomerResult> getAllCustomers(Pageable pageable) {
        var page = respository.findAllCustomer(pageable);
        return new PageResponse<>(page.getSize(),page.getTotalPages(),page.getNumber(),page.getTotalElements(),mapper.toResult(page.getContent()));
    }

    private void checkIfCustomerIsAlreadyRegisteredByCpf(String cpf) throws CustomerAlreadyRegisteredException {
        if(StringHelper.isEmpty(cpf)) throw new IllegalArgumentException("Invalid cpf:" + cpf);
        Optional<Customer> customer = respository.findFirstCustomerByCPF(cpf);
        if(customer.isPresent()){
            throw new CustomerAlreadyRegisteredException(cpf);
        }
    }

    private Customer checkIfCustomerExistsAndReturnHim(String id) throws CustomerNotFoundException {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Invalid Id:" + id);
        return respository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
