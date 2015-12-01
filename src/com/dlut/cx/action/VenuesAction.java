package com.dlut.cx.action;

import java.util.*;

import com.dlut.cx.service.VenuesService;
import com.dlut.cx.util.C;

public class VenuesAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String venuesId;
	private String queryDay;
	private String queryTime;
	
	private String queryStartTime;
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

	private String queryEndTime;
	
	public void setVenuesId(String venuesId) {
		this.venuesId = venuesId;
	}
	
	public void setQueryDay(String queryDay) {
		this.queryDay = queryDay;
	}
	
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	
	public String getVenuesInfo() {
		VenuesService venues = new VenuesService();
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUES_MAPNAME, venues.getVenuesInfo());
		return SUCCESS;
	}
	
	public String getVenuesRecordList() {
		VenuesService venues = new VenuesService();
		
		paramList.clear();
		paramList.add(venuesId);
		
		Map<String, Object> data =  new HashMap<String, Object>();
		data.putAll(venues.getVenuesInfo(paramList));
		
		paramList.add(queryDay);
		
		data.put(C.name.RESERVE_MAPNAME, venues.getVenuesRecord(paramList));
		
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUES_MAPNAME, data);
		return SUCCESS;
	}
	
	public String getRecordByTime() {
		VenuesService venues = new VenuesService();
		
		paramList.clear();
		paramList.add(venuesId);
		paramList.add(queryDay);
		paramList.add(queryTime);
		
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.RESERVE_MAPNAME, venues.getVenuesRecordByTime(paramList));
		return SUCCESS;
	}
	
	/**
	 * 查询场馆不可用的位置信息
	 * @return
	 */
	public String queryInvalidLocation(){
		VenuesService venues = new VenuesService();
		paramList.clear();
		paramList.add(venuesId);
		paramList.add(queryDay + queryStartTime);
		paramList.add(queryDay + queryEndTime);
		paramList.add(queryDay + queryStartTime);
		paramList.add(queryDay + queryEndTime);
		System.err.println("====TAG====" + paramList.toString());
		setResultMap(C.code.LOCATION,C.message.SUCCESS,C.name.LOCATION_MAPNAME,venues.queryInvalidLocation(paramList));
		return SUCCESS;
	}
}
