package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.response.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerUpdateRequest;

import java.util.List;

public interface ICustomerService {

    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest);
    public CustomerResponse getCustomerById(Long id);
    public List<CustomerResponse> getAllCustomers();
    public CustomerResponse updateCustomer(CustomerUpdateRequest request, Long id);
    public void deleteCustomer(Long id);

}
