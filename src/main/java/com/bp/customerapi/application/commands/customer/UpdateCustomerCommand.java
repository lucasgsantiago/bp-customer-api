package com.bp.customerapi.application.commands.customer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerCommand {

    public String id;

    @NotNull(message = "name is required")
    @Size(min = 10, max = 60)
    public String name;

    @NotNull(message = "address is required")
    @Size(min = 10, max = 254)
    public String address;

}
