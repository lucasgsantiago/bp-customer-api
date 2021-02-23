package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.domain.customer.CpfValidatorService;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCustomerService implements ICreateCustomerService {

    private final ICustomerRespository respository;

    @Override
    public void execute(CreateCustomerCommand command) {
        CpfValidatorService.validate(command.cpf);
        checkIfCustomerIsAlreadyRegisteredByCpf(command.cpf);
        var customer = new Customer(command.id, command.name, command.cpf, command.address);
        respository.save(customer);
    }

    private void checkIfCustomerIsAlreadyRegisteredByCpf(String cpf){
        if(StringHelper.isEmpty(cpf)) throw new IllegalArgumentException("Invalid cpf:" + cpf);
        Optional<Customer> customer = respository.findFirstCustomerByCPF(cpf);
        if(customer.isPresent()){
            throw new CustomerAlreadyRegisteredException(cpf);
        }
    }

}
