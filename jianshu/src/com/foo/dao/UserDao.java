package com.foo.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.foo.entity.User;
import com.foo.entity.Userfollowuser;
import com.foo.faker.UserFaker;
import com.foo.util.MD5;
import com.github.javafaker.Name;



public class UserDao {

	/**
	 * 随机查询10个用户
	 * @return
	 */
	public List<User> selectRandom(){	
		String sql = "SELECT * FROM `user` WHERE uid >= (SELECT FLOOR(RAND() * (SELECT MAX(uid) FROM `user`))) ORDER BY uid LIMIT 10";
		return Jdbc.select(sql, new BeanListHandler<User>(User.class));
	}
	
	/**
	 * 随机查询10个没被关注的用户
	 * @return
	 */
	public List<User> selectRandom(int[] exclude){	
		String sql = "SELECT * FROM `user` WHERE uid >= (SELECT FLOOR(RAND() * (SELECT MAX(uid) FROM `user`))) "+this.generateInStatement(exclude)+" ORDER BY uid LIMIT 10";
		Object[] params=new Object[exclude.length];
		for (int i = 0; i < exclude.length; i++) {
			params[i]=exclude[i];
		}
		return Jdbc.select(sql, new BeanListHandler<User>(User.class),params);
		
	}
	
	public String generateInStatement(int[] exclude) {
		if (exclude != null && exclude.length > 0) {
			String in = " AND uid not in (";
			for (int i = 0; i < exclude.length; i++) {
				in += "?";
				if (i < exclude.length - 1)
					in += ",";
			}
			in += ")";
			return in;
		}
		return "";
	}
	
	/**
	 * 从数据库选择已被关注的作者uid列表
	 * @param uid
	 * @return
	 */
	public List<Userfollowuser> selectFollowedAuthorsUid(int uid){
		String sql="select * from userfollowUser where userA=?";
		return Jdbc.select(sql, new BeanListHandler<Userfollowuser>(Userfollowuser.class),uid);
	}
	
	/**
	 *通过id获取作者
	 */
	public User selectById(int id) {
		String sql="select * from `user` where uid=?";
		return Jdbc.select(sql, new BeanHandler<User>(User.class),id);
	}
	
	public User selectByNickName(String nickName) {
		String sql="select * from `user` where nickName=?";
		return Jdbc.select(sql, new BeanHandler<User>(User.class), nickName);
	}
	
	public User selectByPhone(String phone) {
		String sql="select * from `user` where phone=?";
		return Jdbc.select(sql, new BeanHandler<User>(User.class),phone);
	}
	
	public void saveRegInfo(String nickName,String phone,String passWord,String regTime) {
		String pwdString=MD5.encrypt(passWord);
		String sql="insert into `user`(nickName,phone,pwd,registerTime) values(?,?,?,?)";
		Jdbc.update(sql,nickName,phone,pwdString,regTime);
	}
	
	public User selectUserBynickName(String nickName) {
		String sql="select * from `user` where nickName=?";
		return Jdbc.select(sql, new BeanHandler<User>(User.class),nickName);
	}
	public User login(String phone) {
		String sql="select * from `user` where phone=?";
		
		return Jdbc.select(sql, new BeanHandler<User>(User.class),phone);
		
	}
	
	public void saveUserFollowUserInfo(int userA,int userB,String followTime) {
		String sql="insert into userfollowuser(userA,userB,followTime) values(?,?,?)";
		Jdbc.update(sql,userA,userB,followTime);
	}
	
	
	

}
