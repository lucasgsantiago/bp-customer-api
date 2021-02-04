package com.bp.customerapi.presentation.controllers;

import com.bp.customerapi.application.commands.CreateCustomerCommand;
import com.bp.customerapi.application.commands.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.ResourceNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.application.queries.results.SuccessResult;
import com.bp.customerapi.application.services.ICustomerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/"+ CustomerController.RESOURCE)
public class CustomerController implements IClustomerControllerDocs {

    static final String RESOURCE = "customers";
    private final ICustomerService service;

    public CustomerController(ICustomerService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(RESOURCE)
    public PageResponse<CustomerResult> getCustomers(@PageableDefault(value = 20, sort = "name",size = 20,page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return service.getAllCustomers(pageable);
    }

    @GetMapping("/all")
    @Cacheable(RESOURCE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResult> getCustomerssWithoutPagination(){
        return service.getAllCustomersWithoutPagination();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResult getCustomerById(@PathVariable String id) throws CustomerNotFoundException {
        return service.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerCommand command) throws CustomerAlreadyRegisteredException {
        service.createCustumer(command);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/").path(command.id).build().toUri();
        return ResponseEntity.created(locationUri).body(new SuccessResult(command.id,locationUri));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerCommand command) throws BusinessException, ResourceNotFoundException, CustomerNotFoundException {
        command.id = id;
        service.updateCustomer(command);
        var headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public void deleteCustomer(@PathVariable String id) throws CustomerNotFoundException, ResourceNotFoundException {
        service.deleteCustomer(id);
    }

}
