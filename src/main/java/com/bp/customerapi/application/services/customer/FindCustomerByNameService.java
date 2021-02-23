package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import com.bp.customerapi.infrastructure.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCustomerByNameService implements IFindCustomerByNameService {

    private final ICustomerRespository respository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Override
    public PageResponse<CustomerResult> execute(String name, Pageable pageable) {
        var page = respository.findCustomerByName(name,pageable);
        return new PageResponse<>(
                page.getSize(),
                page.getTotalPages(),
                page.getNumber(),
                page.getTotalElements(),
                mapper.toResult(page.getContent()));
    }
}
