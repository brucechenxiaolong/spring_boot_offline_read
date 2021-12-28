package com.pde.pdes.offline.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel(value="呈批单列表",description = "呈批单列表")
@TableName("approve")
public class ApproveDocument implements Serializable {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("呈批单号")
    private  String approve_id;

    @ApiModelProperty("审批人")
    private  String username;

    @ApiModelProperty("状态")
    private  String i_state;

}
