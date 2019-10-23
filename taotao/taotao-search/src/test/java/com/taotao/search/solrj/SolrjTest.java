package com.taotao.search.solrj;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

public class SolrjTest {

    @Test
    public void addDocument() throws SolrServerException, IOException {
    	//创建一个solr连接
    	SolrServer solrServer=new HttpSolrServer("http://192.168.2.128:8888/solr");
    	//创建一个文档对象
    	SolrInputDocument document=new SolrInputDocument();
    	document.addField("id", "test001");
    	document.addField("item_title", "测试商品2");
    	document.addField("item_price", "12");
    	//把文档对象写入索引库
    	solrServer.add(document);
    	//提交
    	solrServer.commit();
    }
    
    @Test
    public void deleteDocument() throws SolrServerException, IOException {
    	SolrServer solrServer=new HttpSolrServer("http://192.168.2.128:8888/solr");
    	solrServer.deleteById("test001");
    	solrServer.deleteByQuery("*:*");
    	solrServer.commit();
    }
    
    @Test
    public void queryDocument()throws Exception{
    	SolrServer solrServer=new HttpSolrServer("http://192.168.2.128:8888/solr");
    	//创建一个查询对象
    	SolrQuery solrQuery=new SolrQuery();
    	//设置查询条件
    	solrQuery.setQuery("*:*");
    	solrQuery.setStart(20);
    	solrQuery.setRows(50);
    	//执行查询
    	SolrResponse response=solrServer.query(solrQuery);
    	//取查询结果
    	SolrDocumentList solrDocumentList=((QueryResponse) response).getResults();
    	System.out.println("共查询到记录:"+solrDocumentList.getNumFound());
    	for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			
		}
    }
}
