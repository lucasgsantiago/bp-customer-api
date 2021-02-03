package com.bp.customerapi.application.commands;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerCommand {

    public String id = UUID.randomUUID().toString();

    @NotNull
    @Size(max = 60)
    public String name;

    @NotNull
    @Size(min = 11, max = 11)
    public String cpf;

    @NotNull
    @Size(min = 10, max = 254)
    public String address;

}
