package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EUTreeNode> getContentCategoryList(long parentId);
	TaotaoResult insetContentCategory(long parentId,String name);
	TaotaoResult deleteContentCategory(long parentId);
	TaotaoResult updateContentCategory(long id,String name);
}
