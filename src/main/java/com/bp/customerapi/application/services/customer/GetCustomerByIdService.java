package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCustomerByIdService implements IGetCustomerById {

    private final ICustomerRespository respository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Override
    public CustomerResult execute(String id)  {
        var customer = checkIfCustomerExistsAndReturnHim(id);
        return mapper.toResult(customer);
    }

    private Customer checkIfCustomerExistsAndReturnHim(String id)  {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Invalid Id:" + id);
        return respository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

}
