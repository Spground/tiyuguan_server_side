package com.dlut.cx.service;

import java.util.*;

public class VenueService extends BaseService {
	/**
	 * 查询所有场馆的信息列表
	 * @return 场馆 venuseid， 场馆名 venuesname，位置数 locationquantity， 场馆费用 charge， 开馆时间 opentime， 
	 * 闭馆时间 closetime
	 */
	public List<Map<String, Object>> getVenuesInfo() {
		String sql = "SELECT venueid, venuename, locationquantity, venuesCharge, opentime, "
				+ "closetime FROM tyg_venue WHERE isopen = true";
		return this.getQueryList(sql, null);
	}
	
	/**
	 * 查询指定场馆的信息
	 * @param paramList (venuesid)
	 * @return 指定场馆 venueid， 场馆名 venuename， 场地数 locationqauntity， 场馆费用 charge， 开馆时间 opentime， 
	 * 闭馆时间 closetime
	 */
	public Map<String, Object> getVenuesInfo(List<Object> paramList) {
		String sql = "SELECT venueid, venuename, locationquantity, charge, opentime, "
				+ "closetime FROM tyg_venue WHERE venueid = ?";
		return this.getQueryMap(sql, paramList);
	}
	
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
	 * 查询指定场馆，指定日期对应的预约信息
	 * @param paramList (venueId, queryDay)
	 * @return 预约号 recordid，用户 userid，场地号 locationid，预约起始时间 starttime，结束时间 endtime
	 * 下单时间 maketime
	 */
	public List<Map<String, Object>> getVenuesRecord(List<Object> paramList) {
		String sql = "SELECT recordid, userid, locationid, starttime,"
				+ "endtime, maketime, status, totalmoney, paymentterm, descr FROM tyg_record WHERE venueid = ? AND "
				+ "starttime = ? ORDER BY starttime ASC";
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
	
	
	/**
	 * 查询指定场馆，指定日期和时间的位置信息
	 * @param paramList (venuesId,startTime)
	 * @return Location对象List
	 */
	public List<Map<String,Object>> queryInvalidLocation(List<Object> paramList){
		//生成Location对象数组
		//TODO 
		List<Map<String,Object>> result;
		String sql = "SELECT x.locationid locationid,locationname,type,descr,starttime,endtime "
				+ "FROM tyg_record x,tyg_location y "
				+ "WHERE x.venuesid=? AND x.locationid=y.locationid AND NOT((endtime >= ? AND starttime >= ?) "
				+ "OR (endtime <= ? AND starttime <= ?))";
		result = this.getQueryList(sql, paramList);
		
		System.err.println("queryInvalidLocation: result is " + (result == null?"null":result.toString()));
		//增加一个属性
		if(result != null){
			for(int i = 0 ; i< result.size(); i++){
				result.get(i).put("valid", "false");
			}
		}
		return result;
	}
	
}
