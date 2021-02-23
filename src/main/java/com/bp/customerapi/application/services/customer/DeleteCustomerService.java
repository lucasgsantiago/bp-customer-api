package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerService implements IDeleteCustomerService {

    private final ICustomerRespository respository;

    @Override
    public void execute(String id) {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        respository.delete(customer);
    }

    private Customer checkIfCustomerExistsAndReturnHim(String id)  {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Invalid Id:" + id);
        return respository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

}
