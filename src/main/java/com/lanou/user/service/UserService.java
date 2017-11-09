package com.lanou.user.service;


import com.lanou.user.bean.SysUser;

/**
 * Created by dllo on 17/11/8.
 */
public interface UserService {

    SysUser findSysByName(String username);

}
