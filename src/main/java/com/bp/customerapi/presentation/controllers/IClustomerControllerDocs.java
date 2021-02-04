package com.bp.customerapi.presentation.controllers;

import com.bp.customerapi.application.commands.CreateCustomerCommand;
import com.bp.customerapi.application.commands.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.ResourceNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "API Customers", description = "Customer management")
public interface IClustomerControllerDocs {

    @Operation(summary = "To create a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully."),
            @ApiResponse(responseCode = "400", description = "A customer already registered with same cpf.")
    })
    ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerCommand command) throws CustomerAlreadyRegisteredException;

    @Operation(summary = "To update a Customer by a given valid id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer updated successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found."),
            @ApiResponse(responseCode = "400", description = "A customer already registered with same cpf."),
            @ApiResponse(responseCode = "400", description = "This Customer already registered.")
    })
    ResponseEntity<?> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerCommand command) throws BusinessException, ResourceNotFoundException, CustomerNotFoundException;

    @Operation(summary = "To return a Customer by a given valid id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    CustomerResult getCustomerById(@PathVariable String id) throws CustomerNotFoundException;

    @Operation(summary = "Return a Customers list with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers list."),
    })
    PageResponse<CustomerResult> getCustomers(Pageable pageable);

    @Operation(summary = "Return a Customers list without pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers list."),
    })
    List<CustomerResult> getCustomerssWithoutPagination();

    @Operation(summary = "To delete a Customer found by a given valid id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    void deleteCustomer(@PathVariable String id) throws CustomerNotFoundException, ResourceNotFoundException;


}
