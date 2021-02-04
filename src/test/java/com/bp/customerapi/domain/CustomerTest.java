package com.bp.customerapi.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        this.customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .cpf("01234567890")
                .name("cliente")
                .address("endereço")
                .creationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void update(){
        String expectedName = "edited name";
        String expecteAddress = "edited address";

        this.customer.update(expectedName,expecteAddress);

        assertThat(this.customer).isNotNull();
        assertThat(this.customer.getName()).isEqualTo(expectedName);
        assertThat(this.customer.getAddress()).isEqualTo(expecteAddress);
        assertThat(this.customer.getUpdateDate()).isNotNull();
        assertThat(this.customer.getUpdateDate()).isAfter(this.customer.getCreationDate());
    }

}
