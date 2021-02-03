package com.xiechao.mapper.second;

import com.xiechao.entity.Demo;
import com.xiechao.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {
    List<Demo> selectAllDemo();
}
