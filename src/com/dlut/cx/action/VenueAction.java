package com.dlut.cx.action;

import java.util.*;

import com.dlut.cx.service.VenueService;
import com.dlut.cx.util.C;

public class VenueAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	VenueService venueService = new VenueService();
	
	private String venueId;
	private String queryStartTime;
	private String queryEndTime;
	
	private String queryDay;
	public String getQueryDay() {
		return queryDay;
	}

	public void setQueryDay(String queryDay) {
		this.queryDay = queryDay;
	}

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public void setVenuesId(String venueId) {
		this.venueId = venueId;
	}
	
	
	/**
	 * 得到所有场馆的详细信息 包括 开闭馆时间 收费标准 位置数量
	 * @return
	 */
	public String getVenueInfo() {
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUE_MAPNAME, venueService.getAllOpenVenueInfo());
		return SUCCESS;
	}
	
	/**
	 * 查询指定的场馆的的指定日期(某一天内)的订单集合
	 * @param venueId queryDay 格式yyyyMMdd
	 * @return
	 */
	public String queryVenueOrderListInSomeday() {
		paramList.clear();
		paramList.add(venueId);
		paramList.add(queryDay);
		Map<String, Object> data =  new HashMap<String, Object>();
		data.put(C.name.RESERVE_MAPNAME, venueService.getVenueOrderList(paramList));
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUE_MAPNAME, data);
		return SUCCESS;
	}
	
//	public String getRecordByTime() {
//		VenueService venues = new VenueService();
//		
//		paramList.clear();
//		paramList.add(venueId);
//		paramList.add(queryDay);
//		paramList.add("");
//		
//		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.RESERVE_MAPNAME, venues.getVenuesRecordByStartEndTime(paramList));
//		return SUCCESS;
//	}
	
	/**
	 * 查询指定时间段内指定场馆不可用的位置集合
	 * @return
	 */
	public String queryUnavailableLocationList(){
		paramList.clear();
		paramList.add(venueId);
		paramList.add(queryStartTime);
		paramList.add(queryEndTime);
		System.err.println("====TAG====" + paramList.toString());
		setResultMap(C.code.LOCATION,C.message.SUCCESS,C.name.LOCATION_MAPNAME,venueService.getUnavailableLocationList(paramList));
		return SUCCESS;
	}
}
