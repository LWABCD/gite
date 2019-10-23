package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResults;

public interface SearchService {

	SearchResults search(String queryString,int page);
}
