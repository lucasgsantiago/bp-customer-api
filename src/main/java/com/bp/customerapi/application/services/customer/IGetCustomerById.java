package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.queries.results.CustomerResult;

public interface IGetCustomerById {
    CustomerResult execute(String customerId);
}
