package com.foo.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class writeValue {

	//向前端响应json数据
	public static void writeValue(HttpServletResponse response,String json) {
		try {
			PrintWriter printWriter=response.getWriter();
			printWriter.write(json);
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
