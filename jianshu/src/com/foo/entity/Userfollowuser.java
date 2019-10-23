package com.foo.entity;

import java.util.Date;

/**
 * 用户关注用户
 * @author LWABCD
 *
 */

public class Userfollowuser {

	private int userA;   //关注者
	private int userB;   //被关注的人
	private Date followTime;   //关注时间
	
	public int getUserA() {
		return userA;
	}
	public void setUserA(int userA) {
		this.userA = userA;
	}
	public int getUserB() {
		return userB;
	}
	public void setUserB(int userB) {
		this.userB = userB;
	}
	public Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
}
