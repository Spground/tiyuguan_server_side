package com.dlut.cx.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;

import com.dlut.cx.db.*;
public class BaseService {
		
	protected Connection conn = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
		
	/**
	 * 
	 * @param sql
	 * @param paramList
	 * @return 执行增、删、改操作，返回影响行数；sql 语句为空时返回 -1，pstmt 初始化失败时返回-2
	 */
	protected int execute(String sql,List<Object> paramList) {
		if(sql == null||sql.trim().equals("")) {
			return -1;
		}
		int result = 0;
		try {
			conn = BaseService.getConnection();
			pstmt = BaseService.getPreparedStatement(conn,sql);
			setPreparedStatementParam(pstmt,paramList);
			if(pstmt == null)
				return -2;
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			//throw new Exception(e);
		}finally {
			BaseService.closeStatement(pstmt);
			BaseService.closeConn(conn);
		}
		return result;
	}
		
	/**
	 * 
	 * @param sql
	 * @param paramList
	 * @return 获得满足指定条件的记录行数；sql 语句为空时返回 -1，pstmt 初始化失败时返回-2
	 */
	protected int getCount(String sql,List<Object> paramList) {
		if(sql == null||sql.trim().equals("")) {
			return -1;
		}
		int result = 0;
		try {
			conn = BaseService.getConnection();
			pstmt = BaseService.getPreparedStatement(conn,sql);
			setPreparedStatementParam(pstmt,paramList);
			if(pstmt == null)
				return -2;
			rs = BaseService.getResultSet(pstmt);
			if(rs.next())
				result = rs.getInt(1);
		}catch(Exception e) {
			//throw new Exception(e);
		}finally {
			BaseService.closeStatement(pstmt);
			BaseService.closeConn(conn);
			BaseService.closeResultSet(rs);
		}
		return result;
	}
		
	/**
	 * 
	 * @param sql
	 * @param paramList
	 * @return 查询单行记录返回map
	 */
	protected Map<String, Object> getQueryMap(String sql,List<Object> paramList) {
		List<Map<String, Object>> resultList = this.getQueryList(sql, paramList);
		return resultList == null ? null : resultList.get(0);
	}
		
	/**
	 * 
	 * @param sql
	 * @param paramList
	 * @return 查询多行记录 ；sql 语句为空或 pstmt，rs 初始化失败时返回 null
	 */
	protected List<Map<String, Object>> getQueryList(String sql,List<Object> paramList) {
		if(sql == null||sql.trim().equals("")) {
			return null;
		}
		List<Map<String,Object>> queryList = null;
		try {
			conn = BaseService.getConnection();
			pstmt = BaseService.getPreparedStatement(conn,sql);
			setPreparedStatementParam(pstmt,paramList);
			if(pstmt == null)
				return null;
			rs = BaseService.getResultSet(pstmt);
			queryList = getQueryList(rs);	
		}catch(Exception e){
			//throw new Exception(e);
		}finally{
			BaseService.closeResultSet(rs);
			BaseService.closeStatement(pstmt);
			BaseService.closeConn(conn);
		}
		return queryList;
	}
		
	private static void setPreparedStatementParam(PreparedStatement pstmt,List<Object> paramList)throws Exception {
		if(pstmt == null||paramList == null||paramList.isEmpty())
			return;
		DateFormat df=DateFormat.getDateTimeInstance();
		for(int i = 0;i < paramList.size();i++) {
			if(paramList.get(i) instanceof Integer) {
				int paramValue=((Integer)paramList.get(i)).intValue();
				pstmt.setInt(i + 1,paramValue);
			}
			else if(paramList.get(i) instanceof Float) {
				float paramValue=((Float)paramList.get(i)).floatValue();
				pstmt.setFloat(i + 1,paramValue);
			}
			else if(paramList.get(i) instanceof Double) {
				double paramValue=((Double)paramList.get(i)).doubleValue();
				pstmt.setDouble(i + 1,paramValue);
			}
			else if(paramList.get(i) instanceof Date) {
				pstmt.setString(i + 1,df.format((Date)paramList.get(i)));
			}
			else if(paramList.get(i) instanceof Long) {
				long paramValue=((Long)paramList.get(i)).longValue();
				pstmt.setLong(i + 1,paramValue);
			}
			else if(paramList.get(i) instanceof String) {
				pstmt.setString(i + 1,(String)paramList.get(i));
			}
			else if(paramList.get(i) instanceof Boolean) {
				pstmt.setBoolean(i + 1,(Boolean)paramList.get(i));
			}
		}
	}
		
	private static Connection getConnection()throws Exception {
		return DBConnection.getConnection();
	}
		
	private static PreparedStatement getPreparedStatement(Connection conn,String sql)throws Exception {
		if(conn == null||sql == null||sql.trim().equals(""))
			return null;
		PreparedStatement pstmt = conn.prepareStatement(sql.trim());
		return pstmt;
	}
		
	private static ResultSet getResultSet(PreparedStatement pstmt)throws Exception {
		if(pstmt == null)
			return null;
		ResultSet rs = pstmt.executeQuery();
		return rs;
	} 
	
	/**
	 * 返回SQL查询的结果，每一个Map代表一行记录，返回的类型是数据库的实际类型
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private static List<Map<String, Object>> getQueryList(ResultSet rs)throws Exception {
		if(rs == null)
			return null;
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int columnCount = rsMetaData.getColumnCount();
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		while(rs.next()) {
			Map<String,Object> dataMap = new HashMap<String, Object>();
			for(int i = 0;i < columnCount;i++)
				dataMap.put(rsMetaData.getColumnLabel(i + 1), rs.getObject(i + 1));
			dataList.add(dataMap);
		}
		return dataList;
	}
		
	private static void closeConn(Connection conn) {
		if(conn==null)
			return;
		try {
			conn.close();
		}catch(SQLException e) {
		}
	}
		
	private static void closeStatement(Statement stmt) {
		if(stmt==null)
			return;
		try {
			stmt.close();
		}catch(SQLException e) {
		}
	}
		
	private static void closeResultSet(ResultSet rs) {
		if(rs==null)
			return;
		try {
			rs.close();
		}catch(SQLException e) {
		}
	}
}