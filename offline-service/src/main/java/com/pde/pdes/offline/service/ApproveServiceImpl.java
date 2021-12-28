package com.pde.pdes.offline.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pde.pdes.offline.dto.ApproveQueryDTO;
import com.pde.pdes.offline.mapper.ApproveMapper;
import com.pde.pdes.offline.po.ApproveDocument;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproveServiceImpl extends ServiceImpl<ApproveMapper,ApproveDocument> implements ApproveService{
    @Override
    public List<ApproveDocument> search(ApproveQueryDTO query) {
        QueryWrapper<ApproveDocument> wrapper = new QueryWrapper<ApproveDocument>();
        wrapper.lambda().eq(ApproveDocument::getI_state,query.getState());
        List<ApproveDocument> listApprove  = this.list(wrapper);
        return listApprove;
    }

    @Override
    public boolean approve(String approveId, int state) {
        LambdaUpdateWrapper<ApproveDocument> wrapper = Wrappers.lambdaUpdate(ApproveDocument.class)
                .set(ApproveDocument::getI_state,state)
                .eq(ApproveDocument::getApprove_id,approveId);
        boolean f = this.update(wrapper);
        return f;
    }




}
