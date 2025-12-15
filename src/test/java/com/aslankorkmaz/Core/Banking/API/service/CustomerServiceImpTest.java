package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerCreateRequest;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImpTest {

    @InjectMocks
    private CustomerServiceImp customerServiceImp;

    @Mock
    private ICustomerRepository customerRepository;


    @Test
    void should_get_customer_by_id() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        customerServiceImp.getCustomerById(id);
        verify(customerRepository).findById(id);

    }

    @Test
    void should_throw_exception_when_customer_id_not_found() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerServiceImp.getCustomerById(id));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Customer with id " + id + " not found");
    }

    @DisplayName("Should create customer and save correct fields")
    @Test
    void should_create_customer() {
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setEmail("Email");
        request.setIdentityNumber("IdentityNumber");
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        customerServiceImp.createCustomer(request);

        verify(customerRepository).save(captor.capture());

        Customer savedCustomer = captor.getValue();
        Assertions.assertThat(savedCustomer.getFirstName()).isEqualTo("FirstName");
        Assertions.assertThat(savedCustomer.getLastName()).isEqualTo("LastName");
        Assertions.assertThat(savedCustomer.getEmail()).isEqualTo("Email");
    }

    @DisplayName("Should throw exception if customer already exists by identity number")
    @Test
    void should_throw_exception_when_customer_already_exists() {
        String identityNumber = "IdentityNumber";
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setIdentityNumber(identityNumber);
        Mockito.when(customerRepository.existsByIdentityNumber(identityNumber)).thenReturn(true);
        CustomerAlreadyExists exception = assertThrows(CustomerAlreadyExists.class, () -> customerServiceImp.createCustomer(request));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Customer Already Exists with Identity Number: "+identityNumber);
        verify(customerRepository,never()).save(Mockito.any(Customer.class));
    }

    @Test
    void Should_return_customers_list() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");
        customer.setEmail("Email");
        customer.setIdentityNumber("IdentityNumber");


        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerResponse> result = customerServiceImp.getAllCustomers();

        CustomerResponse customerResponse = result.get(0);

        Assertions.assertThat(customerResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(customerResponse.getFirstName()).isEqualTo("FirstName");
        Assertions.assertThat(customerResponse.getLastName()).isEqualTo("LastName");
        Assertions.assertThat(customerResponse.getEmail()).isEqualTo("Email");
        Assertions.assertThat(customerResponse.getIdentityNumber()).isEqualTo("IdentityNumber");

        verify(customerRepository,times(1)).findAll();
    }

    @Test
    void should_delete_customer_by_id() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        customerServiceImp.deleteCustomer(id);
        verify(customerRepository).delete(customer);
    }

    @Test
    void should_throw_exception_if_customer_id_not_found_for_delete() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerServiceImp.deleteCustomer(id));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Customer with id " + id + " not found");
        verify(customerRepository,never()).delete(customer);
    }

}
