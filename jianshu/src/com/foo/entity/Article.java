package com.foo.entity;

import java.util.Date;

import javax.swing.Spring;

/**
 * 实体类：文章
 * @author jiang
 *
 */
public class Article {

	 private int    articleId;
	 private int    author;
	 private String title;
	 private String content;
	 private float  totalDiamond;
	 private Date   postTime;
	 private int    wordNum;
	 private int    readNum;
	 private int    commentNum;
	 private int    likeNum;
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public float getTotalDiamond() {
		return totalDiamond;
	}
	public void setTotalDiamond(float totalDiamond) {
		this.totalDiamond = totalDiamond;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public int getWordNum() {
		return wordNum;
	}
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	
	
	public Article() {
		super();
	}
	public Article(int articleId, int author, String title, String content, float totalDiamond, Date postTime,
			int wordNum, int readNum, int commentNum, int likeNum) {
		super();
		this.articleId = articleId;
		this.author = author;
		this.title = title;
		this.content = content;
		this.totalDiamond = totalDiamond;
		this.postTime = postTime;
		this.wordNum = wordNum;
		this.readNum = readNum;
		this.commentNum = commentNum;
		this.likeNum = likeNum;
	}
	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", author=" + author + ", title=" + title + ", content=" + content
				+ ", totalDiamond=" + totalDiamond + ", postTime=" + postTime + ", wordNum=" + wordNum + ", readNum="
				+ readNum + ", commentNum=" + commentNum + ", likeNum=" + likeNum + "]";
	}
	 
	
	
}
