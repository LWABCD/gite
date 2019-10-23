package com.foo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 数据库访问的工具类, 作用是封装共性代码, 供Dao类调用
 * 
 * @author 86151
 *
 */
public class Jdbc {

	// 连接参数

	final static String url = "jdbc:mysql://localhost:3306/jianshu";

	final static String user = "root";
	final static String password = "123";

	// 加载JDBC驱动程序, 向下兼容 ( JDBC4不再需要这个步骤 )
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 线程本地变量
	private static ThreadLocal<Connection> TL = new ThreadLocal<Connection>();
	
	private static boolean TESTMODE = false;
	
	public static void enableTestMode() {
		TESTMODE = true;
	}

	public static Connection getConnection() throws SQLException {
		// 先尝试从TL中获取Connection
		Connection conn = null;
		conn = TL.get();

		// 如果拿不到, 或拿到一个不可用的Connection:
		if (conn == null || conn.isClosed()) {
			// 建个新的Connection
			conn = DriverManager.getConnection(url, user, password);
			// 存到TL
			TL.set(conn);
		}
		return conn;
	}

	public static void startTransaction() {

		try {
			Connection conn = getConnection();
			if (conn != null)
				conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static void commit() {

		try {
			Connection conn = getConnection();
			// 如果在测试模式下, 什么也不做, 不提交数据, 数据库不会受到污染
			if(TESTMODE) {
				return;
			}
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws SQLException {

		Thread A = new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 100; i++) {
						Connection a = getConnection();
						System.out.println("线程A获得Connection为: " + a);
					}
				} catch (Exception e) {

				}
			}
		};
		Thread B = new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 100; i++) {
						Connection a = getConnection();
						System.out.println("线程B获得Connection为: " + a);
					}
				} catch (Exception e) {

				}
			}
		};

		A.start();
		B.start();

	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) {

		close(rs);
		close(stmt);
		close(conn);

	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close() {
		try {
			close(getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void update(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			// 建立连接
			conn = getConnection();
			// 预编译SQL命令
			stmt = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			// 执行SQL
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 释放资源
			close(stmt);
		}
	}

	public static <T> T select(String sql, ResultSetHandler<T> h, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// 建立连接
			conn = getConnection();
			// 预编译SQL命令
			stmt = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			// 执行SQL
			rs = stmt.executeQuery();
			// 解析结果集
			T obj = h.handle(rs);
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 释放资源
			close(rs);
			close(stmt);
		}

	}
	
		
	public static void rollback() {
		try {
			Connection conn = getConnection();
			if (conn != null)
				conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
