package com.aslankorkmaz.Core.Banking.API.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;

}
