package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车controller
 * @author LWABCD
 *
 */

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,@RequestParam(defaultValue="1") int num,
			HttpServletRequest request,HttpServletResponse response) {
		TaotaoResult result=cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,Model model) {
		List<CartItem> cartList=cartService.getCartItemList(request);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String showCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
		TaotaoResult result=cartService.deleteCartItem(itemId, request, response);
		//相当于重新刷新了一下页面
		return "redirect:/cart/cart.html";
	}
}
