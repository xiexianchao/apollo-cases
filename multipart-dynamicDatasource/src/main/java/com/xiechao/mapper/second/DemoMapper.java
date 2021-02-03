package com.xiechao.mapper.second;

import com.xiechao.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {
    List<Demo> selectAllDemo();
}
