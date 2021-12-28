package com.pde.pdes.offline.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "用户离线表", description = "用户离线表")
@TableName("usercc")
public class UserPO {
	@TableId
	@ApiModelProperty(value="id")
	private String id;

	@ApiModelProperty(value = "登录名")
	private String loginname;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "用户名称")
	private String username;

}
