package com.pde.pdes.offline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.pde.pdes.base.controller.BaseController;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.service.VariablesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "变量取值" })
@RequestMapping("/variables")
public class VariablesController implements BaseController{
	
	@Autowired
	private VariablesService variablesService;
	
    @GetMapping("/listMetadataDisplayByTableId/{table_id}/{type}")
    @ApiOperation(value = "根据档案库的ID获取对应的显示规则： 业务类型：0=原文展示字段， 1=目录列表,2=目录摘要,3=文件列表,4=文件摘要")
    public WebAsyncTask<SimpleResponse<?>> listMetadataDisplayByTableId(@PathVariable(value="table_id") String table_id, @PathVariable(value="type") Integer type) {
    	return new WebAsyncTask<>(TIMEOUT_60S, EXECUTOR_NAME, () ->
		    new SimpleResponse<Object>(variablesService.listMetadataDisplayByTableId(table_id,type))
        );
    }

    /**
     * listMetadataDisplayByTableId/T_00219/3
     * @param tableCollection
     * @param type=0
     * @return
     */
//    @ApiOperation(value = "获取原文字段展示")
//    @GetMapping("/listMetadataDisplayByTableId/{tableCollection}/{type}")
//    public WebAsyncTask<SimpleResponse<?>> listMetadataDisplayByTableId(@PathVariable(name = "tableCollection", required = true) String tableCollection, @PathVariable(name = "type", required = true) String type) {
//        return new WebAsyncTask<>(TIMEOUT_30S, EXECUTOR_NAME, () -> {
//            SimpleResponse<?> response = variablesService.listMetadataDisplayByTableId(tableCollection, type);
//            return response;
//        });
//    }

}
