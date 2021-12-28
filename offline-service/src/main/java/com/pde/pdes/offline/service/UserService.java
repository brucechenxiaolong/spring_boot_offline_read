package com.pde.pdes.offline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.UserccDTO;
import com.pde.pdes.offline.po.UserPO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<UserPO>{
    SimpleResponse<Object> getUser(HttpServletRequest request, String username, String password);

    SimpleResponse<Object> validateToken(HttpServletRequest request);
}
