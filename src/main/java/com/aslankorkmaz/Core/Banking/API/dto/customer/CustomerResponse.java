package com.aslankorkmaz.Core.Banking.API.dto.customer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String email;
    private LocalDate createdAt;
}
