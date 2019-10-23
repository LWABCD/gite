package com.taotao.search.service;

import com.taotao.search.pojo.SearchResults;

public interface SearchService {

    SearchResults search(String queryString,int page,int rows)throws Exception;
}
