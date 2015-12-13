package com.dlut.cx.action;

import com.dlut.cx.service.OrderService;
import com.dlut.cx.service.UserService;
import com.dlut.cx.util.C;

import dao.UserSql;

import java.util.*;

public class UserAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String fbkcontent;
	
	private List<Object> paramList = new ArrayList<>();
	
	private UserService userService = new UserService();
	
	public void setUserid(String userid){
		this.userId=userid;
	}
	
	public void setFbk_content(String fbk_content){
		this.fbkcontent = fbk_content;
	}
	
	public Map<String,Object> getResultMap(){
		return resultMap;
	}
	
	public String username(){
		resultMap.clear();
		UserSql usersql = new UserSql();
		resultMap.put("name",usersql.getusernameByid(userId));
		usersql.destory();
		return SUCCESS;
	}
	
	/**
	 * 用户提交反馈
	 * @return 
	 */
	public String feedback(){
		resultMap.clear();
		resultMap.put("code","faild");
		
		paramList.clear();
		paramList.add(this.fbkcontent);
		Date now = new Date();
		paramList.add(now.getTime());
		
		if(userService.setUserFeedbackContent(paramList) > 0){
			resultMap.put("code", "success");
		}
		return SUCCESS;
	}
	
	/**
	 * 得到指定用户的历史订单
	 * @return
	 */
	public String getUserHistoryOrder() {
		if(userId == null) {
			setResultMap(C.code.RECORD, C.message.FAIL);
			return SUCCESS;
		}
		
		paramList.clear();
		paramList.add(userId);
		
		OrderService orderService = new OrderService();
		setResultMap(C.code.RECORD, C.message.SUCCESS, C.name.RESERVE_MAPNAME, orderService.getUserHistoryOrder(paramList));
		return SUCCESS;
	}

}
