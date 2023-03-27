package com.example.myproject.service;

import com.example.myproject.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shiju
 * @description service for table [user(User Information)]
 * @createDate 2023-02-20 23:25:06
 */
public interface UserService extends IService<User> {

    /**
     * Sign up a new account
     *
     * @param userAccount    user input: User Account
     * @param userPassword   user input: Password
     * @param validationCode user input: Validation Code
     * @return user ID
     */
    long userRegister(String userAccount, String userPassword, String validationCode, String planetCode);

    /**
     * User log in
     * @param userAccount user input: User Account
     * @param userPassword user input: Password
     * @return return encrypted user information
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * Data masking, remove the password info to prevent data leakage.
     * @param originUser
     * @return
     */
    User gethandledUser(User originUser);

    /**
     * Cancel a user
     * @params:request
     */
    int userLogout(HttpServletRequest request);
}
