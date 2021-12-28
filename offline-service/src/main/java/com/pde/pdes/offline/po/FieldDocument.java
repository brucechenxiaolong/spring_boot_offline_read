package com.pde.pdes.offline.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel(value = "档案元数据模型", description = "档案元数据模型")
public class FieldDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	public FieldDocument(String name, String value, int type){
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public FieldDocument(String name, String value){
		this(name, value, 1);
	}
	
	@ApiModelProperty("名称（字段）")
	private String name;
	
	@ApiModelProperty("值")
	private String value;
	
	@ApiModelProperty("类型： 1=varchar 2=Integer 3=Double 4=Long 5=Date 6=Datetime ")
	private int type = 1;
}
