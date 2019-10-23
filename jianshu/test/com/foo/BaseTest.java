package com.foo;

import org.junit.Before;

import com.foo.dao.Jdbc;

/**
 * 单元测试类的父类
 * <p> 
 * 所有单元测试类都继承自这个类
 * @author 86151
 *
 */
public class BaseTest {

	// 在每个@Test方法运行之前, 将会自动先调用这个setUp
	@Before
	public void setUp() throws Exception {
		// 启用测试模式
		Jdbc.enableTestMode();
	}

}
