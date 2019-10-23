package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;

public interface CartService<CartItem> {

	TaotaoResult addCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request);
	List<CartItem> getCartItemList(HttpServletRequest request,HttpServletResponse response);
	TaotaoResult deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);
}
