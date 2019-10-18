package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    public int findTotalCount(int cid, String rname) {
        //String sql="select count(*) from tab_route where cid=?";
        //定义sql模板，用于拼接字符串
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List parmas=new ArrayList();   //参数集合
        //判断参数是否有值
        if(cid!=0){
            sb.append(" and cid=? ");
            parmas.add(cid);
        }
        if(rname!=null&&rname.length()>0){
            sb.append(" and rname like ?");
            parmas.add("%"+rname+"%");
        }
        sql=sb.toString();
        return template.queryForObject(sql, Integer.class,parmas.toArray());
    }

    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql="select * from tab_route where cid=? limit ?,?";
        //定义sql模板，用于拼接字符串
        String sql="select * from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List parmas=new ArrayList();   //参数集合
        //判断参数是否有值
        if(cid!=0){
            sb.append(" and cid=? ");
            parmas.add(cid);
        }
        if(rname!=null&&rname.length()>0){
            sb.append(" and rname like ?");
            parmas.add("%"+rname+"%");
        }
        sb.append(" limit ?,?");
        sql=sb.toString();

        parmas.add(start);
        parmas.add(pageSize);
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),parmas.toArray());
    }

    public Route findByRid(int rid) {
        String sql="select * from tab_route where rid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
