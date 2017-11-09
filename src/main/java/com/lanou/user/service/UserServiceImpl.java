package com.lanou.user.service;

import com.lanou.user.bean.SysUser;
import com.lanou.user.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * Created by dllo on 17/11/8.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper mapper;


    @Override
    public SysUser findSysByName(String username) {
        return mapper.selectByName(username);
    }
}
