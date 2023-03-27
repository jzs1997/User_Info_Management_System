package com.example.myproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myproject.common.ErrorCode;
import com.example.myproject.constant.UserConstant;
import com.example.myproject.exception.BusinessException;
import com.example.myproject.service.UserService;
import com.example.myproject.model.domain.User;
import com.example.myproject.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Todo
 * Replace all -1 & null with self defined exceptions
 */

/**
 * @author shiju
 * @description Implementation of the database operation Service for the table [User]
 * @createDate 2023-02-20 23:25:06
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * SALT, for password obfuscation.
     */
    private final String SALT = "mysalt";
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // Validation
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "No Parameters");
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Length of User Account should be 4 at lease");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password is not valid");
        }
        if(planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Planet Code length exceed limit");
        }
        //Search for special marks in userAccount
        String validPattern = "[^A-Za-z0-9]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Special Marks in user account");
        }
        //check password
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password and checkPassword do not match");
        }
        // No duplicate
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // No duplicate planet Code
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // Encryption
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // insert data
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean savedResult = this.save(user);
        if (!savedResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // Validation
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        //Search for special marks in userAccount
        String validPattern = "[^A-Za-z0-9]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // Encryption
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // Query the user account and password
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        //Failed to extract user data.
        if(user == null){
            log.info("User login failed, user account or password did not match, or user account did not exist. ");
            return null;
        }

        User handledUser = gethandledUser(user);
        //Record user login status.
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, handledUser);

        //Return masked data
        return handledUser;
    }

    /**
     * Data masking, remove the password info to prevent data leakage.
     * @param originUser
     * @return
     */
    @Override
    public User gethandledUser(User originUser){
        User handledUser = new User();
        handledUser.setId(originUser.getId());
        handledUser.setUsername(originUser.getUsername());
        handledUser.setUserAccount(originUser.getUserAccount());
        handledUser.setAvatar(originUser.getAvatar());
        handledUser.setGender(originUser.getGender());
        handledUser.setPhone(originUser.getPhone());
        handledUser.setEmail(originUser.getEmail());
        handledUser.setScope(originUser.getScope());
        handledUser.setUserStatus(originUser.getUserStatus());
        handledUser.setCreateTime(originUser.getCreateTime());
        handledUser.setPlanetCode(originUser.getPlanetCode());
        return handledUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }
}




