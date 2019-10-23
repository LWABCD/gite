package com.foo.service;
import java.util.List;
import org.junit.Test;
import com.foo.BaseTest;
import com.foo.entity.User;

public class TestUserService extends BaseTest {

	private UserService userService=new UserService();
	/***
	 * 测试userService----getRecommedAuthors()方法
	 */
	@Test
	public void test() {
		List<User> user=userService.getRecommedAuthors();
		System.out.println(user);
	}
	/***
	 * 测试userService----getAuthorById()方法
	 */
	@Test
	public void testgetAuthorById() {
		User user=userService.getAuthorById(20);
		System.out.println(user);
	}
	
	@Test
	public void testUserFollowedUser() {
		userService.saveUserFollowUserInfo(1,2,"3");
	}

}
