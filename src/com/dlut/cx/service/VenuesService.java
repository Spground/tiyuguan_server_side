package com.dlut.cx.service;

import java.util.*;

public class VenuesService extends BaseService {
	/**
	 * 查询所有场馆的信息列表
	 * @return 场馆 venuseId， 场馆名 venuesName，位置数 locationNum， 场馆费用 venuesCharge， 开馆时间 openTime， 
	 * 闭馆时间 closeTime
	 */
	public List<Map<String, String>> getVenuesInfo() {
		String sql = "select venuesId, venuesName, locationNum, venuesCharge, time(openTime) openTime, "
				+ "time(closeTime) closeTime from tbl_venues where isOpen = true";
		return this.getQueryList(sql, null);
	}
	
	/**
	 * 查询指定场馆的信息
	 * @param paramList (venuesId)
	 * @return 指定场馆 venuseId， 场馆名 venuesName， 场地数 locationNum， 场馆费用 venuesCharge， 开馆时间 openTime， 
	 * 闭馆时间 closeTime
	 */
	public Map<String, String> getVenuesInfo(List<Object> paramList) {
		String sql = "select venuesId, venuesName, locationNum, venuesCharge, time(openTime) openTime, "
				+ "time(closeTime) closeTime from tbl_venues where venuesId = ?";
		return this.getQueryMap(sql, paramList);
	}
	/**
	 * 查询包含指定运动的场馆的信息
	 * @param paramList (sportId)
	 * @return 指定场馆 venuseId， 场馆名 venuesName， 场地数 locationNum， 场馆费用 venuesCharge， 开馆时间 openTime， 
	 * 闭馆时间 closeTime
	 */
	public List<Map<String, String>> getVenuesInfoBySport(List<Object> paramList) {
		String sql = "select venuesId, venuesName, locationNum, venuesCharge, time(openTime) openTime, "
				+ "time(closeTime) closeTime from tbl_venues where sportId = ?";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 查询指定场馆，指定日期对应的预约信息
	 * @param paramList (venuesId, queryDay)
	 * @return 预约号 recordId，用户 userId，场地号 location，预约起始时间 startTime，结束时间 endTime
	 */
	public List<Map<String, String>> getVenuesRecord(List<Object> paramList) {
		String sql = "select recordId, userId, location, time(startTime) startTime,"
				+ "time(endTime) endTime from tbl_record where venuesId = ? and "
				+ "date(startTime) = ? order by startTime asc";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 查询指定场馆，指定日期，指定时间段对应的预约信息
	 * @param paramList (venuesId, queryDay, queryTime)
	 * @return 预约号 recordId，用户 userId，场地号 location，预约起始时间 startTime，结束时间 endTime
	 */
	public List<Map<String, String>> getVenuesRecordByTime(List<Object> paramList) {
		String sql = "select recordId, userId, location, time(startTime) startTime,"
				+ "time(endTime) endTime from tbl_record where venuesId = ? and "
				+ "date(startTime) = ? and time(startTime) = ?";
		return this.getQueryList(sql, paramList);
	}
	
	/**
	 * 查询指定场馆，指定日期和时间的位置信息
	 * @param paramList (venuesId,startTime)
	 * @return Location对象List
	 */
	public List<Map<String,String>> queryInvalidLocation(List<Object> paramList){
		//生成Location对象数组
		List<Map<String,String>> result;
		String sql = "SELECT location,startTime,endTime FROM tbl_record WHERE `venuesId`=? AND NOT((endTime >= ? AND startTime >= ?) OR (endTime <= ? AND startTime <= ?))";
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
