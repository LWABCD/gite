package com.foo.entity;

import java.util.Date;

/**
 * �û���ע�û�
 * @author LWABCD
 *
 */

public class Userfollowuser {

	private int userA;   //��ע��
	private int userB;   //����ע����
	private Date followTime;   //��עʱ��
	
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
