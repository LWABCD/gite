package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResults;
import com.taotao.portal.service.SearchService;

/**
 * 商品搜索Service
 * @author LWABCD
 *
 */

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	
	@Override
	public SearchResults search(String queryString, int page) {
		//调用taotao-search服务
		//参数是一个map对象
		Map<String,String> param=new HashMap<>();
		param.put("q",queryString);
		param.put("page",page+"");
		try {
			String json=HttpClientUtil.doGet(SEARCH_BASE_URL,param);
			TaotaoResult taotaoResult=TaotaoResult.formatToPojo(json,SearchResults.class);
			if(taotaoResult.getStatus()==200) {
				SearchResults results=(SearchResults)taotaoResult.getData();
				return results;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
