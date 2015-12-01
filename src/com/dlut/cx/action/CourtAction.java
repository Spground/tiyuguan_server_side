package com.dlut.cx.action;

import java.text.DateFormat;
import java.util.Date;

import com.dlut.cx.service.CourtService;
import com.dlut.cx.util.C;

public class CourtAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DateFormat df=DateFormat.getDateInstance();
	
	private int sportId;
	private String venuesName;
	private int locationNum;
	private float venuesCharge;
	private String openTime;
	private String closeTime;
	private boolean isOpen;
	
	public void setSportId(int sportId) {
		this.sportId = sportId;
	}
	
	public void setVenuesName(String venuesName) {
		this.venuesName = venuesName;
	}
	
	public void setLocationNum(int locationNum) {
		this.locationNum = locationNum;
	}
	
	public void setVenuesCharge(float venuesCharge) {
		this.venuesCharge = venuesCharge;
	}
	
	public void setOpenTime(String openTime) {
		this.openTime = df.format(new Date()) + " "+ openTime;
	}
	
	public void setCloseTime(String closeTime) {
		this.closeTime = df.format(new Date()) + " "+ closeTime;
	}
	
	public void setIsOpen(String isOpen){
		this.isOpen = (isOpen.equals("开放"));
	}
	
	public String getAllCourtInfo() {
		CourtService court = new CourtService();
		setResultMap(C.code.COURT, C.message.SUCCESS, C.name.VENUES_MAPNAME, court.getCourtInfo());
		return SUCCESS;
	}

	public String editCourtInfo() {
		setResultMap(C.code.COURT, C.message.FAIL);
		
		paramList.clear();
		paramList.add(locationNum);
		paramList.add(venuesCharge);
		paramList.add(isOpen);
		paramList.add(openTime);
		paramList.add(closeTime);
		paramList.add(venuesName);
		
		CourtService court = new CourtService();
		if(court.updateCourtInfo(paramList) > 0)
			setResultMap(C.code.COURT, C.message.SUCCESS);
		
		return SUCCESS;
	}
	
	public String addCourtInfo() {
		setResultMap(C.code.COURT, C.message.FAIL);
		
		paramList.clear();
		paramList.add(sportId);
		paramList.add(venuesName);
		paramList.add(locationNum);
		paramList.add(venuesCharge);
		paramList.add(isOpen);
		paramList.add(openTime);
		paramList.add(closeTime);
		
		CourtService court = new CourtService();
		if(court.insertCourtInfo(paramList) > 0)
			setResultMap(C.code.COURT, C.message.SUCCESS);
		return SUCCESS;
	}
	
	public String deleteCourtInfo() {
		setResultMap(C.code.COURT, C.message.FAIL);
		paramList.clear();
		paramList.add(venuesName);
		
		CourtService court = new CourtService();
		if(court.deleteCourtInfo(paramList) > 0)
			setResultMap(C.code.COURT, C.message.SUCCESS);
		return SUCCESS;
	}
}
