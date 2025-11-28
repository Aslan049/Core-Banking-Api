package com.aslankorkmaz.Core.Banking.API;

import com.aslankorkmaz.Core.Banking.API.dto.CustomerCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.CustomerResponse;
import com.aslankorkmaz.Core.Banking.API.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class Controller {

    private ICustomerService customerService;
    @Autowired
    public Controller(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerCreateRequest));
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

}
