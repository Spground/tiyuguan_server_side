package com.dlut.cx.action;

import java.util.*;

import com.dlut.cx.service.*;
import com.dlut.cx.util.C;

public class OrderAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sportId;
	
	public void setSportId(int sportId) {
		this.sportId = sportId;
	}

	public String getVenuesRestInfo() {	
		OrderService record = new OrderService();
		VenueService venues = new VenueService();
		
		List<Map<String, Object>> data = new ArrayList<>();
		List<Map<String, Object>> venuesList;
		
		if(sportId == 0) {
			venuesList = venues.getVenuesInfo();
		}
		else {
			paramList.clear();
			paramList.add(sportId);
			
			venuesList = venues.getVenuesInfoBySport(paramList);
		}
		
		for(Map<String, Object> venuesInfo : venuesList) {
			paramList.clear();
			paramList.add(venuesInfo.get("venuesId"));
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.putAll(venuesInfo);
			dataMap.put(C.name.RESERVE_MAPNAME, record.getReserveRecordCount(paramList));
			
			data.add(dataMap);
		}
		
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUE_MAPNAME, data);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEndtimeInFutureOrder() {
		OrderService record = new OrderService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, record.getEndtimeInFutureOrder());
		return SUCCESS;
	}


}
