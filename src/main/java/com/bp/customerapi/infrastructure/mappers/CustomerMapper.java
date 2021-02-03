package com.bp.customerapi.infrastructure.mappers;

import com.bp.customerapi.application.commands.CreateCustomerCommand;
import com.bp.customerapi.application.commands.UpdateCustomerCommand;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.domain.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toDomain(CreateCustomerCommand command);
    Customer toDomain(UpdateCustomerCommand command);
    CustomerResult toResult(Customer source);
    @IterableMapping(elementTargetType = CustomerResult.class)
    List<CustomerResult> toResult(List<Customer> customers);
}
