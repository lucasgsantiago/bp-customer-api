package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerMongoJPARespository extends MongoRepository<Customer, String> {

    Optional<Customer> findFirstByCpf(String cpf);
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
