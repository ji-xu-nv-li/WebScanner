package com.wsbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JdbcTemplate {
	
	static{
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			String user = "webscanner";
			String password = "123456";
			String url = "jdbc:mysql://192.168.124.16:3306/webscanner";
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Map<String,Object>> executeQuery(String sql, List<Object> values) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			for (int index = 0; index < values.size(); index++) {
				ps.setObject(index + 1, values.get(index));
			}
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int index = 1 ; index <= columnCount; index++) {
					String name = metaData.getColumnName(index);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				result.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, ps, rs); 
		}
		return result;
	}
	
	public static int executeUpdate(String sql, List<Object> values) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			for (int index = 0; index < values.size(); index++) {
				ps.setObject(index + 1, values.get(index));
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, ps, rs); 
		}
		return 0;
	}
	
	/**
	 * 方法描述：批量插入数据
	 * 作者： yxm
	 * 创建时间： 2020年2月6日  下午11:37:20
	 * @param sql
	 * @param valuesList
	 * @return
	 * @throws
	 */
	public static int[] executeBatch(String sql, List<List<Object>> valuesList) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			for (List<Object> values : valuesList) {
				for (int index = 0; index < values.size(); index++) {
					ps.setObject(index + 1, values.get(index));
				}
				ps.addBatch();
			}
			return ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, ps, rs); 
		}
		return null;
	}

	private static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args) throws Exception {
		String sql = "select * from Test";
		List<Map<String, Object>> list = executeQuery(sql, new ArrayList<Object>());
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
}
