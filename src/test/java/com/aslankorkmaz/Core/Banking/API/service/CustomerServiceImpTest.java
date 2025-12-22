package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerUpdateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.response.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerAlreadyExists;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerNotFoundException;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.service.imp.CustomerServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImpTest {

    @InjectMocks
    private CustomerServiceImp customerServiceImp;

    @Mock
    private ICustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;


    @Test
    void should_get_customer_by_id() {
        // given
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");
        customer.setEmail("Email");
        when(customerRepository.findById(id))
                .thenReturn(Optional.of(customer));

        // when
        CustomerResponse result = customerServiceImp.getCustomerById(id);

        // then
        assertEquals(id, result.getId());
        assertEquals("FirstName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals("Email", result.getEmail());

        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void should_throw_exception_when_customer_id_not_found() {
        // given
        Long id = 1L;
        when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        // when
        CustomerNotFoundException exception =
                assertThrows(CustomerNotFoundException.class,
                        () -> customerServiceImp.getCustomerById(id));

        // then
        Assertions.assertThat(exception.getMessage())
                .isEqualTo("Customer with id " + id + " not found");

    }

    @Test
    @DisplayName("Should create customer and save correct fields")
    void should_create_customer() {
        // given
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setEmail("Email");
        request.setIdentityNumber("IdentityNumber");
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(new Customer());

        // when
        customerServiceImp.createCustomer(request);

        // then
        verify(customerRepository, times(1))
                .save(customerArgumentCaptor.capture());

        Customer savedCustomer = customerArgumentCaptor.getValue();
        assertThat(savedCustomer.getFirstName()).isEqualTo("FirstName");
        assertThat(savedCustomer.getLastName()).isEqualTo("LastName");
        assertThat(savedCustomer.getEmail()).isEqualTo("Email");
        assertThat(savedCustomer.getIdentityNumber()).isEqualTo("IdentityNumber");

    }

    @Test
    @DisplayName("Should throw exception if customer already exists by identity number")
    void should_throw_exception_when_customer_already_exists_by_identity_number() {
        // given
        String identityNumber = "IdentityNumber";
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setIdentityNumber(identityNumber);
        when(customerRepository.existsByIdentityNumber(identityNumber))
                .thenReturn(true);

        // when
        CustomerAlreadyExists exception = assertThrows(CustomerAlreadyExists.class,
                () -> customerServiceImp.createCustomer(request));

        // then
       assertThat(exception.getMessage()).
                isEqualTo("Customer Already Exists with Identity Number: " + identityNumber);
       verify(customerRepository, times(1)).existsByIdentityNumber(identityNumber);
       verify(customerRepository,never()).save(any(Customer.class));
    }

    @Test
    void should_return_customers_list() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");
        customer.setEmail("Email");
        customer.setIdentityNumber("IdentityNumber");
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // when
        List<CustomerResponse> result = customerServiceImp.getAllCustomers();

        // then
        CustomerResponse customerResponse = result.get(0);

        assertThat(customerResponse.getId()).isEqualTo(1L);
        assertThat(customerResponse.getFirstName()).isEqualTo("FirstName");
        assertThat(customerResponse.getLastName()).isEqualTo("LastName");
        assertThat(customerResponse.getEmail()).isEqualTo("Email");
        assertThat(customerResponse.getIdentityNumber()).isEqualTo("IdentityNumber");

        verify(customerRepository,times(1)).findAll();
    }

    @Test
    void should_delete_customer_by_id() {
        // given
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // when
        customerServiceImp.deleteCustomer(id);

        // then
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void should_throw_exception_if_customer_id_not_found_for_delete() {
        // given
        Long id = 1L;
        Mockito.when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        // when
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerServiceImp.deleteCustomer(id));

        // then
        assertThat(exception.getMessage())
                .isEqualTo("Customer with id " + id + " not found");
        verify(customerRepository, never()).delete(any());
    }

    @Test
    void should_update_customer_by_id() {
        // given
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setEmail("Email");
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        CustomerResponse customerResponse = customerServiceImp.updateCustomer(request,id);

        // then
        verify(customerRepository).findById(id);
        verify(customerRepository, times(1))
                .save(customerArgumentCaptor.capture());

        Customer updatedCustomer = customerArgumentCaptor.getValue();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("FirstName");
        assertThat(updatedCustomer.getLastName()).isEqualTo("LastName");
        assertThat(updatedCustomer.getEmail()).isEqualTo("Email");

        assertThat(customerResponse.getFirstName()).isEqualTo("FirstName");
        assertThat(customerResponse.getLastName()).isEqualTo("LastName");
        assertThat(customerResponse.getEmail()).isEqualTo("Email");
    }

}
