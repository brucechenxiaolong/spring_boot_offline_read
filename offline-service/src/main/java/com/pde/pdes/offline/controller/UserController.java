package com.pde.pdes.offline.controller;

import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.UserccDTO;
import com.pde.pdes.offline.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;

import static com.pde.pdes.base.controller.BaseController.TIMEOUT_60S;

@RestController
@Api(tags = {"用户验证"})
@RequestMapping(value="/usercc")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/validateUser")
    @ApiOperation(value = "验证用户")
    public WebAsyncTask<SimpleResponse<?>> validateUser(HttpServletRequest request,String username, String password) {
        return new WebAsyncTask<>(TIMEOUT_60S, () ->
                userService.getUser(request,username,password)
        );
    }


    @PostMapping("/validateToken")
    @ApiOperation(value = "取得用户的基本信息")
    public WebAsyncTask<SimpleResponse<?>> validateToken(HttpServletRequest request) {
        return new WebAsyncTask<>(TIMEOUT_60S, () ->
                userService.validateToken(request)
        );
    }

}
