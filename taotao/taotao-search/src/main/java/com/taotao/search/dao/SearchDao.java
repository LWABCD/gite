package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.SearchResults;

public interface SearchDao {

	SearchResults search(SolrQuery query)throws Exception;
}
