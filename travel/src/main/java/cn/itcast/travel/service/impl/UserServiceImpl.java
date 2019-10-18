package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();

    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u=userDao.findByUsername(user.getUsername());
        //判断u是否为空
        if(u!=null){
            //用户名存在，保存失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送，邮件正文
        //注意：这个链接是在邮箱中点击来访问该项目，所以链接地址要使用绝对地址，
        //还要注意：该项目名为travel_war_exploded，而不是travel
        String content="<a href='http://localhost:8080/travel_war_exploded/activeUserServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    public boolean active(String code) {
        //1.根据激活码查找用户对象
        User user=userDao.findByCode(code);
        if(user!=null){
            //2.调用service完成激活
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
