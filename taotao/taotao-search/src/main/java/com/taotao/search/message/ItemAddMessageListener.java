package com.taotao.search.message;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;

public class ItemAddMessageListener implements MessageListener{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try {
			//从消息中取商品id
			TextMessage textMessage=(TextMessage) message;
			String text=textMessage.getText();
			Long itemId=new Long(text);
			System.out.println(itemId);
			//等待事务提交
			//Thread.sleep(1000);
			//从数据库中取商品信息
			Item item=itemMapper.getItemById(itemId);
			System.out.println(item.getTitle());
			//创建一个文档对象
			SolrInputDocument document=new SolrInputDocument();
			//向文档对象中添加域
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			//把文档写入索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (JMSException | SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

}
