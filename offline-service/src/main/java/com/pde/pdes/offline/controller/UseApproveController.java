package com.pde.pdes.offline.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.pde.pdes.base.controller.BaseController;
import com.pde.pdes.base.dto.Log;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.UserApproveQueryDTO;
import com.pde.pdes.offline.service.UseApproveService;
import com.pde.pdes.offline.service.VariablesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "呈批单" })
@RequestMapping("/userApprove")
public class UseApproveController implements BaseController{
	
	@Autowired
	UseApproveService userApproveService;
	
	@Autowired
	VariablesService variablesService;
	
	@PostMapping("/search")
	@ApiOperation(value = "根据关键词检索")
	public WebAsyncTask<SimpleResponse<?>> search(@RequestBody UserApproveQueryDTO query) {
		SimpleResponse<Object> obj = new SimpleResponse<Object>(userApproveService.search(query));
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
				obj
		);
	}

	/**
	 * 离线阅览查询
	 * @param query
	 * @return
	 */
//	@PostMapping("/searchAll")
//	@ApiOperation(value = "根据关键词检索")
//	public WebAsyncTask<SimpleResponse<?>> searchAll(@RequestBody UserApproveQueryDTO query) {
//		SimpleResponse<Object> obj = new SimpleResponse<Object>(userApproveService.searchAll(query));
//		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
//				obj
//		);
//	}


	@GetMapping("/searchByID/{ID}")
	@ApiOperation(value = "根据ID查询详情")
	public WebAsyncTask<SimpleResponse<?>> searchByID(@PathVariable String ID) {
		SimpleResponse<Object> obj = new SimpleResponse<Object>(userApproveService.searchByID(ID));
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
				obj
		);
	}

	@ApiOperation(value = "审批")
	@ApiImplicitParam(name = "state", value = "审批类型：1=同意 2=驳回", paramType = "path", required = true)
	@PostMapping("/approve/{approveId}/{state}")
	public WebAsyncTask<SimpleResponse<?>> approve(@PathVariable String approveId,@PathVariable int state) {
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () -> {
			SimpleResponse<?> response = new SimpleResponse<Object>(userApproveService.approve(approveId,state));
			response.getLogs().add( new Log(null, "审批"));
			return response;
		});
	}
	
	@ApiOperation(value = "数据授权")
	@ApiImplicitParam(name = "type", value = "权限类型：0=全部权限 1=目录浏览 2=原文浏览 3=原文打印 4=原文下载 5=实体出库", paramType = "path", required = true)
	@PutMapping("/setPermission/{type}")
	public WebAsyncTask<SimpleResponse<?>> setPermission(@PathVariable Integer type, @RequestBody List<String> ids) {
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () -> {
			SimpleResponse<?> response = userApproveService.addPermission(type, ids);
			response.getLogs().add( new Log(null, "数据授权"));
			return response;
		});
	}
	
	@ApiOperation(value = "取消授权")
	@DeleteMapping("/cancelPermission")
	public WebAsyncTask<SimpleResponse<?>> cancelPermission(@RequestBody List<String> ids) {
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () -> {
			return new SimpleResponse<>(userApproveService.removePermission(ids));
		});
	}
	
	@ApiOperation("查看审批历史")
	@ApiImplicitParam(name = "approveNo", value = "呈批单号", paramType = "path", required = true)
	@GetMapping("/track/{approveNo}")
	public WebAsyncTask<SimpleResponse<?>> flowTrack(@PathVariable String approveNo) {
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
				new SimpleResponse<Object>(variablesService.getJson2MapVar(approveNo, 5))
		);
	}


	@ApiOperation("配置管理_门类管理 判断档案库是否存在原文库")
	@GetMapping("/originalIsExist/{use_approve_id}")
	public WebAsyncTask<?> originalIsExist(@PathVariable String use_approve_id) {
		return new WebAsyncTask<SimpleResponse<?>>(BaseController.TIMEOUT_30S, BaseController.EXECUTOR_NAME, () -> {
			int count = userApproveService.count(use_approve_id);
			return new SimpleResponse<>(true, "执行成功", count > 0);
		});
	}

	/**
	 * /userApprove/listFile
	 * @param use_approve_id
	 * @return
	 */
	@ApiOperation(value = "获取原文  -1所有类型 0=电子档案  1=版式复制件  2=数字复制件")
	@GetMapping("/listFile/{use_approve_id}")
	public WebAsyncTask<SimpleResponse<?>> listFile(@PathVariable(name = "use_approve_id", required = true) String use_approve_id) {
		return new WebAsyncTask<>(TIMEOUT_30S, EXECUTOR_NAME, () -> {
			SimpleResponse<?> response = userApproveService.listFile(use_approve_id);
			return response;
		});
	}

}
