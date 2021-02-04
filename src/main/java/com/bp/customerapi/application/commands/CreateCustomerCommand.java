package com.bp.customerapi.application.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@NoArgsConstructor
public class CreateCustomerCommand {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String id = UUID.randomUUID().toString();

    @NotNull(message = "name is required")
    @Size(min = 10, max = 60)
    public String name;

    @NotNull(message = "cpf is required")
    @Size(min = 11, max = 11)
    public String cpf;

    @NotNull(message = "address is required")
    @Size(min = 10, max = 254)
    public String address;

    public CreateCustomerCommand(String name, String cpf, String address) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
    }
}
