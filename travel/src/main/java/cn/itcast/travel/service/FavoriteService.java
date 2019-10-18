package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断用户是否收藏过该路线
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(int rid,int uid);
}
