package com.aslankorkmaz.Core.Banking.API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    private String firstName;
    private String lastName;
    private String identityNumber;
    private String email;
}
