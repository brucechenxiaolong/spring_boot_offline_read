package com.pde.pdes.offline.service;

import com.pde.pdes.base.dto.SimpleResponse;

import java.util.List;
import java.util.Map;

public interface VariablesService {
	
	/**
	 * 
	 * @param tableId 
	 * @param type    
	 * @return
	 */
	List<Map<String,String>> listMetadataDisplayByTableId(String relationId,int type);
	
	Map<String,Object> getJson2MapVar(String relationId,int type);
	
	String getJsonVar(String relationId,int type);

	SimpleResponse<Map<String, List<Map<String, Object>>>> listMetadataDisplayByTableId2(String tableCollection, String type);

}
