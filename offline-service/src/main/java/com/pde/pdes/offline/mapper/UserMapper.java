package com.pde.pdes.offline.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pde.pdes.offline.po.UserPO;

@Mapper
public interface UserMapper extends BaseMapper<UserPO>{

}
