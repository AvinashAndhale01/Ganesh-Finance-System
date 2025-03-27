package com.finance.service;

import lombok.Data;

@Data
public class UserLoginDTO {

	private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
}
