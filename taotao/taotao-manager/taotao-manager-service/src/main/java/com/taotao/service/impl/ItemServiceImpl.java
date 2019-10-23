package com.taotao.service.impl;


import java.util.*;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * @author LWABCD
 *
 */

@Service
public class ItemServiceImpl implements ItemService  {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
//	@Autowired
//	private JmsTemplate jmsTemplate;
//	@Autowired
//	private Destination queueDestination;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem item=itemMapper .selectByPrimaryKey(itemId);
		return item;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品
		TbItemExample example=new TbItemExample();
		//分页处理
		PageHelper.startPage(page,rows);
		List<TbItem> list=itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result=new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult creatItem(TbItem item, String desc,String itemParams) throws Exception {
		//item补全
		//生成商品id
		final Long itemId=IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中获得JmsTemplate对象。
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		//从容器中获得一个Destination对象。
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		//发送添加商品消息
		jmsTemplate.send(destination,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage=session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		//添加商品描述信息
		TaotaoResult result=insertItemDesc(itemId, desc);
		//抛了异常，spring会自动进行事务回滚操作
		if(result.getStatus()!=200) {
			throw new Exception();
		}
		//添加商品规格参数
		result=insertItemParamItem(itemId, itemParams);
		if(result.getStatus()!=200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加商品描述信息
	 */
    public TaotaoResult insertItemDesc(Long itemId,String desc)throws Exception{
    	TbItemDesc itemDesc=new TbItemDesc();
    	itemDesc.setItemId(itemId);
    	itemDesc.setItemDesc(desc);
    	itemDesc.setCreated(new Date());
    	itemDesc.setUpdated(new Date());
    	itemDescMapper.insert(itemDesc);
    	return TaotaoResult.ok();
    }
    
    /**
     * 添加商品规格参数
     */
    public TaotaoResult insertItemParamItem(long itemId,String itemParams) {
    	TbItemParamItem itemParamItem=new TbItemParamItem();
    	itemParamItem.setItemId(itemId);
    	itemParamItem.setParamData(itemParams);
    	itemParamItem.setCreated(new Date());
    	itemParamItem.setUpdated(new Date());
    	itemParamItemMapper.insert(itemParamItem);
    	return TaotaoResult.ok();
    }
}
