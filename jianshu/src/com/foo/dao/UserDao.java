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
	 * �����ѯ10���û�
	 * @return
	 */
	public List<User> selectRandom(){	
		String sql = "SELECT * FROM `user` WHERE uid >= (SELECT FLOOR(RAND() * (SELECT MAX(uid) FROM `user`))) ORDER BY uid LIMIT 10";
		return Jdbc.select(sql, new BeanListHandler<User>(User.class));
	}
	
	/**
	 * �����ѯ10��û����ע���û�
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
	 * �����ݿ�ѡ���ѱ���ע������uid�б�
	 * @param uid
	 * @return
	 */
	public List<Userfollowuser> selectFollowedAuthorsUid(int uid){
		String sql="select * from userfollowUser where userA=?";
		return Jdbc.select(sql, new BeanListHandler<Userfollowuser>(Userfollowuser.class),uid);
	}
	
	/**
	 *ͨ��id��ȡ����
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
