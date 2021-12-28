package com.pde.pdes.offline.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author dc
 * @Date 2021/12/12 16:32
 * @Version 1.0
 */
@Data
public class ArchiveFileVO {

    @ApiModelProperty("文件访问地址")
    String file_url;

    @ApiModelProperty("文件访问类型")
    String reader_type;

    @ApiModelProperty("文件后缀")
    String file_suffix;

    @ApiModelProperty("文件权限")
    Map<String, Object> role = new HashMap<>();
    {
        role.put("openFileBtn", "0");                /* 打开文件按钮    */
        role.put("saveBtn", "0");                    /* 保存文件按钮   */
        role.put("exportBtn", "1");                  /* 下载文档     */
        role.put("printBtn", "1");                   /* 打印按钮     */
        role.put("copyBtn", "1");                   /* 拷贝按钮     */
        /* 组织权限数据 */
        role.put("permission", "1");                 /* 权限总开关 1-有权限  0-无任何权限   */
        role.put("head", "0");                       /* LOGO栏     */
        role.put("goToPageBox", "1");                /* 跳转指定页框   */
        role.put("zoomPageBox", "1");                /* 页面缩放     */
        role.put("pageLayoutBtn", "1");              /* 页面布局     */
        role.put("handToolBtn", "1");                /* 手型工具     */
        role.put("textSelectBtn", "1");              /* 选择文本工具    */
        role.put("heightLightBtn", "1");             /* 高亮       */
        role.put("underlineBtn", "1");               /* 下划线     */
        role.put("pencilBtn", "1");                  /* 铅笔       */
        role.put("drawingAnnotBtn", "1");            /* 其他标注     */
        role.put("commentsBtn", "1");                /* 注释       */
        role.put("elecSignatureBtn", "1");           /* 签章       */
        role.put("checkElecSignatureBtn", "1");      /* 验章       */
        role.put("rotateSwitchBtn", "0");            /* 页面旋转     */
        role.put("toolBarSearchBtn", "1");           /* 查找（工具栏）   */
        role.put("moreBtn", "1");                    /* 更多           */
        role.put("outlineBtn", "0");                 /* 大纲       */
        role.put("thumbnailBtn", "1");               /* 缩略图     */
        role.put("commentListBtn", "1");             /* 注释评论     */
        role.put("searchBtn", "1");                  /* 查找       */
        role.put("semanticTreeBtn", "1");            /* 语义树     */
        role.put("attachmentBtn", "1");              /* 附件       */
        role.put("pageRange", null);            /* 阅读页数控制字段- null:全部可读 |  1-3，5-6  |  None */
        /*
         * "0","绝对时间"  yyyy-mm-dd hh:mm:ss  --  yyyy-mm-dd hh:mm:ss
         * "1","相对时间"  string onlineReadTime  分钟 整数    1*60
         * "2","不限时间"
         * */
        role.put("timeModel", "2");                               /* 阅读时间控制 */
        role.put("onlineReadBeginTime", null);   /* 在线阅读起始时间 */
        role.put("onlineReadTime", null);        /* 在线阅读结束时间 */
        /* 隐写溯源三项 */
        role.put("isImplicit", "1");                              /* 实际环境中,仅需控制这个开关即可，其他两个字段为保留字段供后期需求使用 */
        role.put("isPrintImplicit", "0");
        role.put("isShowImplicit", "0");
        /* 遮盖预遮盖 */
        role.put("areaSelectBtn", "1");                            /* 区域选择工具开关-遮盖相关所有功能的开关 */
        role.put("cover", "1");
        role.put("preCover", "1");
        role.put("editBtn", "1");   /* 编辑相关按钮*/
        //默认其他业务有查看权限
        role.put("viewPower", "1");
    }


}
