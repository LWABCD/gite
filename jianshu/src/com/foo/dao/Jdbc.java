package com.foo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ���ݿ���ʵĹ�����, �����Ƿ�װ���Դ���, ��Dao�����
 * 
 * @author 86151
 *
 */
public class Jdbc {

	// ���Ӳ���

	final static String url = "jdbc:mysql://localhost:3306/jianshu";

	final static String user = "root";
	final static String password = "123";

	// ����JDBC��������, ���¼��� ( JDBC4������Ҫ������� )
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// �̱߳��ر���
	private static ThreadLocal<Connection> TL = new ThreadLocal<Connection>();
	
	private static boolean TESTMODE = false;
	
	public static void enableTestMode() {
		TESTMODE = true;
	}

	public static Connection getConnection() throws SQLException {
		// �ȳ��Դ�TL�л�ȡConnection
		Connection conn = null;
		conn = TL.get();

		// ����ò���, ���õ�һ�������õ�Connection:
		if (conn == null || conn.isClosed()) {
			// �����µ�Connection
			conn = DriverManager.getConnection(url, user, password);
			// �浽TL
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
			// ����ڲ���ģʽ��, ʲôҲ����, ���ύ����, ���ݿⲻ���ܵ���Ⱦ
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
						System.out.println("�߳�A���ConnectionΪ: " + a);
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
						System.out.println("�߳�B���ConnectionΪ: " + a);
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
			// ��������
			conn = getConnection();
			// Ԥ����SQL����
			stmt = conn.prepareStatement(sql);
			// ���ò���
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// �ͷ���Դ
			close(stmt);
		}
	}

	public static <T> T select(String sql, ResultSetHandler<T> h, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// ��������
			conn = getConnection();
			// Ԥ����SQL����
			stmt = conn.prepareStatement(sql);
			// ���ò���
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL
			rs = stmt.executeQuery();
			// ���������
			T obj = h.handle(rs);
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// �ͷ���Դ
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
