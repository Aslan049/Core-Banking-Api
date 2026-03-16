package com.aslankorkmaz.Core.Banking.API.mapper;

import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.request.CustomerUpdateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.customer.response.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerCreateRequest customerCreateRequest);
    CustomerResponse toDto(Customer customer);
    List<CustomerResponse> toDtoList(List<Customer> customers);
    void updateCustomer(CustomerUpdateRequest customerUpdateRequest, @MappingTarget Customer customer);
}
