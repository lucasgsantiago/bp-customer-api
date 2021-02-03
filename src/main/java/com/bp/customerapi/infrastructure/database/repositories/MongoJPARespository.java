package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoJPARespository extends JpaRepository<Customer, String> {

    Optional<Customer> findFirstByCpf(String cpf);
}
