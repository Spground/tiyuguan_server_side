package com.dlut.cx.service;

import java.util.*;


public class OrderService extends BaseService{
	
	/**
	 * 查询指定场馆的不同时间段的预约数目
	 * @param paramList (venuesId)
	 * @return 预约开始时间 startTime ，结束时间 endTime ，该时间段的预约数目 orderNum
	 */
	public List<Map<String, Object>> getReserveRecordCount(List<Object> paramList) {
		String sql = "SELECT starttime startTime, endtime endTime, COUNT(*) orderNum FROM tyg_order WHERE "
				+ "starttime = UNIX_TIMESTAMP(NOW()) AND venueid = ? GROUP BY starttime ORDER BY starttime ASC";
		return this.getQueryList(sql, paramList);
	}

	/**
	 * 查询订单结束时间是当前时间之后的所有用户订单
	 * @return 预约的订单号 recordId ， 用户 userId ， 场馆名称 venuesName ，预约开始时间 startTime ，
	 * 结束时间 endTime ， 预约日期 recordDate ，场地号  location
	 */
	public List<Map<String, Object>> getEndtimeInFutureOrder() {
		String sql = "SELECT orderid orderId, userid userId, locationid locationId,i.venueid venueId,"
				+ "venuename venueName,starttime startTime,endtime endTime,maketime makeTime,"
				+ "totalmoney totalMoney, paymentterm paymentTerm, status, descr FROM tyg_venue i, "
				+ "tyg_order j WHERE i.venueid = j.venueid AND endtime > unix_timestamp(now()) "
				+ "ORDER BY j.starttime ASC;";
		return this.getQueryList(sql, null);
	}
	
	/**
	 * 获取指定用户所有的历史订单，按照下单时间降序排列
	 * @param paramList (userId)
	 * @return 预约号 recordId ，场馆名称 venuesName，场地号 location，预约起始时间（时间） startTime，
	 * 结束时间（时间） endTime ，预约日期（日期） recordDate
	 */
	public List<Map<String, Object>> getUserHistoryOrder(List<Object> paramList) {
		String sql = "SELECT orderid  orderId, o.venueid venueId, venuename venueName, locationid locationId, "
		  + "totalmoney totalMoney, paymentterm paymentTerm, status, descr, maketime  makeTime, endtime endTime, "
		  + "starttime startTime FROM tyg_venue v , tyg_order o WHERE v.venueid = o.venueid AND userid = ? AND o.maketime <= UNIX_TIMESTAMP(NOW())" 
		  +  "ORDER BY o.maketime DESC";
		return this.getQueryList(sql, paramList);
	}
	
}
