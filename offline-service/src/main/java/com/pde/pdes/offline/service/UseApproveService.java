package com.pde.pdes.offline.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.dto.ArchivesFileQueryDTO;
import com.pde.pdes.offline.dto.UserApproveQueryDTO;
import com.pde.pdes.offline.po.UserApproveDocument;
import com.pde.pdes.offline.vo.ArchiveFileVO;

public interface UseApproveService {

	/**
	 * 查询审批列表
	 * @param query
	 * @return 分页展示
	 */
	Map<String, Object> search(UserApproveQueryDTO query);

//	Map<String, Object> searchAll(UserApproveQueryDTO query);

	/**
	 * 根据条目ID查询条目明细
	 * @param ID
	 * @return
	 */

	UserApproveDocument searchByID(String ID);

	/**
	 * 数据授权
	 * @param type
	 * @param ids
	 * @return 返回保持与利用一直，有错则写入
	 */
	SimpleResponse<?> addPermission(Integer type,List<String> ids);
	
	/**
	 * 取消授权
	 * @param ids
	 * @return
	 */
	boolean removePermission(List<String> ids);
	
    /**
     * 
     * @param orderNo
     * @param state
     * @param ids
     * @param response
     */
	void exportApprove(String orderNo,int state,List<String> ids, HttpServletResponse response);

	/**
	 * 审批
	 * @param approveId
	 * @param state
	 * @return
	 */
	boolean approve(String approveId,int state);

    List<ArchiveFileVO> getFileConfig(ArchivesFileQueryDTO archivesFileQueryDTO);

    void showFile(String file_url, HttpServletResponse res);

    int count(String use_approve_id);

	SimpleResponse<Map<String, List<Map<String, Object>>>> listFile(String use_approve_id);

}
