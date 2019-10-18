package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    public Seller findSeller(int sid) {
        String sql="select * from tab_seller where sid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
