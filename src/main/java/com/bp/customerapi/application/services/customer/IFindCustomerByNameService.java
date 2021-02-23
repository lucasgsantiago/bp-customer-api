package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import org.springframework.data.domain.Pageable;

public interface IFindCustomerByNameService {
    PageResponse<CustomerResult> execute(String name, Pageable pageable);
}
