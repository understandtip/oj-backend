package com.jackqiu.oj.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户创建请求
 *
 *  
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态
     */
    private Integer userStatus;

    /**
     * 性别
     */
    private Integer gender;

    private static final long serialVersionUID = 1L;
}