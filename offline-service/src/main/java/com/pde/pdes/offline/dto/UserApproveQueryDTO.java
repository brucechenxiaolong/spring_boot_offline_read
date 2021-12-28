package com.pde.pdes.offline.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "呈批单查询参数", description = "呈批单查询参数")
@Data
public class UserApproveQueryDTO implements Serializable {
    private static final long serialVersionUID = 6923436134154384685L;

    /**
     * 呈批单号
     */
    @ApiModelProperty("呈批单号")
    private String approveId;

    /**
     * 检索关键字
     */
    @ApiModelProperty("检索关键字")
    private String keyword;

    /**
     * 是否仅显示敏感内容
     */
    @ApiModelProperty("是否仅显示敏感内容")
    private boolean showSensitive;

    /**
     * 过滤权限类型：0=全部权限 3=原文打印 4=原文下载 5=实体出库
     */
    @ApiModelProperty("过滤权限类型：0=全部权限 3=原文打印 4=原文下载 5=实体出库")
    private int permissionType;

    /**
     * 审批人ID
     */
//    @ApiModelProperty("审批人ID")
//    private String userId;

    /**
     * 审批状态（指的是当前审批人，并非整个单据的状态）：0=代办，1=已办
     */
//    @ApiModelProperty("审批状态（指的是当前审批人，并非整个单据的状态）：0=代办，1=已办")
//    private Integer approveState;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private int page = 1;

    /**
     * 页数
     */
    @ApiModelProperty("页数")
    private int size = 20;
}
