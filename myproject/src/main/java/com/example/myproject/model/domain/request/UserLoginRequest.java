package com.example.myproject.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request sent by user login
 * @author shiju
 */
@Data
public class UserLoginRequest implements Serializable{

    private static final long serialVersionUID = -3712405954962640939L;
    private String userAccount;
    private String userPassword;
}
