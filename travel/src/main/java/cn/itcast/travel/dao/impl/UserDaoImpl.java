package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;
import com.sun.javafx.css.parser.Css2Bin;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    public User findByUsername(String username) {
        User user=null;
        //如果执行sql时出错就会直接报异常，所以使用try...catch
        try {
            //1.定义sql
            String sql = "select * from tab_user where username=?";
            //2.执行sql
           user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    public void save(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    /**
     * 通过激活码查找指定用户
     * @param code
     * @return
     */
    public User findByCode(String code) {
        User user=null;
        try{
            String sql="select * from tab_user where code=?";
            //避免执行以下语句时出现异常，所以使用try...catch
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改用户激活状态
     * @param user
     */
    public void updateStatus(User user) {
        String sql="update tab_user set status='Y' where uid=?";
        template.update(sql, user.getUid());
        System.out.println(user.getUsername());
    }

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password) {
        User user=null;
        try{
            String sql="select * from tab_user where username=? and password=?";
            //避免执行以下语句时出现异常，所以使用try...catch
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return user;
    }
}
