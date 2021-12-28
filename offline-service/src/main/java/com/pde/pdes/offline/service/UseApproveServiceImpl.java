package com.pde.pdes.offline.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pde.pdes.base.dto.SimpleResponse;
import com.pde.pdes.offline.annotation.Override2;
import com.pde.pdes.offline.dto.ArchivesFileQueryDTO;
import com.pde.pdes.offline.dto.UserApproveQueryDTO;
import com.pde.pdes.offline.mapper.UseApproveMapper;
import com.pde.pdes.offline.po.UserApproveDocument;
import com.pde.pdes.offline.utils.Aes;
import com.pde.pdes.offline.utils.BaseTools;
import com.pde.pdes.offline.vo.ArchiveFileVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO CXL-离线阅览原文操作
 */
@Service
public class UseApproveServiceImpl extends ServiceImpl<UseApproveMapper, UserApproveDocument> implements UseApproveService {

	@Autowired
	ApproveService approveService;

	@Value("#{xx.xx}")
	private String xxx;

	private final Map<String, String> defaultMap = new HashMap<String, String>();
	{
		//阅读器默认组件
		defaultMap.put("ofd", "OFD-OFD.JS");
		defaultMap.put("pdf", "PDF-PDF.JS");
		defaultMap.put("jpg", "JPG");
		defaultMap.put("png", "JPG");
		defaultMap.put("bmp", "JPG");
		defaultMap.put("mp3", "MP3");
		defaultMap.put("mp4", "MP4");
	}


	@Override2
	public Map<String, Object> search(UserApproveQueryDTO query) {
		//this.sqlSessionBatch()
		System.out.println("ElementType.CONSTRUCTOR=" + ElementType.CONSTRUCTOR);
		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
		wrapper.lambda().like(UserApproveDocument::getEntity_text, query.getKeyword());
		Page<UserApproveDocument> page = PageHelper.startPage(query.getPage(), query.getSize());
		list(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getResult()); 
		return map;
	}

	/**
	 * 离线阅览
	 * keyword
	 * @param query
	 * @return
	 */
//	@Override
//	public Map<String, Object> searchAll(UserApproveQueryDTO query) {
//		//this.sqlSessionBatch()
//		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
//		wrapper.lambda().like(UserApproveDocument::getEntity_text, query.getKeyword());
//		Page<UserApproveDocument> page = PageHelper.startPage(query.getPage(), query.getSize());
//		list(wrapper);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("total", page.getTotal());
//		map.put("rows", page.getResult());
//		return map;
//	}

	@Override
	public UserApproveDocument searchByID(String ID) {
		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
		wrapper.lambda().eq(UserApproveDocument::getEntity_id, ID);
		return super.getOne(wrapper);
	}


	//todo 实体出库的权限离线端能赋吗？
	@Override
	public SimpleResponse<?> addPermission(Integer type, List<String> ids) {
		Assert.notEmpty(ids,"修改条目不能为空.");
		Assert.notNull(type,"赋权类型不能为空.");
		LambdaUpdateWrapper<UserApproveDocument> wrapper = Wrappers.lambdaUpdate(UserApproveDocument.class);
		switch(type) {
		case 0:
			wrapper.set(UserApproveDocument::getEntity_view_permission, 1)
			.set(UserApproveDocument::getFile_view_permission, 1)
			.set(UserApproveDocument::getFile_print_permission, 1)
			.set(UserApproveDocument::getFile_down_permission, 1)
			.set(UserApproveDocument::getOutstock_permission,1);
			break;
		case 1:
			wrapper.set(UserApproveDocument::getEntity_view_permission, 1);
			break;
		case 2:
			wrapper.set(UserApproveDocument::getFile_view_permission, 1);
			break;
		case 3:
			wrapper.set(UserApproveDocument::getFile_print_permission, 1);
			break;
		case 4:
			wrapper.set(UserApproveDocument::getFile_down_permission,1);
			break;
		case 5:
			wrapper.set(UserApproveDocument::getOutstock_permission,1);
			break;
		default:
			return new SimpleResponse<>(false, "无效的赋权类型.");
		}
		wrapper.set(UserApproveDocument::getModified, 1);
		return new SimpleResponse<>(this.update(wrapper));
	}

	@Override
	public boolean removePermission(List<String> ids) {
		LambdaUpdateWrapper<UserApproveDocument> wrapper = Wrappers.lambdaUpdate(UserApproveDocument.class);
		wrapper.set(UserApproveDocument::getEntity_view_permission, 0)
		.set(UserApproveDocument::getFile_view_permission, 0)
		.set(UserApproveDocument::getFile_print_permission, 0)
		.set(UserApproveDocument::getFile_down_permission, 0)
		.set(UserApproveDocument::getOutstock_permission,0)
		.set(UserApproveDocument::getModified,1);
		return this.update(wrapper);
		
	}

	@Override
	public void exportApprove(String orderNo, int state, List<String> ids, HttpServletResponse response) {
		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
		wrapper.lambda().eq(UserApproveDocument::getModified, 1);
		List<UserApproveDocument> list = list(wrapper);
		
	}
	

	@Override
	public boolean approve(String approveId, int state) {
		boolean f = approveService.approve(approveId,1);
		if (f) {
			LambdaUpdateWrapper<UserApproveDocument> wrapper = Wrappers.lambdaUpdate(UserApproveDocument.class)
					.set(UserApproveDocument::getState,state)
					.eq(UserApproveDocument::getApprove_id,approveId);
			return this.update(wrapper);
		}
		return false;
	}

	/**
	 * 获取原文配置
	 * @param archivesFileQueryDTO
	 */
	@Override
	public List<ArchiveFileVO> getFileConfig(ArchivesFileQueryDTO archivesFileQueryDTO) {
		UserApproveDocument userApproveDocument = searchByID(archivesFileQueryDTO.getEntity_id());
		List<ArchiveFileVO> listVo = new ArrayList<ArchiveFileVO>();
		if (null == userApproveDocument) {
			return listVo;
		}
		List<Map<String, String>> listFile = userApproveDocument.getFiles();
		int i = 0;
		for (Map fileDocument: listFile) {

			String json = JSON.toJSONString(fileDocument);//map转String
			JSONObject jsonObject = JSON.parseObject(json);//String转json
			System.out.println(jsonObject);

			System.out.println(jsonObject.getString("sys_file_path"));

			JSONArray jsonChildArr = jsonObject.getJSONArray("" + i);
			for (int j = 0; j < jsonChildArr.size(); j++) {
				JSONObject jsonChildObject = jsonChildArr.getJSONObject(j);

				ArchiveFileVO archiveFileVO = new ArchiveFileVO();
				String file_path = jsonChildObject.getString("sys_file_path");
				System.out.println("*****file_path=" + file_path);
				String fileSuffix = jsonChildObject.getString("jsjwjgs");
				System.out.println("*****fileSuffix=" + fileSuffix);

				archiveFileVO.setFile_url("/archivesFile/showFile/" + BaseTools.base64Encoder(file_path));
				archiveFileVO.setFile_suffix(fileSuffix);
				archiveFileVO.setReader_type(defaultMap.get(fileSuffix.toLowerCase()));
				if (StringUtils.isEmpty(archivesFileQueryDTO.getFile_id())) {
					listVo.add(archiveFileVO);
				} else {
					if (archivesFileQueryDTO.getFile_id().equals(file_path)) {
						listVo.add(archiveFileVO);
					}
				}

			}


			i++;
		}
		return listVo;
	}

	/**
	 * TODO CXL-此处文件需要解密后才能查看
	 * @param file_url
	 * @param res
	 */
	@Override
	public void showFile(String file_url, HttpServletResponse res){
		InputStream in = null;
		try {
			file_url = BaseTools.base64Decoder(file_url);
			String fileName = file_url.substring(file_url.lastIndexOf("/")+1);

//			File fileRoot = new File(new File("").getAbsolutePath());

			String basePath = "";
			//FIXME CXL-本地测试使用,此处代码要屏蔽
			basePath = "D:/sqlite/offline/";
//			basePath = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator;
			System.out.println("*****basePath=" + basePath);

			String name = fileName.split("\\.")[0];
			String type = fileName.split("\\.")[1];


			File file = new File(basePath + "file" + File.separator + name + "_encrypt." + type);
			System.out.println(file.exists());
			if (!file.exists()) {
				return;
			}
			//解密文件
			File newFile = Aes.decryptFile(file,basePath + "file" + File.separator + fileName);

			in = FileUtils.openInputStream(newFile);
			showFile(in,fileName,fileName,res);

			//最后删除解密后的原文
			newFile.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {

		}
	}

	public void showFile(InputStream input, String fileTitle, String fileName, HttpServletResponse resp) {
		OutputStream output = null;
		BufferedOutputStream outstream = null;
		BufferedInputStream inputstream = null;
		try {
			output = resp.getOutputStream();
//			resp.setContentType(ContentType.getContentType("." + fileName.substring(fileName.lastIndexOf('.')).toLowerCase(Locale.getDefault())));
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileTitle.getBytes("GBK"), "iso-8859-1") + "\"");
			outstream = new BufferedOutputStream(output);
			inputstream = new BufferedInputStream(input);
			int len;
			int _length = 0;
			byte[] buff = new byte[1024];
			while ((len = inputstream.read(buff, 0, buff.length)) != -1) {
				_length += len;
				outstream.write(buff, 0, len);
			}
			resp.setContentLength(_length);
			outstream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 判断是否有原文
	 * @param use_approve_id
	 * @return
	 */
	@Override
	public int count(String use_approve_id){
		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
		wrapper.lambda().eq(UserApproveDocument::getId, use_approve_id);
		List<UserApproveDocument> list = list(wrapper);
		if(!list.isEmpty()){
			UserApproveDocument document = list.get(0);
			if(!StringUtils.isEmpty(document.getFiles_text())){
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 离线阅览，原文信息
	 * @param use_approve_id
	 * @return
	 */
	@Override
	public SimpleResponse<Map<String, List<Map<String, Object>>>> listFile(String use_approve_id) {

		QueryWrapper<UserApproveDocument> wrapper = new QueryWrapper<UserApproveDocument>();
		wrapper.lambda().eq(UserApproveDocument::getId, use_approve_id);
		UserApproveDocument userApproveDocument = super.getOne(wrapper);

		Map<String, List<Map<String, Object>>> originals = new HashMap<>();
		if (null == userApproveDocument) {
			throw new RuntimeException("获取登记单失败！");
		}

		/**
		 * 循环原文信息
		 */
		List<Map<String, String>> listFile = userApproveDocument.getFiles();
		int i = 0;
		for (Map fileDocument: listFile) {

			String json = JSON.toJSONString(fileDocument);//map转String
			JSONObject jsonObject = JSON.parseObject(json);//String转json
			JSONArray jsonChildArr = jsonObject.getJSONArray("" + i);

			List<Map<String, Object>> orginalFiles = new ArrayList<>();

			for (int j = 0; j < jsonChildArr.size(); j++) {
				JSONObject jsonChildObject = jsonChildArr.getJSONObject(j);
				Map<String,Object> map = (Map<String,Object>)jsonChildObject;
				orginalFiles.add(map);
			}

			originals.put("" + i, orginalFiles);
			i++;
		}

		return new SimpleResponse<>(true, "执行成功", originals);
	}

}

