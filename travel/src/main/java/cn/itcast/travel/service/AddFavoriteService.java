package cn.itcast.travel.service;

import java.util.Date;

public interface AddFavoriteService {
    /**
     * 添加用户收藏的信息
     * @param rid
     * @param date
     * @param uid
     */
    public void addFavorite(int rid, Date date, int uid);
}
