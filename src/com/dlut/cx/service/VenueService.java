package com.dlut.cx.service;

import java.util.*;

import com.dlut.cx.util.C;

public class VenueService extends BaseService {
	/**
	 * 查询所有开馆场馆的详细信息
	 * @return 场馆 venusid， 场馆名 venuename，位置数 locationquantity， 场馆费用 charge， 开馆时间 opentime， 
	 * 闭馆时间 closetime
	 */
	public List<Map<String, Object>> getAllOpenVenueInfo() {
		String sql = "SELECT venueid venueId, venuename venueName, locationquantity locationQuantity, "
				+ "charge charge, opentime openTime, "
				+ "closetime closeTime FROM tyg_venue WHERE isopen = 1";
		return this.getQueryList(sql, null);
	}
	
	/**
	 * 查询指定场馆的信息
	 * @param paramList (venueId)
	 * @return 指定场馆 venueid， 场馆名 venuename， 场地数 locationqauntity， 场馆费用 charge， 开馆时间 opentime， 
	 * 闭馆时间 closetime
	 */
	public Map<String, Object> getVenueInfoByVenueId(List<Object> paramList) {
		String sql = "SELECT venueid venueId, venuename venueName, locationquantity locationQuantity,"
				+ " charge, opentime openTime, "
				+ "closetime closeTime FROM tyg_venue WHERE venueid = ?";
		return this.getQueryMap(sql, paramList);
	}
	

	/**
	 * 查询指定场馆，指定日期和时间的位置信息
	 * @param paramList (venueId,startTime, endTime)
	 * @return Location对象List
	 */
	public List<Map<String,Object>> getUnavailableLocationList(List<Object> paramList){
		//生成Location对象数组
		//TODO 
		List<Map<String,Object>> result;
		paramList.add(paramList.get(1));
		paramList.add(paramList.get(2));
		System.err.println(paramList);
		String sql = "SELECT o.locationid locationId,locationname locationName,type,l.descr descr,starttime startTime,"
				+ "endtime endTime FROM tyg_order o,tyg_location l "
				+ "WHERE o.venueid = ? AND status = '" + C.recordStatus.NEW + "' AND o.locationid=l.locationid "
				+ "AND NOT((endtime >= ? AND starttime >= ?) OR (endtime <= ? AND starttime <= ?))";
		
		System.err.println("SQL is " + sql);
		result = this.getQueryList(sql, paramList);
		System.err.println("getUnavailableLocationList: result is " + (result == null?"null":result.toString()));
		return result;
	}
	
	/**
	 * 查询指定场馆，指定日期对应的预约信息
	 * @param paramList (venueId, queryDay)
	 * @return 预约号 recordid，用户 userid，场地号 locationid，预约起始时间 starttime，结束时间 endtime等等
	 * 下单时间 maketime
	 */
	public List<Map<String, Object>> getVenueOrderList(List<Object> paramList) {
		if(paramList == null || paramList.size() == 0) return null;
		paramList.add(paramList.get(1));
		System.err.println("paramList is " + paramList);
		String sql = "SELECT orderid orderId, userid userId, locationid locationId, starttime startTime, "
				+ "endtime endTime, maketime makeTime, status, totalmoney totalMoney, paymentterm paymentTerm, "
				+ "descr FROM tyg_order WHERE venueid = ? AND "
				+ "starttime >= UNIX_TIMESTAMP(?)  AND endtime <= (UNIX_TIMESTAMP(?) + 86399)  ORDER BY maketime DESC";
		return this.getQueryList(sql, paramList);
	}
	
	
	/*****************以下待测试********************/
	/**
	 * 查询包含指定运动的场馆的信息
	 * @param paramList (sportid)
	 * @return 指定场馆 venuseid， 场馆名 venuename， 场地数 locationquantity， 场馆费用 charge， 开馆时间 opentime， 
	 * 闭馆时间 closetime
	 */
	public List<Map<String, Object>> getVenuesInfoBySport(List<Object> paramList) {
		String sql = "SELECT venueid, venuename, locationquantity, charge, opentime, "
				+ "closetime FROM tyg_venue WHERE sportid = ?";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 查询指定场馆，指定订单开始生效日期，指定时间段对应的预约信息
	 * @param paramList (venuesId, queryDay, queryTime)
	 * @return 预约号 recordid，用户 userId，场地号 location，预约起始时间 startTime，结束时间 endTime
	 */
	public List<Map<String, Object>> getVenuesRecordByStartEndTime(List<Object> paramList) {
		String sql = "SELECT recordid, userid, locationid, starttime,"
				+ "endtime FROM tyg_record WHERE venueid = ? AND "
				+ "starttime = ? AND endtime = ?";
		return this.getQueryList(sql, paramList);
	}
	
	
	
}
