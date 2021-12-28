package com.pde.pdes.offline.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "原文模型", description = "原文模型")
public class FileDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("原文唯一标识")
	private String file_id;

	/**
	 *  原文为动态表结构，此处有K-V结构存储原文
	 */
	@ApiModelProperty("原文详细信息")
	private List<FieldDocument> detail = new ArrayList<>();

	@ApiModelProperty("文件内容")
	private String file_txt = " ";

}
