package com.taotao.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.tools.internal.ws.processor.model.Request;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传服务
 * @author LWABCD
 *
 */

@Controller
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping(value="pic/upload",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		Map resultMap=pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，即可以在多个浏览器上正常运行，需要把Result转换成json格式的字符串
		String json=JsonUtils.objectToJson(resultMap);
		return json;
	}
	
//	/**
//	* 获取文件上传目录
//	* @return
//	*/
//	private File getUploadFileLocation(HttpServletRequest request){
//	File upload = null;
//	try {
//	//获取项目的编译类目录   class 位置
//	// File path = new File(ResourceUtils.getURL("classpath:").getPath());
//	//获取项目文件位置  项目位置
//	File path = new File(request.getSession().getServletContext().getRealPath("/"));
//	if(!path.exists()) path = new File("");
//	System.out.println("**************** path:"+path.getAbsolutePath() + "****************");
//	//如果上传目录为/static/images/upload/，则可以如下获取：
//	upload = new File(path.getAbsolutePath(),WEB_FILE_LOCATION );
//	if(!upload.exists()) upload.mkdirs();
//	System.out.println("****************** upload url:"+upload.getAbsolutePath() + "****************");
//	} catch (Exception e) {
//	e.printStackTrace();
//	}
//	return upload;
//	}
}
