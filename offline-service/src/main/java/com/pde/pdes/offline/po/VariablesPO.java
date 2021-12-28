package com.pde.pdes.offline.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "变量对象", description = "变量对象")
@TableName("variables")
public class VariablesPO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7217584019192486247L;

	@ApiModelProperty("ID")
	@TableId
	private String id;
	
	@ApiModelProperty("业务主键")
	private String relation_id;
	
	@ApiModelProperty("业务类型1=目录列表,2=目录摘要,3=文件列表,4=文件摘要,5=流程跟踪,6=单据信息")
	private String var_type;
	
	@ApiModelProperty("数据")
	private String content;
}
