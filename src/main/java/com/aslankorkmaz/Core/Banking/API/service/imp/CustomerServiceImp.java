package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.customer.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.dto.customer.CustomerUpdateRequest;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerAlreadyExists;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerNotFoundException;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.service.ICustomerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImp implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final IAccountRepository accountRepository;
    @Autowired
    public CustomerServiceImp(ICustomerRepository customerRepository, IAccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {

       if(customerRepository.existsByIdentityNumber(customerCreateRequest.getIdentityNumber())) {
           throw new CustomerAlreadyExists("Customer Already Exists with Identity Number: "+customerCreateRequest.getIdentityNumber());
       }

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDateTime.now());
        customer.setEmail(customerCreateRequest.getEmail());
        customer.setFirstName(customerCreateRequest.getFirstName());
        customer.setLastName(customerCreateRequest.getLastName());
        customer.setIdentityNumber(customerCreateRequest.getIdentityNumber());

        Customer savedCustomer =  customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));

        return mapToResponse(customer);

    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (Customer customer : customers) {
            mapToResponse(customer);
            customerResponseList.add(mapToResponse(customer));
        }
        return customerResponseList;
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());


        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        customerRepository.delete(customer);
    }


    private static CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setFirstName(customer.getFirstName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setCreatedAt(customer.getCreatedAt());
        customerResponse.setIdentityNumber(customer.getIdentityNumber());
        return customerResponse;
    }
}
