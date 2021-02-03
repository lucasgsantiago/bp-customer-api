package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.Customer;
import com.bp.customerapi.domain.ICustomerRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository implements ICustomerRespository {

    private final MongoJPARespository mongoJPARespository;

    @Override
    public void save(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public Optional<Customer> findFirstCustomerByCPF(String cpf) {
        return mongoJPARespository.findFirstByCpf(cpf);
    }

    @Override
    public Optional<Customer> findCustomerById(String id) {
        return mongoJPARespository.findById(id);
    }

    @Override
    public Page<Customer> findAllCustomer(Pageable pageable) {
        return mongoJPARespository.findAll(pageable);
    }

    @Override
    public List<Customer> findAllCustomerWithoutPagintation() {
        return mongoJPARespository.findAll();
    }

}
