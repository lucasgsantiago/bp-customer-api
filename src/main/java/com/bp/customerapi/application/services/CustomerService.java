package com.bp.customerapi.application.services;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.InvalidCPFException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.domain.customer.CpfValidatorService;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
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
    public void createCustumer(CreateCustomerCommand command) {
        validateCpf(command.cpf);
        checkIfCustomerIsAlreadyRegisteredByCpf(command.cpf);
        var customer = new Customer(command.id, command.name, command.cpf, command.address);
        respository.save(customer);
    }

    @Override
    public void updateCustomer(UpdateCustomerCommand command) {
        var customer = checkIfCustomerExistsAndReturnHim(command.id);
        customer.update(command.name, command.address);
        respository.save(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        respository.delete(customer);
    }

    @Override
    public CustomerResult getCustomerById(String id)  {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        return mapper.toResult(customer);
    }

    @Override
    public PageResponse<CustomerResult> findCustomerByName(String name, Pageable pageable) {
        var page = respository.findCustomerByName(name,pageable);
        return new PageResponse<>(page.getSize(),page.getTotalPages(),page.getNumber(),page.getTotalElements(),mapper.toResult(page.getContent()));
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

    private void checkIfCustomerIsAlreadyRegisteredByCpf(String cpf){
        if(StringHelper.isEmpty(cpf)) throw new IllegalArgumentException("Invalid cpf:" + cpf);
        Optional<Customer> customer = respository.findFirstCustomerByCPF(cpf);
        if(customer.isPresent()){
            throw new CustomerAlreadyRegisteredException(cpf);
        }
    }

    private Customer checkIfCustomerExistsAndReturnHim(String id)  {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Invalid Id:" + id);
        return respository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void validateCpf(String cpf){
        if(CpfValidatorService.isValid(cpf)) throw new InvalidCPFException();
    }
}
