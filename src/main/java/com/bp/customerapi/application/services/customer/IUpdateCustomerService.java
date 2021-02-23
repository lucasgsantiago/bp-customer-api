package com.bp.customerapi.application.services.customer;

import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;

public interface IUpdateCustomerService {
    void execute(UpdateCustomerCommand command);
}
