package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResults;
import com.taotao.search.service.SearchService;

/**
 * 搜索服务controller
 * @author LWABCD
 *
 */

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService; 
	
	@RequestMapping(value="/query",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult search(@RequestParam("q")String queryString,
			                   @RequestParam(defaultValue="1")Integer page,
			                   @RequestParam(defaultValue="60")Integer rows) {
		//请求参数不能为空
		if(StringUtils.isBlank(queryString)) {
			return TaotaoResult.build(400, "查询条件不能为空");
		}
		SearchResults searchResults=null;
		try {
			//解决get请求乱码问题
			queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");
			searchResults=searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok(searchResults);
	}
}
