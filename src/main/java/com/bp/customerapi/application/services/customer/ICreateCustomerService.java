package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;

public interface ICreateCustomerService {
    void execute(CreateCustomerCommand command);
}
