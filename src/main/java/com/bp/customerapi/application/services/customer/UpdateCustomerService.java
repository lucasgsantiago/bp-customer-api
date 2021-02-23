package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCustomerService implements IUpdateCustomerService {

    private final ICustomerRespository respository;

    @Override
    public void execute(UpdateCustomerCommand command) {
        var customer = checkIfCustomerExistsAndReturnHim(command.id);
        customer.update(command.name, command.address);
        respository.save(customer);
    }

    private Customer checkIfCustomerExistsAndReturnHim(String id)  {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Invalid Id:" + id);
        return respository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

}
