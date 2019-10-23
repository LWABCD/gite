package com.grbk.blog.service;

import com.grbk.blog.NotFoundException;
import com.grbk.blog.dao.TagRepository;
import com.grbk.blog.pojo.Tag;
import com.grbk.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 */

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    //根据id列表查询到博客所属的标签列表
    @Override
    public List<Tag> listTag(String tagIds) {
        List<Long> listIds=convertIdsToList(tagIds);
        List<Tag> listTag=new ArrayList<>();
        int size=listIds.size();
        for(int i=0;i<size;i++){
            listTag.add(tagRepository.getOne((listIds.get(i))));
        }
        return listTag;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0, size,sort);
        return tagRepository.findTop(pageable);
    }

    //把标签id字符串转成长整型列表
    public List<Long> convertIdsToList(String tagIds){
        List<Long> list=new ArrayList<>();
        if(!"".equals(tagIds)&&tagIds!=null){
            String ids[]=tagIds.split(",");
            for(int i=0;i<ids.length;i++){
                list.add(new Long(ids[i]));
            }
        }
        return list;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag, t, MyBeanUtils.getNullPropertyNames(tag));
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
