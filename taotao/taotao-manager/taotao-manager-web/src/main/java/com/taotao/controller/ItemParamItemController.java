package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

/**
 * 展示商品规格参数
 * @author LWABCD
 *
 */

@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService ItemParamItemService;
	
	@RequestMapping("/showitem/{itemId}")
	public String ShowItemParam(@PathVariable Long itemId,Model model) {
		String itemParam=ItemParamItemService.getItemParamByItemId(itemId);
		System.out.println(itemParam);
		model.addAttribute("itemParam", itemParam);
	    return "item";
	}
}
