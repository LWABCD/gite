package com.foo;

import org.junit.Before;

import com.foo.dao.Jdbc;

/**
 * ��Ԫ������ĸ���
 * <p> 
 * ���е�Ԫ�����඼�̳��������
 * @author 86151
 *
 */
public class BaseTest {

	// ��ÿ��@Test��������֮ǰ, �����Զ��ȵ������setUp
	@Before
	public void setUp() throws Exception {
		// ���ò���ģʽ
		Jdbc.enableTestMode();
	}

}
