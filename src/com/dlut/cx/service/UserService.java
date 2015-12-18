package com.dlut.cx.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author asus
 * last modified at 2015/12/04 by wujie
 */
public class UserService extends BaseService{
	/**
	 * 根据用户账号查询密码
	 * @param paramList (userId)
	 * @return 用户密码 password
	 */
	public String getPassword(List<Object> paramList) {
		String sql = "SELECT password FROM tyg_user WHERE userid = ?";
		Map<String, Object> userMap = null;
		return (userMap = this.getQueryMap(sql, paramList)) != null ? (String)userMap.get("password") : null;
	}
	
	/**
	 * 根据用户账号查询用户身份
	 * @param paramList (userid)
	 * @return 用户身份 role
	 */
	public String getRole(List<Object> paramList) {
		String sql = "SELECT role FROM tyg_user WHERE userid = ?";
		Map<String, Object> userMap = null;
		return (userMap = this.getQueryMap(sql, paramList)) != null ? (String)userMap.get("role") : null;
	}
	
	/**
	 * 设置用户 cookie
	 * @param paramList (userNameCookie, userId)
	 * @return 返回数据库操作影响行数
	 */
	public int setCookie(List<Object> paramList) {
		String sql = "UPDATE tyg_user SET cookie = ? WHERE userid = ?";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 查询用户的个人信息
	 * @param paramList (userid)
	 * @return 用户 userId，用户名 username，用户信誉度 creditrating,用户角色 role
	 */
	public Map<String, Object> getUserInfo(List<Object> paramList){
		String sql = "SELECT userid userId, username userName, creditrating creditRating,"
				+ " role FROM tyg_user WHERE userid = ?";
		return this.getQueryMap(sql, paramList);
	}
	
	/**
	 * 提交用户对移动app的反馈
	 * @param paramList 反馈内容fbkcontent 反馈时间 fbktime
	 * @return
	 */
	public int setUserFeedbackContent(List<Object> paramList){
		String sql = "INSERT INTO tyg_feedback(`fbkcontent`, fbktime) VALUES(?, ?)";
		System.err.println("user feedback sql is " + sql);
		System.err.println("user feedback paramlist is " + paramList);
		return this.execute(sql, paramList);
	}
	
	/*****************************************以下方法待测试***************************************************/
	/**
	 * 获取用户特定时间段内的订单记录
	 * @param paramList (userId, startTime, endTime)
	 * @return 预约号 recordId ，场馆名称 venuesName，场地号 location，预约起始时间 startTime，
	 * 结束时间 endTime
	 */
	public List<Map<String, Object>> getUserOrder(List<Object> paramList) {
		String sql = "select r.recordId, v.venuesName, r.location, r.startTime, r.endTime "
				+ "	from tbl_venues v,tbl_record r where v.venuesId = r.venuesId "
				+ " and r.userId = ? and r.startTime >= ? and r.endTime <= ?"
				+ " order by r.startTime desc";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 获取用户指定日期的预约记录
	 * @param paramList (userId, queryDay)
	 * @return 预约号 recordId ，场馆名称 venuesName，场地号 location，预约起始时间 startTime，
	 * 结束时间 endTime
	 */
	public List<Map<String, Object>> getReserveByDate(List<Object> paramList) {
		String sql = "select r.recordId, v.venuesName, r.location, r.startTime, r.endTime "
				+ "	from tbl_venues v,tbl_record r where v.venuesId = r.venuesId "
				+ " and r.userId = ? and date(r.startTime) = ? order by r.startTime desc";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 获取用户所有未过期的预约记录，按照预约开始时间升序排列
	 * @param paramList (userId)
	 * @return 预约号 recordId ，场馆名称 venuesName，场地号 location，预约起始时间（时间） startTime，
	 * 结束时间（时间） endTime ，预约日期（日期） recordDate
	 */
	public List<Map<String, Object>> getUseableReserve(List<Object> paramList) {
		String sql = "select recordId, venuesName, location, time(startTime) startTime,"
				+ " time(endTime) endTime, date(startTime) recordDate from tbl_venues v , tbl_record r where r.venuesId ="
				+ " v.venuesId and userId = ? and r.endTime > now() order by r.startTime asc";
		return this.getQueryList(sql, paramList);
	}
}
