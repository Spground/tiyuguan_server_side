package com.dlut.cx.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dlut.cx.service.ReserveService;
import com.dlut.cx.util.C;
import com.dlut.cx.util.GeneralUtil;

public class ReserveAction extends BaseAction {

	/**
	 * 用户预约管理模块
	 */
	private static final long serialVersionUID = 1L;

	private HttpSession session = ServletActionContext.getRequest().getSession();
	private String userId = (String) session.getAttribute(session.getId());

	private ReserveService reserveService = new ReserveService();
	private int startTime;
	private int endTime;

	private String venueId;
	private String locationId;

	private String orderId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 下订单
	 * 
	 * @return
	 */
	public String makeReserve() {
		setResultMap(C.code.RESERVE, C.message.FAIL);
		
		paramList.clear();
		int makeTime = GeneralUtil.getNowTimeStamp();
		String orderId = GeneralUtil.generateOrderId(makeTime);
		paramList.add(orderId);
		paramList.add(venueId);
		paramList.add(userId);
		paramList.add(locationId);
		paramList.add(startTime);
		paramList.add(endTime);
		paramList.add(makeTime);
		
		if (reserveService.makeReserve(paramList) > 0) {
			Map<String, Object> recordObj = new HashMap<>();
			recordObj.put("venueId", venueId);
			recordObj.put("userId", userId);
			recordObj.put("locationId", locationId);
			recordObj.put("startTime", startTime);
			recordObj.put("endTime", endTime);
			recordObj.put("orderId", orderId);
			setResultMap(C.code.RESERVE, C.message.SUCCESS, C.name.RESERVE_MAPNAME, recordObj);
		}

		return SUCCESS;
	}

	/**
	 * 取消用户的预约
	 * 
	 * @return
	 */
	public String cancelReserve() {
		setResultMap(C.code.CANCEL, C.message.FAIL);

		paramList.clear();
		paramList.add(orderId);

		if (reserveService.cancelReserve(paramList) > 0)
			setResultMap(C.code.CANCEL, C.message.SUCCESS);

		return SUCCESS;
	}

}
