package com.dlut.cx.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dlut.cx.service.ReserveService;
import com.dlut.cx.util.C;

public class ReserveAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpSession session = ServletActionContext.getRequest().getSession();
	private String userId = (String)session.getAttribute(session.getId());
	
	private String startTime;
	private String endTime;
	
	private int venuesId;
	private int location;
	
	private String recordId;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public void setVenuesId(int venuesId) {
		this.venuesId = venuesId;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	/**
	 * 下订单
	 * @return
	 */
	public String makeReserve() {
		setResultMap(C.code.RESERVE, C.message.FAIL);
		
		paramList.clear();
		paramList.add(venuesId);
		paramList.add(location);
		paramList.add(startTime);
		
		ReserveService reserve=new ReserveService();
		if(reserve.checkReserve(paramList) == 0){
			
			paramList.clear();
			String orderId =  generateRecordId();
			paramList.add(orderId);
			paramList.add(venuesId);
			paramList.add(userId);
			paramList.add(location);
			paramList.add(startTime);
			paramList.add(endTime);
			
			if(reserve.makeReserve(paramList) > 0){
				Map<String,String> recordObj = new HashMap<>();
				recordObj.put("venuesId", venuesId + "");
				recordObj.put("userId", userId);
				recordObj.put("location", location + "");
				recordObj.put("startTime", startTime);
				recordObj.put("endTime", endTime);
				recordObj.put("recordId", orderId);
				setResultMap(C.code.RESERVE, C.message.SUCCESS,C.name.RESERVE_MAPNAME, recordObj);
			}
				
		}
		
		return SUCCESS;
	}
	
	public String cancelReserve() {
		setResultMap(C.code.CANCEL, C.message.FAIL);
		
		paramList.clear();
		paramList.add(recordId);
		
		ReserveService reserve = new ReserveService();
		if(reserve.cancelReserve(paramList) > 0)
			setResultMap(C.code.CANCEL, C.message.SUCCESS);
		
		return SUCCESS;
	}
	
	public String getReserve() {
		if(userId == null) {
			setResultMap(C.code.RECORD, C.message.FAIL);
			return SUCCESS;
		}
		
		paramList.clear();
		paramList.add(userId);
		paramList.add(startTime);
		paramList.add(endTime);
		
		ReserveService reserve = new ReserveService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, reserve.getReserve(paramList));
		return SUCCESS;
	}
	
	public String getUseableReserve() {
		if(userId == null) {
			setResultMap(C.code.RECORD, C.message.FAIL);
			return SUCCESS;
		}
		
		paramList.clear();
		paramList.add(userId);
		
		ReserveService reserve = new ReserveService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, reserve.getUseableReserve(paramList));
		return SUCCESS;
	}
	
	public String getHistoryReserve() {
		if(userId == null) {
			setResultMap(C.code.RECORD, C.message.FAIL);
			return SUCCESS;
		}
		
		paramList.clear();
		paramList.add(userId);
		
		ReserveService reserve = new ReserveService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, reserve.getHistoryReserve(paramList));
		return SUCCESS;
	}
	
	/**生成订单号**/
	private String generateRecordId() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String recordId = null;
		try {
			recordId = df.format(sdf.parse(startTime)) + venuesId + location;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			recordId = startTime.substring(0,startTime.length() - 2) + venuesId + location;
			e.printStackTrace();
		}
		return recordId;
	}
}
