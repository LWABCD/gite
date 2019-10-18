package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过sid查询商家信息
     * @param sid
     * @return
     */
    public Seller findSeller(int sid);
}
