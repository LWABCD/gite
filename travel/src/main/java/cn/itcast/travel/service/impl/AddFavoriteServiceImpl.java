package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.AddFavoriteDao;
import cn.itcast.travel.dao.impl.AddFavoriteDaoImpl;
import cn.itcast.travel.service.AddFavoriteService;

import java.util.Date;

public class AddFavoriteServiceImpl implements AddFavoriteService {
    private AddFavoriteDao addFavoriteDao=new AddFavoriteDaoImpl();

    public void addFavorite(int rid, Date date, int uid) {
        addFavoriteDao.insertFavorite(rid,date, uid);
    }

}
