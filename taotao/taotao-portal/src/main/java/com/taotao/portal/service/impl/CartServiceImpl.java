package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车service
 * @author LWABCD
 *
 */

@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	
	/**
	 * 向购物车添加商品
	 */
	@Override
	public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		//取商品信息
		CartItem cartItem=null;
		//从购物车中取商品列表
		List<CartItem> itemList=getCartItemList(request);
		//判断购物车中是否有此商品
		for (CartItem cItem : itemList) {
			//如果存在此商品
			if(cItem.getId()==itemId) {
				cItem.setNum(cItem.getNum()+num);
				cartItem=cItem;
				break;
			}
		}
		//如果购物车中不存在此商品，就需要调用rest服务从数据库取商品信息
		if(cartItem==null) {
			cartItem=new CartItem();
			String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_BASE_URL+itemId);
			//把json转成java对象
			TaotaoResult result=TaotaoResult.formatToPojo(json, TbItem.class);
			if(result.getStatus()==200) {
				TbItem item=(TbItem)result.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);
				cartItem.setImage(item.getImage()==null?"":item.getImage().split(",")[0]);
			}
			//把商品加入购物车列表
			itemList.add(cartItem);
		}
		//把购物车列表写入cookie中
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}

	/**
	 * 得到购物车中的商品列表，因为未登录也可以使用购物车，因此把商品存在cookie中
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request) {
		//从cookie中取商品列表
		String cartJson=CookieUtils.getCookieValue(request, "TT_CART", true);
		//判断cookie中是否有商品，避免报空指针异常
		if(cartJson==null) {
			return new ArrayList<>();
		}
		//把json转成商品列表
		try {
			List<CartItem> list=JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> list=getCartItemList(request);
		return list;
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//取出购物车商品列表
		List<CartItem> itemList=getCartItemList(request, response);
		//找到对应的商品并删除
		for (CartItem cartItem : itemList) {
			if(cartItem.getId()==itemId) {
				itemList.remove(cartItem);
				break;
			}
		}
		//把商品列表重新写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}
	
	

}
