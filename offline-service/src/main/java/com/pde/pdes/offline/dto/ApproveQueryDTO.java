package com.pde.pdes.offline.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "呈批单列表查询参数", description = "呈批单列表查询参数")
@Data
public class ApproveQueryDTO implements Serializable {
    private static final long serialVersionUID = 6923436134154384685L;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private int state;



}
