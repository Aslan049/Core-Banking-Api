package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.entity.Customer;

import java.util.List;

public interface ICustomerService {

    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest);
    public CustomerResponse getCustomerById(Long id);
    public List<CustomerResponse> getAllCustomers();
    public CustomerResponse updateCustomer(CustomerCreateRequest request, Long id);
    public void deleteCustomer(Long id);

}
