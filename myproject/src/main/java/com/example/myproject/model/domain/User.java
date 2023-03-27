package com.example.myproject.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * User Information
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * User name
     */
    private String username;

    /**
     * 
     */
    private String userAccount;

    /**
     * user profile icon
     */
    private String avatar;

    /**
     * sex
     */
    private Integer gender;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * status of the account
     */
    private Integer userStatus;

    /**
     * when account is created
     */
    private Date createTime;

    /**
     * Account info update time
     */
    private Date updateTime;

    /**
     * Scope
     */
    private int scope;

    /**
     * if the account is deleted
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * Planet Code
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}