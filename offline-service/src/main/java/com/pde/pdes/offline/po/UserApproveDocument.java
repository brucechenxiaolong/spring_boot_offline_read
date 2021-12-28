package com.pde.pdes.offline.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@NoArgsConstructor
@ApiModel(value = "呈批单数据模型", description = "呈批单数据模型")
@TableName("use_approve")
public class UserApproveDocument implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 3433442167692482434L;

	@ApiModelProperty("单纯的主键")
    private String id;

    /**
     * 呈批单号,如:LY2021070013001
     */
    @ApiModelProperty("呈批单号，-1表示数据还在借阅车尚未形成呈批单")
    private String approve_id = "-1";

    /**
     * 登记单号
     */
    @ApiModelProperty("登记单号")
    private String form_id;


    /**
     * 档案库ID
     */
    @ApiModelProperty("档案库ID")
    private String table_id;

    /**
     * 条目ID
     */
    @ApiModelProperty("条目ID")
    private String entity_id;
    
    /**
     * 条目文本
     */
    @ApiModelProperty("条目文本")
    private String entity_text;

    /**
     * 条目信息
     */
    @ApiModelProperty("条目信息")
    @TableField(exist=false)
    private List<FieldDocument> entity = new ArrayList<>();
    
    public List<FieldDocument> getEntity(){
        if (StringUtils.isEmpty(entity_text))
            return this.entity;
        List<FieldDocument> listFieldDoc = JSONObject.parseObject(entity_text, new TypeReference<List<FieldDocument>>(){});
        return listFieldDoc;
    }

    /**
     * 原文文本
     */
    @ApiModelProperty("原文文本")
    private String files_text;
    
    /**
     * 原文列表
     */
    @ApiModelProperty("原文列表")
    @TableField(exist=false)
    private List<Map<String, String>> files = new ArrayList<>();
    
    public List<Map<String, String>> getFiles(){
    	if (StringUtils.isEmpty(files_text))
    		return this.files;
        List<Map<String, String>> listFileDoc = JSONObject.parseObject(files_text,List.class);
    	return listFileDoc;
    }

    /**
     * 目录阅读权限：0=无权 1=有权
     */
    @ApiModelProperty("目录阅读权限：0=无权 1=有权")
    private int entity_view_permission = 0;

    /**
     * 原文阅读权限：0=无权 1=有权
     */
    @ApiModelProperty("原文阅读权限：0=无权 1=有权")
    private int file_view_permission = 0;

    /**
     * 原文下载权限：0=无权 1=有权
     */
    @ApiModelProperty("原文下载权限：0=无权 1=有权")
    private int file_down_permission = 0;

    /**
     * 原文打印权限：0=无权 1=有权
     */
    @ApiModelProperty("原文打印权限：0=无权 1=有权")
    private int file_print_permission = 0;

    /**
     * 实体出库权限：0=无权 1=有权
     */
    @ApiModelProperty("实体出库权限：0=无权 1=有权")
    private int outstock_permission = 0;

    /**
     * 呈批单有效期限时间戳，0表示永久有效
     */
    @ApiModelProperty("呈批单有效期限时间戳，0表示永久有效")
    private Long expired = 0L;

    /**
     * 呈批单状态该：-1=停用，0=审批中，1=完成，2-驳回
     */
    @ApiModelProperty("呈批单状态该：-1=停用，0=审批中，1=完成，2-驳回")
    private int state;
    
    /**
     * 离线端权限变更标志0=未赋权 1=离线端重新赋权
     * 导出至档案系统时需要过滤状态为1既在离线端权限有变更的条目
     */
    @ApiModelProperty("离线端权限变更标志0=未赋权 1=离线端重新赋权")
    private int modified;

//    /**
//     * 临时授权数据
//     */
//    @ApiModelProperty("临时授权数据")
//    private TempAuthorizationPO temp = new TempAuthorizationPO();


//    /**
//     * 审批过程附件（一般为离线审批产生）
//     */
//    @ApiModelProperty("审批过程附件（一般为离线审批产生）")
//    private List<ApproveFilePO> approveFiles = new ArrayList<>();


    public static void main(String[] args) {
    	String files_text = "[{file_id: \"ED89DBCA2A8941C4B1C5609E124EB0A3\", detail: [], file_txt: \" \"}]"; 
    	List<FileDocument> li = JSONObject.parseObject(files_text, new TypeReference<List<FileDocument>>(){});
    	System.out.println(li);
    }
}
