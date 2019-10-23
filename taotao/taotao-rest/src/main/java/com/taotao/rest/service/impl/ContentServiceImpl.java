package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

/**
 * 商品管理
 * @author LWABCD
 *
 */

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient JedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentList(long categoryId) {
		//从缓存中取内容
		try {
			String result=JedisClient.hget("INDEX_CONTENT_REDIS_KEY", categoryId+"");
			System.out.println(result);
			if(!StringUtils.isEmpty(result)) {
				List<TbContent> resultList=JsonUtils.jsonToList(result, TbContent.class);
			    return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example=new TbContentExample();
		Criteria criteria=example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list=contentMapper.selectByExample(example);
		//向缓存中存内容
		try {
			//把list转换成字符串
			String cacheString=JsonUtils.objectToJson(list);
			JedisClient.hset("INDEX_CONTENT_REDIS_KEY", categoryId+"", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
