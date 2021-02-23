package com.bp.customerapi.controllers;

import com.bp.customerapi.application.queries.results.CustomerResult;
import com.bp.customerapi.application.queries.results.PageResponse;
import com.bp.customerapi.application.services.customer.*;
import com.bp.customerapi.presentation.controllers.CustomerController;
import com.bp.customerapi.utils.CustomerCreator;
import com.bp.customerapi.utils.JsonConvertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Customer Controller")
public class CustomerControllerTest {

    private static final String RESOURCE_API_URL_PATH = "/api/v1/customers";

    private MockMvc mockMvc;

    @Mock
    private CreateCustomerService createCustomerService;
    @Mock
    private DeleteCustomerService deleteCustomerService;
    @Mock
    private GetAllCustomersService getAllCustomersService;


    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("list returns pageable list of customers inside page object when successful")
    void getCustomers_Success(){
        var expecteCustomer = CustomerCreator.createCustomerToBeReturned();
        PageResponse<CustomerResult> customerPage = new PageResponse<>(1,1,1,1,
                Stream.of(expecteCustomer).collect(Collectors.toList()));
        BDDMockito.when(getAllCustomersService.execute(ArgumentMatchers.any())).thenReturn(customerPage);

        PageResponse<CustomerResult> receivedPage = customerController.getCustomers(null);

        assertThat(receivedPage).isNotNull();

        assertThat(receivedPage.getElements())
                .isNotEmpty()
                .hasSize(1);

        assertThat(receivedPage.getElements().get(0).getName()).isEqualTo(expecteCustomer.getName());
    }

    @Test
    @DisplayName("should create customer successfully")
    void createCustomer_Success() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();

        // when
        doNothing().when(createCustomerService).execute(any());

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("should return a BadRequest when try create a customer with empty name")
    void createCustumer_ShouldReturnBadRequest_WhenEmptyName() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.name = null;

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return a BadRequest when try create a customer with empty cpf")
    void createCustumer_ShouldReturnBadRequest_WhenEmptyCpf() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.cpf = null;

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return a BadRequest when try create a customer with empty address")
    void createCustumer_ShouldReturnBadRequest_WhenEmptyAddress() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.address = null;

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return a BadRequest when try create a customer with a short name")
    void createCustumer_ShouldReturnBadRequest_WhenShortName() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.name = "Name";

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return a BadRequest when try create a customer with a short cpf")
    void createCustumer_ShouldReturnBadRequest_WhenShortCpf() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.cpf = "01234";

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("should return a BadRequest when try create a customer with a short address")
    void createCustumer_ShouldReturnBadRequest_WhenShortAddress() throws Exception {
        // given
        var command = CustomerCreator.createCommandToBeSaved();
        command.address = "address";

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return a NoConten when try delete with nonexistent id")
    void deleteCustumer_ShouldReturnNoContent_WhenNonexistentId() throws Exception {
        // given
        var customerId = UUID.randomUUID().toString();

        //when
        doNothing().when(deleteCustomerService).execute(any());

        // then
        mockMvc.perform(delete(RESOURCE_API_URL_PATH + "/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
