package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao=new RouteDaoImpl();
    RouteImgDao routeImgDao=new RouteImgDaoImpl();
    SellerDao sellerDao=new SellerDaoImpl();
    FavoriteDao favoriteDao=new FavoriteDaoImpl();

    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb=new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页的数据集合
        int start=(currentPage-1)*pageSize;
        List<Route> list=routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        //设置总页数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    public Route findOne(int rid) {
        //封装route
        Route route=new Route();
        route=routeDao.findByRid(rid);
        //根据线路rid查询tab_route_img对象
        List<RouteImg> routeImgList= routeImgDao.findRouteImg(rid);
        //设置图片数据集合
        route.setRouteImgList(routeImgList);
        Seller seller=sellerDao.findSeller(route.getSid());
        //设置商家数据集合
        route.setSeller(seller);
        //查询收藏次数
        int count= favoriteDao.findFavoriteCount(rid);
        route.setCount(count);
        System.out.println("收藏次数"+count);
        return route;
    }
}
