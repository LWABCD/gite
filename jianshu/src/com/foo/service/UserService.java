package com.foo.service;

import java.util.Date;
import java.util.List;

import com.foo.dao.Jdbc;
import com.foo.dao.UserDao;
import com.foo.entity.User;
import com.foo.entity.Userfollowuser;

public class UserService {
	private UserDao userDao=new UserDao();
	/**
	 * 获得随机10个没被关注的作者
	 * 
	 * @return
	 */
	public List<User> getRecommedAuthors(int uid) {

		try {
			//如果用户没有登录
			if(uid==-1) {
				return userDao.selectRandom();
			}
			//拿到已被关注的作者列表
			List<Userfollowuser> isFollowed=userDao.selectFollowedAuthorsUid(uid);
			int size=isFollowed.size();
			int[] exclude=new int[size];
			//把列表转换为数组
			for(int i=0;i<size;i++) {
				exclude[i]=isFollowed.get(i).getUserB();
			}
			return userDao.selectRandom(exclude);
		} finally {
			Jdbc.close();
		}
	}
	
	/**
	 * 通过id获取作者
	 */
	public User getAuthorById(int id) {

		try {
			return userDao.selectById(id);
		} finally {
			Jdbc.close();
		}
	}
	
	public boolean checkNickNameUnique(String nickName) {
		if(userDao.selectByNickName(nickName)==null)
			return false;
		return true;
	}
	
	public boolean checkPhoneUnique(String phone) {
		if(userDao.selectByPhone(phone)==null)
			return false;
		return true;
	}
	public User slectUserByPhone(String phone) {
		
		return userDao.selectByPhone(phone);
	}
	
	
	public void saveRegInfo(String nickName,String phone,String passWord,String regTime){
		userDao.saveRegInfo(nickName, phone, passWord,regTime);
	}
	
	public User selectUserByNickName(String nickName) {
		return userDao.selectByNickName(nickName);
	}
	
	public User login(String phone,String pwd) {
		User user=userDao.selectByPhone(phone);
		if (user==null) {
			//手机号不存在
			return null;
			
		}else {
			//验证密码
			if (pwd.equals(user.getPwd())){
				return user;
			}
		}
		return null;
		
	}
	
	public void saveUserFollowUserInfo(int userA,int userB,String followTime) {
		userDao.saveUserFollowUserInfo(userA, userB, followTime);
	}


}
