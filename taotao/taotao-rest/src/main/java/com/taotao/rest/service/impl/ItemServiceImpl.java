package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

import redis.clients.jedis.Jedis;

/**
 * 查询商品基础信息service
 * @author LWABCD
 *
 */

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品id对应的信息
			String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
			if(!StringUtils.isBlank(json)) {
				TbItem item=JsonUtils.jsonToPojo(json,TbItem.class);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询商品信息
		TbItem item=itemMapper.selectByPrimaryKey(itemId);
		try {
			//把商品信息存入缓存中
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base",JsonUtils.objectToJson(item));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base",REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDesc(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品id对应的描述信息
			String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
			if(!StringUtils.isBlank(json)) {
				TbItemDesc itemDesc=JsonUtils.jsonToPojo(json,TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//取商品描述信息
		TbItemDesc itemDesc=itemDescMapper.selectByPrimaryKey(itemId);
		try {
			//把商品信息存入缓存中
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc",JsonUtils.objectToJson(itemDesc));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParam(long itemId) {
		//根据商品id查询规格参数
		try {
			//添加缓存逻辑
			//从缓存中取商品id对应的规格信息
			String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
			if(!StringUtils.isBlank(json)) {
				TbItemParamItem itemParamItem=JsonUtils.jsonToPojo(json,TbItemParamItem.class);
				return TaotaoResult.ok(itemParamItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置查询条件
		TbItemParamItemExample example=new TbItemParamItemExample();
		Criteria criteria=example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list =itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			TbItemParamItem itemParamItem=list.get(0);
			try {
				//把商品信息存入缓存中
				jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param",JsonUtils.objectToJson(itemParamItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param",REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(itemParamItem);
		}
		return TaotaoResult.build(400,"商品规格信息为空");
	}

}
