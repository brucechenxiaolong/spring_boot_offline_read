package com.pde.pdes.offline.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "离线端原文查询参数", description = "离线端原文查询参数")
@Data
public class ArchivesFileQueryDTO implements Serializable {

    @ApiModelProperty("条目id")
    private String entry_id;

    @ApiModelProperty("原文id")
    private String file_id;

    @ApiModelProperty("条目id")
    private String entity_id;


}
