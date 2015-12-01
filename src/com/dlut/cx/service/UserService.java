package com.dlut.cx.service;

import java.util.List;
import java.util.Map;

public class UserService extends BaseService{
	/**
	 * 根据用户账号查询密码
	 * @param paramList (userId)
	 * @return 用户密码 password
	 */
	public String getPassword(List<Object> paramList) {
		String sql = "select password from tbl_user where userId = ?";
		Map<String, String> userMap = null;
		return (userMap = this.getQueryMap(sql, paramList)) != null ? userMap.get("password") : null;
	}
	
	/**
	 * 根据用户账号查询用户身份
	 * @param paramList (userId)
	 * @return 用户身份 userRole
	 */
	public String getRole(List<Object> paramList) {
		String sql = "select userRole from tbl_user where userId = ?";
		Map<String, String> userMap = null;
		return (userMap = this.getQueryMap(sql, paramList)) != null ? userMap.get("userRole") : null;
	}
	
	/**
	 * 设置用户 cookie
	 * @param paramList (userNameCookie, userId)
	 * @return 返回数据库操作影响行数
	 */
	public int setCookie(List<Object> paramList) {
		String sql = "update tbl_user set cookie = ? where userId = ?";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 查询用户的个人信息
	 * @param paramList (userId)
	 * @return 用户 userId，用户名 userName，用户信誉度 creditWorthiness
	 */
	public List<Map<String, String>> getUserInfo(List<Object> paramList){
		String sql = "select userId, userName, creditWorthiness from tbl_user where userId = ?";
		return this.getQueryList(sql, paramList);
	}
	
	public int setUserFeedbackContent(List<Object> paramList){
		String sql = "insert into tbl_feedback(`fbk_content`, `fbk_time`) values(?, ?)";
		System.err.println("user feedback sql is " + sql);
		System.err.println("user feedback paramlist is " + paramList);
		return this.execute(sql, paramList);
	}
}
