package com.dlut.cx.action;

import java.util.*;

import com.dlut.cx.service.*;
import com.dlut.cx.util.C;

public class RecordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sportId;
	
	public void setSportId(int sportId) {
		this.sportId = sportId;
	}

	public String getVenuesRestInfo() {	
		RecordService record = new RecordService();
		VenuesService venues = new VenuesService();
		
		List<Map<String, Object>> data = new ArrayList<>();
		List<Map<String, String>> venuesList;
		
		if(sportId == 0) {
			venuesList = venues.getVenuesInfo();
		}
		else {
			paramList.clear();
			paramList.add(sportId);
			
			venuesList = venues.getVenuesInfoBySport(paramList);
		}
		
		for(Map<String, String> venuesInfo : venuesList) {
			paramList.clear();
			paramList.add(venuesInfo.get("venuesId"));
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.putAll(venuesInfo);
			dataMap.put(C.name.RESERVE_MAPNAME, record.getReserveRecordCount(paramList));
			
			data.add(dataMap);
		}
		
		setResultMap(C.code.VENUES, C.message.SUCCESS, C.name.VENUES_MAPNAME, data);
		return SUCCESS;
	}
	
	public String getReserveRecord() {
		RecordService record = new RecordService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, record.getReserveRecord());
		return SUCCESS;
	}


}
