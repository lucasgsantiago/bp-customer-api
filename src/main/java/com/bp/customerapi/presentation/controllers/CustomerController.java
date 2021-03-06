package com.bp.customerapi.presentation.controllers;

import com.bp.customerapi.application.commands.customer.CreateCustomerCommand;
import com.bp.customerapi.application.commands.customer.UpdateCustomerCommand;
import com.bp.customerapi.application.exceptions.BusinessException;
import com.bp.customerapi.application.exceptions.CustomerAlreadyRegisteredException;
import com.bp.customerapi.application.exceptions.CustomerNotFoundException;
import com.bp.customerapi.application.exceptions.ResourceNotFoundException;
import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.application.queries.results.SuccessResult;
import com.bp.customerapi.application.services.customer.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/"+ CustomerController.RESOURCE)
public class CustomerController implements IClustomerControllerDocs {

    static final String RESOURCE = "customers";
    private final ICreateCustomerService createCustomerService;
    private final IUpdateCustomerService updateCustomerService;
    private final IDeleteCustomerService deleteCustomerService;
    private final IFindCustomerByNameService findCustomerByNameService;
    private final IGetCustomerById getCustomerByIdService;
    private final IGetAllCustomerWithoutPaginationService getAllCustomerWithoutPaginationService;
    private final IGetAllCustomersService getAllCustomersService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(RESOURCE)
    @PreAuthorize("hasRole('USER')")
    public PageResponse<CustomerResult> getCustomers(@ParameterObject Pageable pageable){
        return getAllCustomersService.execute(pageable);
    }

    @GetMapping("/all")
    @Cacheable(RESOURCE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<CustomerResult> getCustomerssWithoutPagination(){
        return getAllCustomerWithoutPaginationService.execute();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public CustomerResult getCustomerById(@PathVariable String id) throws CustomerNotFoundException {
        return getCustomerByIdService.execute(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public PageResponse<CustomerResult> findCustomerByName(@RequestParam("name") String name,@ParameterObject Pageable pageable) {
        return this.findCustomerByNameService.execute(name,pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = RESOURCE, allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResult> createCustomer(@Valid @RequestBody CreateCustomerCommand command) throws CustomerAlreadyRegisteredException {
        createCustomerService.execute(command);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/").path(command.id).build().toUri();
        return ResponseEntity.created(locationUri).body(new SuccessResult(command.id,locationUri));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESOURCE, allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerCommand command) throws BusinessException, ResourceNotFoundException, CustomerNotFoundException {
        command.id = id;
        updateCustomerService.execute(command);
        var headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = RESOURCE, allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(@PathVariable String id) throws CustomerNotFoundException, ResourceNotFoundException {
        deleteCustomerService.execute(id);
    }

}
