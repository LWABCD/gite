package com.foo.util;

import java.util.Random;

public class GenerateValiCode {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getCode());
		// ����getCode()������ӡһ����λ���������֤����
	}

	/* * ����һ����ȡ�����֤��ķ�����getCode(); */
	public static String getCode() {
		String string = "0123456789";// ��������0-9 �� ��Сд��ĸ
		char[] ch = new char[5]; // ����һ���ַ��������ch ���� ��֤��
		for (int i = 0; i < 5; i++) {
			Random random = new Random();// ����һ���µ������������
			int index = random.nextInt(string.length());// ����[0,string.length)��Χ��intֵ ���ã������±�
			ch[i] = string.charAt(index);// charAt() : ����ָ���������� char ֵ ==�����浽�ַ��������ch����
		} // ��char��������ת��ΪString���ͱ��浽result
		// String result = new String(ch);//����һ��ֱ��ʹ�ù��췽�� String(char[] value) ������һ���µ�
		// String��ʹ���ʾ�ַ���������е�ǰ�������ַ����С�
		String result = String.valueOf(ch);// �������� String���� valueOf(char c) ������ char �������ַ�����ʾ��ʽ��
		return result;
	}
}
