package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.AddFavoriteDao;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class AddFavoriteDaoImpl implements AddFavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public void insertFavorite(int rid, Date date, int uid) {
        String sql = "insert into tab_favorite(rid,date,uid) values(?,?,?)";
        template.update(sql, rid,date, uid);
    }
}

