package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao=new FavoriteDaoImpl();

    public boolean isFavorite(int rid, int uid) {
        Favorite favorite =favoriteDao.findByUidAndRid(rid,uid);
        return favorite!=null;
    }
}
