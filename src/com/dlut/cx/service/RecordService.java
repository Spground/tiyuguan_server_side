package com.dlut.cx.service;

import java.util.*;


public class RecordService extends BaseService{
	
	/**
	 * 查询指定场馆的不同时间段的预约数目
	 * @param paramList (venuesId)
	 * @return 预约开始时间 startTime ，结束时间 endTime ，该时间段的预约数目 orderNum
	 */
	public List<Map<String, String>> getReserveRecordCount(List<Object> paramList) {
		String sql = "select startTime, endTime, count(*) orderNum from tbl_record where "
				+ "date(startTime) = date(now()) and venuesId = ? group by(startTime) order by startTime asc";
		return this.getQueryList(sql, paramList);
	}

	/**
	 * 查询当前时间之后的所有用户预约
	 * @return 预约的订单号 recordId ， 用户 userId ， 场馆名称 venuesName ，预约开始时间 startTime ，
	 * 结束时间 endTime ， 预约日期 recordDate ，场地号  location
	 */
	public List<Map<String, String>> getReserveRecord() {
		String sql = "select recordId, userId, venuesName, time(startTime) startTime, time(endTime) endTime,"
				+ " date(startTime) recordDate, location from tbl_venues i, tbl_record j"
				+ " where i.venuesId = j.venuesId and endTime > now() order by j.startTime asc";
		return this.getQueryList(sql, null);
	}
}
