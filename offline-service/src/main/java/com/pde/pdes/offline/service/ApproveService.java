package com.pde.pdes.offline.service;

import com.pde.pdes.offline.dto.ApproveQueryDTO;
import com.pde.pdes.offline.po.ApproveDocument;

import java.util.List;
import java.util.Map;

public interface ApproveService {

    /**
     * 条件检索呈批单列表
     * @param query
     * @return
     */
    List<ApproveDocument> search(ApproveQueryDTO query);

    /**
     * 更新呈批单审批
     * @param approveId
     * @param state
     * @return
     */
    public boolean approve(String approveId, int state);
}
