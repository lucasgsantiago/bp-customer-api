package com.bp.customerapi.infrastructure.database.repositories;

import com.bp.customerapi.domain.customer.Customer;
import com.bp.customerapi.domain.customer.ICustomerRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository implements ICustomerRespository {

    private final CustomerMongoJPARespository customerMongoJPARespository;

    @Override
    public void save(Customer customer) {
        customerMongoJPARespository.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerMongoJPARespository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerMongoJPARespository.delete(customer);
    }

    @Override
    public Optional<Customer> findFirstCustomerByCPF(String cpf) {
        return customerMongoJPARespository.findFirstByCpf(cpf);
    }

    @Override
    public Optional<Customer> findCustomerById(String id) {
        return customerMongoJPARespository.findById(id);
    }

    @Override
    public Page<Customer> findAllCustomer(Pageable pageable) {
        return customerMongoJPARespository.findAll(pageable);
    }

    @Override
    public List<Customer> findAllCustomerWithoutPagintation() {
        return customerMongoJPARespository.findAll();
    }

    @Override
    public Page<Customer> findCustomerByName(String name, Pageable pageable) {
        return customerMongoJPARespository.findByNameContainingIgnoreCase(name,pageable);
    }

}
