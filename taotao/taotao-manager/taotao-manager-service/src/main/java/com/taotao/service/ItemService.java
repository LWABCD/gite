package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    
	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page,int rows);
	TaotaoResult creatItem(TbItem item,String desc,String paramData)throws Exception;
}
