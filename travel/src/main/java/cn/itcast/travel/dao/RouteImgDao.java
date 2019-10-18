package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据rid查询详情页的图片数据集合
     * @param rid
     * @return
     */
    public List<RouteImg> findRouteImg(int rid);
}
