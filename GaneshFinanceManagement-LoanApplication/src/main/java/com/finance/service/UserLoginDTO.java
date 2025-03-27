package com.finance.service;

import lombok.Data;

@Data
public class UserLoginDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

}
