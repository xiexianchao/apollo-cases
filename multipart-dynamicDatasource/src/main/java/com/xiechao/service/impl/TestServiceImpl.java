package com.xiechao.service.impl;

import com.xiechao.mapper.first.UserMapper;
import com.xiechao.mapper.second.DemoMapper;
import com.xiechao.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public void testService() {
        log.info("userMapper select size: " + userMapper.selectAllUser().size());
        log.info("demoMapper select size: " + demoMapper.selectAllDemo().size());
    }
}
