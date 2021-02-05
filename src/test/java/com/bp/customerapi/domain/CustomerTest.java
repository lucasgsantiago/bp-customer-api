package com.bp.customerapi.domain;

import com.bp.customerapi.utils.CustomerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Customer Model")
public class CustomerTest {

    @Test
    @DisplayName("Should update customer")
    void updateCustomer(){
        var customer = CustomerCreator.createValidCustomer();
        String expectedName = "edited name";
        String expecteAddress = "edited address";

        customer.update(expectedName,expecteAddress);

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(expectedName);
        assertThat(customer.getAddress()).isEqualTo(expecteAddress);
        assertThat(customer.getUpdateDate()).isNotNull();
        assertThat(customer.getUpdateDate()).isAfter(customer.getCreationDate());
    }

}
