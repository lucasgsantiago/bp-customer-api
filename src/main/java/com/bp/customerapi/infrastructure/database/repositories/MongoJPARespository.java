package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoJPARespository extends MongoRepository<Customer, String> {

    Optional<Customer> findFirstByCpf(String cpf);
}
