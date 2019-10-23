package com.taotao.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Soundbank;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

import cn.e3mall.common.utils.FastDFSClient;

/**
 * 图片上传服务
 * @author LWABCD
 *
 */

@Service
public class PictureServiceImpl implements PictureService {

	//在spring容器中把properties中的相应属性值取出来
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile){
		try {
			//把图片上传到图片服务器
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
			//取文件扩展名
			String OriginalFilename=uploadFile.getOriginalFilename();
			String extName=OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
			//得到一个图片的地址和文件名
			String url=fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//补充为完整的url
			url=IMAGE_BASE_URL+url;
			//封装到map中返回
			Map result=new HashMap<>();
			result.put("error",0);
			result.put("url",url);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map result=new HashMap<>();
			result.put("error",1);
			result.put("messge","上传图片失败");
			return result;
		}
	}

}
