package com.dlut.cx.service;

import java.util.*;
public class CourtService extends BaseService {
	
	/**
	 * 获得所有体育场馆的资源信息
	 * @return 场馆名 venuesName ，开放时间 openTime ，关闭时间 closeTime ，场地数量 locationNum ， 收费 venuesCharge ，是否开放 isOpen
	 */
	public List<Map<String, String>> getCourtInfo() {
		String sql = "select venuesName, time(openTime) openTime, time(closeTime) closeTime, locationNum, venuesCharge, isOpen from tbl_venues";
		return this.getQueryList(sql, null);
	}
	
	/**
	 * 修改场馆信息
	 * @param paramList (locationNum, veunesCharge, isOpen, openTime, closeTime, venuesName)
	 * @return 操作影响的行数
	 */
	public int updateCourtInfo(List<Object> paramList){
		String sql = "update tbl_venues set locationNum = ?, venuesCharge = ?, isOpen = ?, openTime = ?, closeTime = ? where venuesName = ? "
				+ " and gymId = 1";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 添加场馆信息
	 * @param paramList (sportId, venuesName, locationNum, veunesCharge, isOpen, openTime, closeTime)
	 * @return 操作影响的行数
	 */
	public int insertCourtInfo(List<Object> paramList){
		String sql = "insert into tbl_venues(gymId, sportId, venuesName,"
				+ " locationNum, venuesCharge, isOpen, openTime, closeTime) values(1, ?, ?, ?, ?, ?, ?, ?)";
		return this.execute(sql, paramList);
	}
	
	/**
	 * 删除场馆
	 * @param paramList (venuesName)
	 * @return 操作影响的行数
	 */
	public int deleteCourtInfo(List<Object> paramList) {
		String sql = "delete from tbl_venues where gymId = 1 and venuesName = ?";
		return this.execute(sql, paramList);
	}
}
