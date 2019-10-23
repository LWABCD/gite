package com.foo.entity;
/**
 * 实体类：用户
 * @author jiang
 *
 */

import java.util.Date;

import org.omg.CORBA.PRIVATE_MEMBER;

public class User {
     private int uid;
     private String nickName;
     private String phone;
     private String pwd;
     private String email;
     private String headImg;
     private String gender;
     private String info;
     private int    totalWordNum;
     private int    totalLikeNum;
     private int    totalFollowNum;
     private int    totalFansNum;
     private int    totalReceiveDiamonds;
     private float  diamondBalance;
     private int    totalArticleNum;
     private Date   registerTime;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getTotalWordNum() {
		return totalWordNum;
	}
	public void setTotalWordNum(int totalWordNum) {
		this.totalWordNum = totalWordNum;
	}
	public int getTotalLikeNum() {
		return totalLikeNum;
	}
	public void setTotalLikeNum(int totalLikeNum) {
		this.totalLikeNum = totalLikeNum;
	}
	public int getTotalFollowNum() {
		return totalFollowNum;
	}
	public void setTotalFollowNum(int totalFollowNum) {
		this.totalFollowNum = totalFollowNum;
	}
	public int getTotalFansNum() {
		return totalFansNum;
	}
	public void setTotalFansNum(int totalFansNum) {
		this.totalFansNum = totalFansNum;
	}
	public int getTotalReceiveDiamonds() {
		return totalReceiveDiamonds;
	}
	public void setTotalReceiveDiamonds(int totalReceiveDiamonds) {
		this.totalReceiveDiamonds = totalReceiveDiamonds;
	}
	public float getDiamondBalance() {
		return diamondBalance;
	}
	public void setDiamondBalance(float diamondBalance) {
		this.diamondBalance = diamondBalance;
	}
	public int getTotalArticleNum() {
		return totalArticleNum;
	}
	public void setTotalArticleNum(int totalArticleNum) {
		this.totalArticleNum = totalArticleNum;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public User() {
		super();
	}
	public User(int uid, String nickName, String phone, String pwd, String email, String headImg, String gender,
			String info, int totalWordNum, int totalLikeNum, int totalFollowNum, int totalFansNum,
			int totalReceiveDiamonds, float diamondBalance, int totalArticleNum, Date registerTime) {
		super();
		this.uid = uid;
		this.nickName = nickName;
		this.phone = phone;
		this.pwd = pwd;
		this.email = email;
		this.headImg = headImg;
		this.gender = gender;
		this.info = info;
		this.totalWordNum = totalWordNum;
		this.totalLikeNum = totalLikeNum;
		this.totalFollowNum = totalFollowNum;
		this.totalFansNum = totalFansNum;
		this.totalReceiveDiamonds = totalReceiveDiamonds;
		this.diamondBalance = diamondBalance;
		this.totalArticleNum = totalArticleNum;
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", nickName=" + nickName + ", phone=" + phone + ", pwd=" + pwd + ", email=" + email
				+ ", headImg=" + headImg + ", gender=" + gender + ", info=" + info + ", totalWordNum=" + totalWordNum
				+ ", totalLikeNum=" + totalLikeNum + ", totalFollowNum=" + totalFollowNum + ", totalFansNum="
				+ totalFansNum + ", totalReceiveDiamonds=" + totalReceiveDiamonds + ", diamondBalance=" + diamondBalance
				+ ", totalArticleNum=" + totalArticleNum + ", registerTime=" + registerTime + "]";
	}
       
	

}
