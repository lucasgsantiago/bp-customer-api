package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCustomerWithoutPaginationService implements IGetAllCustomerWithoutPaginationService {

    private final ICustomerRespository respository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Override
    public List<CustomerResult> execute(){
        return mapper.toResult(respository.findAllCustomerWithoutPagintation());
    }
}
