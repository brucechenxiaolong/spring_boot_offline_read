package com.pde.pdes.offline.controller;

import com.pde.pdes.base.controller.BaseController;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.ApproveQueryDTO;
import com.pde.pdes.offline.dto.ArchivesFileQueryDTO;
import com.pde.pdes.offline.po.UserPO;
import com.pde.pdes.offline.service.UseApproveService;
import com.pde.pdes.offline.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@Api(tags = { "原文操作" })
@RequestMapping("/archivesFile")
public class ArchivesFileController implements BaseController{
	@Autowired
	UseApproveService userApproveService;

	@ApiOperation(value = "获取文件访问信息")
	@PostMapping("/fileConfig")
	public WebAsyncTask<SimpleResponse<?>> fileConfig(HttpServletRequest request, @RequestBody ArchivesFileQueryDTO archivesFileQueryDTO) {
		return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
				new SimpleResponse<Object>(userApproveService.getFileConfig(archivesFileQueryDTO))
		);
	}

	@ApiOperation(value = "文件查看")
	@GetMapping("/showFile/{file_url}")
	public void showFile(@PathVariable String file_url, HttpServletResponse res){
		userApproveService.showFile(file_url,res);
	}


}
