package com.service.impl;

import com.mapper.UserMapper;
import com.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements userService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String queryUserName() {
        return userMapper.selectNameById("1");
    }
}
