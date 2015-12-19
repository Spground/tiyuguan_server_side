package com.dlut.cx.service;

import java.util.List;
import java.util.Map;

import com.dlut.cx.util.C;
import com.dlut.cx.util.GeneralUtil;

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
	
	
	/**
	 * 获取用户指定时间段内的订单记录,如果未指定startTime 和 endTime,将默认筛选全部 并按照maketime降序排列
	 * @param paramList (userId, startTime, endTime)
	 * @return 预约号 orderId ，场馆名称 venueName，场地号 locationId，订单起始时间 startTime ，订单结束时间endTime....
	 * 结束时间 endTime
	 */
	public List<Map<String, Object>> getUserAllOrderByStarttimeAndEndtime(List<Object> paramList) {
		if(paramList == null || paramList.size() == 0)
			return null;
		String sql = "";
		if(paramList.size() == 1)//指定了userId
			return getUserAllOrder(paramList);
		else if(paramList.size() == 2)//指定了userId 和 startTime
			 sql = "SELECT orderid orderId, venuename venueName, o.venueid venueId, locationid locationId,"
						+ "starttime startTime, endtime endTime, maketime makeTime, totalmoney totalMoney,"
						+ "paymentterm paymentTerm, status Status, descr descr"
						+ "	FROM tyg_venue v,tyg_order o WHERE v.venueid = o.venueid "
						+ " AND userid = ? AND starttime >= ? ORDER BY maketime DESC";
		else if(paramList.size() == 3)//指定了userId 和 startTime 和 endTime
			 sql = "SELECT orderid orderId, venuename venueName, locationid locationId, o.venueid venueId,"
						+ "starttime startTime, endtime endTime, maketime makeTime, totalmoney totalMoney,"
						+ "paymentterm paymentTerm, status Status, descr descr"
						+ "	FROM tyg_venue v,tyg_order o WHERE v.venueid = o.venueid "
						+ " AND userid = ? AND starttime >= ? AND endtime <= ? ORDER BY maketime DESC";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 返回指定用户所有的订单，按maketime降序排列
	 * @param paramList（userId）
	 * @return
	 */
	public List<Map<String, Object>> getUserAllOrder(List<Object> paramList) {
		if(paramList == null || paramList.size() == 0)
			return null;
		String sql = "SELECT orderid orderId, venuename venueName, locationid locationId, o.venueid venueId,"
				+ "starttime startTime, endtime endTime, maketime makeTime, totalmoney totalMoney,"
				+ "paymentterm paymentTerm, status Status, descr descr"
				+ "	FROM tyg_venue v, tyg_order o WHERE v.venueid = o.venueid "
				+ " AND userid = ? ORDER BY maketime DESC";
		return this.getQueryList(sql, paramList);
	}
	
	
	/**
	 * 获取用户指定下单日期的订单记录
	 * @param paramList (userId, queryStartDay 格式yyyy-MM-dd, queryEndDay 格式yyyy-MM-dd),
	 *  如果queryStartDay 和 queryEndDay缺省，则默认当前日期，如果queryEndDay缺省，默认queryStartDay加上24小时
	 * @return 预约号 orderId ，场馆名称 venueName，场地号 locationId，预约起始时间 startTime等等
	 * 结束时间 endTime
	 */
	public List<Map<String, Object>> getUserAllOrderByMakeDate(List<Object> paramList) {
		if(paramList == null || paramList.size() == 0)
			return null;
		if(paramList.size() == 2)
			paramList.add(paramList.get(1));
		if(paramList.size() == 1){
			paramList.add(GeneralUtil.getNowyyyyMMddDateString());
			paramList.add(GeneralUtil.getNowyyyyMMddDateString());
		}
			
		String sql = "SELECT orderid orderId, venuename venueName, locationid locationId, o.venueid venueId,"
					+ "starttime startTime, endtime endTime, maketime makeTime, totalmoney totalMoney,"
					+ "paymentterm paymentTerm, status Status, descr descr"
					+ "	FROM tyg_venue v, tyg_order o WHERE v.venueid = o.venueid "
					+ " AND userid = ? AND UNIX_TIMESTAMP(?) <= maketime AND (UNIX_TIMESTAMP(?) + 86399) >= maketime "
					+ "ORDER BY maketime DESC";
		return this.getQueryList(sql, paramList);
	}
	
	/*****************************************以下方法待测试***************************************************/
	/**
	 * 获取用户指定订单状态的订单，按照下单时间降序排列
	 * @param paramList (userId, Status)
	 * @return 预约号 recordId ，场馆名称 venuesName，场地号 location，预约起始时间（时间） startTime，
	 * 结束时间（时间） endTime ，预约日期（日期） recordDate
	 */
	public List<Map<String, Object>> getUserAllOrderByStatus(List<Object> paramList) {
		if(paramList == null || paramList.size() == 0)
			return null;
		if(paramList.size() == 1){
			paramList.add(C.recordStatus.NEW);//缺省查询状态为NEW的订单
		}
		
		String sql = "SELECT orderid orderId, venuename venueName, locationid locationId, o.venueid venueId,"
				+ "starttime startTime, endtime endTime, maketime makeTime, totalmoney totalMoney,"
				+ "paymentterm paymentTerm, status Status, descr descr"
				+ "	FROM tyg_venue v, tyg_order o WHERE v.venueid = o.venueid "
				+ " AND userid = ? AND status = ? "
				+ "ORDER BY maketime DESC";
		return this.getQueryList(sql, paramList);
	}
}
