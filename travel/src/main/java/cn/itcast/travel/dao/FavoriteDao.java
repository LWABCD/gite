package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据uid和rid查询tab_favorite表
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByUidAndRid(int rid,int uid);

    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    public int findFavoriteCount(int rid);
}
