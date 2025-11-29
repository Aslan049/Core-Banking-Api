package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.dto.CustomerUpdateRequest;
import com.aslankorkmaz.Core.Banking.API.entity.Customer;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerNotFoundException;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.service.ICustomerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        CustomerResponse customerResponse = new CustomerResponse();
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerCreateRequest, customer);
        if(customer.getCreatedAt() == null) {
            customer.setCreatedAt(LocalDate.now());
        }
        Customer savedCustomer =  customerRepository.save(customer);
        BeanUtils.copyProperties(savedCustomer, customerResponse);
        return customerResponse;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return customerResponse;

    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerResponse customerResponse = new CustomerResponse();
            BeanUtils.copyProperties(customer, customerResponse);
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request, Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        BeanUtils.copyProperties(request, customer,"id","createdAt");
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(updatedCustomer, customerResponse);
        return customerResponse;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        customerRepository.delete(customer);

    }
}
