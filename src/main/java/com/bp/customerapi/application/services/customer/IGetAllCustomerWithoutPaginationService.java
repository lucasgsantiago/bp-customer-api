package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.queries.results.CustomerResult;

import java.util.List;

public interface IGetAllCustomerWithoutPaginationService {
    List<CustomerResult> execute();
}
