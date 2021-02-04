package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.Customer;
import com.bp.customerapi.utils.CustomerCreator;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Tests for Customer Repository")
class MongoJPARespositoryTest {

    @Autowired
    private MongoJPARespository respository;
    
    @Test
    @DisplayName("Save customer when Successful")
    void save_Success() {
        var customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        var customerSaved = this.respository.save(customerToBeSaved);

        assertThat(customerSaved).isNotNull();

        assertThat(customerSaved.getId()).isNotNull();

        assertThat(customerSaved.getName()).isEqualTo(customerToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates customer when Successful")
    void updates_Success(){
        var customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        var customerSaved = this.respository.save(customerToBeSaved);

        customerSaved.setName("Updated Name");

        Customer customerUpdated = this.respository.save(customerSaved);

        assertThat(customerUpdated).isNotNull();

        assertThat(customerUpdated.getId()).isNotNull();

        assertThat(customerUpdated.getName()).isEqualTo(customerSaved.getName());
    }

    @Test
    @DisplayName("Delete removes customer when Successful")
    void delete_Success(){
        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        Customer customerSaved = this.respository.save(customerToBeSaved);

        this.respository.delete(customerSaved);

        Optional<Customer> customerOptional = this.respository.findById(customerSaved.getId());

        assertThat(customerOptional).isEmpty();

    }

//    @Test
//    @DisplayName("Find By Name returns list of customer when Successful")
//    void findByName_Successful(){
//        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();
//
//        Customer customerSaved = this.respository.save(customerToBeSaved);
//
//        String name = customerSaved.getName();
//
//        Page<Customer> customers = this.respository.findByNameContainingIgnoreCase(name,PageRequest.of(1,1 ));
//
//        assertThat(customers).isNotEmpty().contains(customerSaved);
//
//    }

    @Test
    @DisplayName("Find By Name returns empty list when no customer is found")
    void findByName_ReturnsEmptyList_WhenCustomerIsNotFound(){
        Page<Customer> customers = this.respository.findByNameContainingIgnoreCase("name",PageRequest.of(1,1 ));

        assertThat(customers).isEmpty();
    }

}