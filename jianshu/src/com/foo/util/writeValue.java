package com.foo.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class writeValue {

	//��ǰ����Ӧjson����
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
