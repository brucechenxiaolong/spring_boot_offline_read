package com.pde.pdes.offline.controller;


import com.pde.pdes.base.controller.BaseController;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.ApproveQueryDTO;
import com.pde.pdes.offline.service.ApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@RestController
@Api(tags = {"呈批单列表"})
@RequestMapping(value = "/approve")
public class ApproveController implements BaseController {
    @Autowired
    private ApproveService approveService;

    @PostMapping(value = "/search")
    @ApiOperation(value = "检索所有审批单列表信息")
    public WebAsyncTask<SimpleResponse<?>> search(@RequestBody ApproveQueryDTO query) {
        return new WebAsyncTask<>(TIMEOUT_60S,() ->
                new SimpleResponse<Object>(approveService.search(query))
        );
    }


}
