package cn.itcast.travel.dao;

import java.util.Date;

public interface AddFavoriteDao {
    /**
     * 往tab_favorite表中插入用户收藏的信息
     * @param rid
     * @param date
     * @param uid
     */
    public void insertFavorite(int rid, Date date, int uid);
}
