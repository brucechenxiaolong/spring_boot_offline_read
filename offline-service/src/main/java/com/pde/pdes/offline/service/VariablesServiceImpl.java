package com.pde.pdes.offline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.po.UserApproveDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pde.pdes.offline.mapper.VariablesMapper;
import com.pde.pdes.offline.po.VariablesPO;

@Service
public class VariablesServiceImpl extends ServiceImpl<VariablesMapper, VariablesPO> implements VariablesService{

	@Override
	public List<Map<String, String>> listMetadataDisplayByTableId(String relationId, int type) {
		String content = getJsonVar(relationId,type);
		List<Map<String, String>> jsonList = JSONObject.parseObject(content, new TypeReference<List<Map<String, String>>>(){});
		return jsonList;
	}

	@Override
	public Map<String, Object> getJson2MapVar(String relationId, int type) {
		String content = getJsonVar(relationId,type);
		return JSONObject.parseObject(content, new TypeReference<Map<String, Object>>(){});
	}

	@Override
	public String getJsonVar(String relationId, int type) {
		QueryWrapper<VariablesPO> wrapper = new QueryWrapper<VariablesPO>();
		wrapper.lambda().eq(VariablesPO::getRelation_id, relationId)
                        .eq(VariablesPO::getVar_type, type);
		List<VariablesPO> list = list(wrapper);
		Assert.isTrue(list.size()>0 && list.get(0) != null, "未找到类型["+type+"]元数据对应模板.");
		String content = list.get(0).getContent();

		Assert.isTrue(StringUtil.isNotEmpty(content), "类型["+type+"]元数据模板为空.");
		return content;
	}

	@Override
	public SimpleResponse<Map<String, List<Map<String, Object>>>> listMetadataDisplayByTableId2(String tableCollection, String type) {

		QueryWrapper<VariablesPO> wrapper = new QueryWrapper<VariablesPO>();
		wrapper.lambda().eq(VariablesPO::getRelation_id, tableCollection)
				.eq(VariablesPO::getVar_type, type);
		VariablesPO variablesPO = super.getOne(wrapper);

		Map<String, List<Map<String, Object>>> originals = new HashMap<>();
		if (null == variablesPO) {
			throw new RuntimeException("获取原文展示列表失败！");
		}

		String originalRelation = variablesPO.getContent();
		if("".equals(originalRelation)){
			JSONObject jsonObject = JSON.parseObject(originalRelation);//String转json
			JSONArray jsonChildArr = jsonObject.getJSONArray("0");
			List<Map<String, Object>> orginalFiles = new ArrayList<>();
			for (int j = 0; j < jsonChildArr.size(); j++) {
				JSONObject jsonChildObject = jsonChildArr.getJSONObject(j);
				Map<String,Object> map = (Map<String,Object>)jsonChildObject;
				orginalFiles.add(map);
			}
			originals.put("0", orginalFiles);
		}
		return new SimpleResponse<>(true, "执行成功", originals);
	}

}
