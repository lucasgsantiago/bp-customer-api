package com.bp.customerapi.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICustomerRespository {
    Optional<Customer> findFirstCustomerByCPF(String cpf);
    Optional<Customer> findCustomerById(String id);
    Page<Customer> findAllCustomer(Pageable pageable);
    List<Customer> findAllCustomerWithoutPagintation();
}
