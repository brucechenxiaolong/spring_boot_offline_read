package com.pde.pdes.offline.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "用户查询参数", description = "用户查询参数")
@Data
public class UserccDTO implements Serializable {

    @ApiModelProperty(value = "登录名")
    private  String loginname;

    @ApiModelProperty(value = "密码")
    private String password;


}
