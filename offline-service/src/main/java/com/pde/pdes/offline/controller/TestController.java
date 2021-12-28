package com.pde.pdes.offline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.pde.pdes.base.controller.BaseController;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.po.UserPO;
import com.pde.pdes.offline.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "测试" })
@RequestMapping("/offline/test")
public class TestController implements BaseController{
	
	@Autowired
	UserService userService;
	
    @GetMapping("/x1")
	@ApiOperation(value = "测试")
	public WebAsyncTask<SimpleResponse<?>> x1(){

		return new WebAsyncTask<>(BaseController.TIMEOUT_120S, BaseController.EXECUTOR_NAME, () -> {
			UserPO po = new UserPO();
			po.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			boolean result = userService.save(po);
	    	return new SimpleResponse<>(result);
		});
	}

}
