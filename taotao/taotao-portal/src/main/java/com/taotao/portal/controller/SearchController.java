package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResults;
import com.taotao.portal.service.SearchService;

/**
 * 商品搜索controller
 * @author LWABCD
 *
 */

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,@RequestParam(defaultValue="1")Integer page,Model model) {
		if(queryString!=null) {
			try {
				queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		SearchResults results=searchService.search(queryString,page);
		//向页面传递参数
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages",results.getPageCount());
		model.addAttribute("itemList",results.getItemList());
		model.addAttribute("page",page);
		
		return "search";
	}
}
