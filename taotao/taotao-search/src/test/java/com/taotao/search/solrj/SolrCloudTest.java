package com.taotao.search.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testAddSolrCloud()throws Exception{
		//创建一个和solr集群的连接
		//参数就是zookeeper的地址列表，用逗号
		String zkHost="192.168.25.141:2181,192.168.25.141:2182,192.168.25.141:2183";
		CloudSolrServer solrServer=new CloudSolrServer(zkHost);
		//设置默认的collecton
		solrServer.setDefaultCollection("collection2");
		//创建一个文档对象
		SolrInputDocument document=new SolrInputDocument();
		//向文档中添加域
		document.setField("id", "test001");
		document.setField("item_title", "测试商品");
		//把文档添加到索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void testDeleteSolrCloud()throws Exception{
		//创建一个和solr集群的连接
		//参数就是zookeeper的地址列表，用逗号
		String zkHost="192.168.25.141:2181,192.168.25.141:2182,192.168.25.141:2183";
		CloudSolrServer solrServer=new CloudSolrServer(zkHost);
		//设置默认的collecton
		solrServer.setDefaultCollection("collection2");
		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
}
