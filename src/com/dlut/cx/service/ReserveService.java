package com.dlut.cx.service;

import java.util.*;

import com.dlut.cx.util.C;


public class ReserveService extends BaseService {
	OrderService orderService = new OrderService();
	
	/**
	 * 用户预约操作
	 * @param paramList (venuesId, userId, location, startTime, endTime)
	 * @return 返回数据库操作影响行数
	 */
	public int makeReserve(List<Object> paramList) {
		String sql = "INSERT INTO tyg_order(orderid, venueid, userid, locationid,"
				+ " starttime, endtime, maketime, status) VALUES(?, ?, ?, ?, ?, ?, ?,'" + 
				C.recordStatus.NEW + "')";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 用户取消预约
	 * @param paramList (orderId)
	 * @return 返回数据库操作影响行数
	 */
	public int cancelReserve(List<Object> paramList) {
		//TODO
		//if the order's status is not right
		if(!checkOrderStatus(orderService.queryOrderStatus(paramList), C.recordStatus.NEW))
			return 0;
		String sql = "UPDATE tyg_order SET status = '" + C.recordStatus.CANCELED 
				+"' WHERE orderid = ?";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 使一个订单为完成违约态
	 * @param paraList (orderId)
	 * @return
	 */
	public int violateReserve(List<Object> paramList) {
		//TODO check the order's status
		if(!checkOrderStatus(orderService.queryOrderStatus(paramList), C.recordStatus.NEW))
			return 0;
		String sql = "UPDATE tyg_order SET status = '" + C.recordStatus.VIOLATED 
				+"' WHERE orderid = ?";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 使一个订单为完成状态
	 * @param paraList (orderId)
	 * @return
	 */
	public int finishReserve(List<Object> paramList) {
		//TODO check the order's status
		if(!checkOrderStatus(orderService.queryOrderStatus(paramList), C.recordStatus.NEW))
			return 0;
		String sql = "UPDATE tyg_order SET status = '" + C.recordStatus.FINISHED 
				+"' WHERE orderid = ?";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 使一个订单变为错误状态
	 * @param paraList (orderId)
	 * @return
	 */
	public int errorReserve(List<Object> paramList) {
		//TODO check the order's status
		String sql = "UPDATE tyg_order SET status = '" + C.recordStatus.ERROR 
				+"' WHERE orderid = ?";
		return this.execute(sql, paramList);
	}
	
	//TODO此方法有待改写
	/**
	 * 检查该时段，所选场地是否已经被预约，避免同一场地同一时间多次使用
	 * @param paramList (venuesId, location, startTime)
	 * @return 该时段，所选场地的预约数目，如果为 0 ，则可进行预约
	 */
	public int checkReserve(List<Object> paramList) {
		String sql = "select count(*) from tbl_record where venuesId = ? and"
				+ " location = ? and startTime = ?";
		return this.getCount(sql, paramList);
	}
	
	/**
	 * 验证订单状态是否符合要求
	 * @param statusMap
	 * @param shouldbe
	 * @return
	 */
	private  boolean checkOrderStatus(Map<String, Object> statusMap, String shouldbe) {
		return shouldbe.trim().equals(((String)statusMap.get("status")).trim());
	}
	
}
