package com.dlut.cx.service;

import java.util.*;

import com.dlut.cx.util.C;


public class ReserveService extends BaseService {
	OrderService orderService = new OrderService();
	
	/**
	 * 用户预约操作
	 * @param paramList (orderId, venueId, userId, locationid, startTime, endTime, makeTime)
	 * @return 返回下单成功的订单号
	 */
	public int makeReserve(List<Object> paramList) {
		//TODO检查订单是否在同一时间段内有冲突
		//冲突检查
		List<Object> list = new ArrayList<>();
		list.add(paramList.get(1));//venueid
		list.add(paramList.get(3));//locationid
		list.add(paramList.get(4));//starttime
		list.add(paramList.get(5));//endtime
		if(checkOrderConflictInTime(list))
					return 0;
		
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
	 * 检查该时段是否存在时段上的冲突，所选场馆位置是否已经被预约，避免同一位置同一时间多次被预定
	 * @param paramList (venueId, locationId, startTime， endTime)
	 * @return 该时段，所选场地的预约数目，如果为 true ，则冲突 false不冲突
	 */
	public boolean checkOrderConflictInTime(List<Object> paramList) {
		if(paramList == null || paramList.size() < 4) return true;
		paramList.add(paramList.get(2));
		paramList.add(paramList.get(3));
		String sql = "SELECT COUNT(*) FROM tyg_order WHERE venueid = ? AND locationid = ? AND status='"
				+ C.recordStatus.NEW + "' "
				+ "AND NOT((endtime >= ? AND starttime >= ?) OR (endtime <= ? AND starttime <= ?))";
		return this.getCount(sql, paramList) != 0;
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
