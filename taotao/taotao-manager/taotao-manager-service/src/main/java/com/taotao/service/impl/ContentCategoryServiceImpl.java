package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author LWABCD
 *
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
		
	public List<EUTreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList=new ArrayList<>();
		for(TbContentCategory contentCategory:list) {
			EUTreeNode node=new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	public TaotaoResult insetContentCategory(long parentId, String name) {
		TbContentCategory contentCategory=new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        contentCategoryMapper.insert(contentCategory);
        //查询父节点
        TbContentCategory parentCat=contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断父节点原来是否为父节点
        if(!parentCat.getIsParent()) {
        	parentCat.setIsParent(true);
        	//更新父节点
        	contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
		return TaotaoResult.ok(contentCategory);
	}

	public TaotaoResult deleteContentCategory(long id) {
		//查询所删节点的父节点
		TbContentCategory contentCategory=contentCategoryMapper.selectByPrimaryKey(id);
		long parentId=contentCategory.getParentId();
		//删除对应的节点及它的所有子节点
		deleteContentCat(contentCategory);
		//查看所删节点的父节点是否还有子节点
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
		//父节点没有子节点
		if(list==null||list.size()==0) {
			TbContentCategory parentCategory=contentCategoryMapper.selectByPrimaryKey(parentId);
			parentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		return TaotaoResult.ok();
	}
	
	//删除指定节点及它的所有字节点
	public void deleteContentCat(TbContentCategory contentCategory) {{
		if(!contentCategory.getIsParent()) {
			contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
			return;
		}else {
			TbContentCategoryExample example=new TbContentCategoryExample();
			Criteria criteria=example.createCriteria();
			criteria.andParentIdEqualTo(contentCategory.getId());
			List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
			if(list!=null&&list.size()!=0) {
				for(TbContentCategory contentCat:list) {
					deleteContentCat(contentCat);
				}
			}
			contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
		}
	}
		
	}

	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory contentCategory=contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok();
	}

}
