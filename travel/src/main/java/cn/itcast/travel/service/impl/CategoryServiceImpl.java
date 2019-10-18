package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao=new CategoryDaoImpl();

    public List<Category> findAll() {
        //1.从jedis中查询
        //1.1获取jedis客户端
        Jedis jedis= JedisUtil.getJedis();
        //1.2可使用sortset排序查询
        //Set<String> categorys=jedis.zrange("category", 0, -1);
        //1.3查询sortset中的分数(cid)和值(cname)
        Set<Tuple> categorys=jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs=null;
        //2.判断查询的数据是否为空
        if(categorys==null||categorys.size() ==0){
            //3.如果为空，需要从数据库查询，再将数据存入redis
            //3.1从数据库查询
            System.out.println("从数据库中查询...");
            cs=categoryDao.findAll();
            //3.2将集合数据存储到redis中key为category中
            for(int i=0;i<cs.size(); i++){
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        }else{
            //4.如果不为空，将set的数据存入List中
            System.out.println("从redis中查询");
            cs=new ArrayList<Category>();
            for(Tuple tuple:categorys){
                Category category=new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        //4.如果不为空直接返回
        return cs;
    }
}
