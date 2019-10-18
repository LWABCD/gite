package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    public Favorite findByUidAndRid(int rid, int uid) {
        Favorite favorite=null;
        try{
            String sql="select * from tab_favorite where rid=? and uid=?";
            favorite=template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return favorite;
    }

    public int findFavoriteCount(int rid) {
        String sql="select count(*) from tab_favorite where rid=?";
        return template.queryForInt(sql,rid);
    }
}
