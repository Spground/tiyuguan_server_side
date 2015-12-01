package com.dlut.cx.action;

import com.dlut.cx.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserSql;

import java.util.*;

public class UserAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private String fbk_content;
	
	private Map<String,String> resultMap = new HashMap<String,String>();
	private List<Object> paramList = new ArrayList<>();
	
	private UserService userService = new UserService();
	
	public void setUserid(String userid){
		this.userid=userid;
	}
	
	public void setFbk_content(String fbk_content){
		this.fbk_content = fbk_content;
	}
	
	public Map<String,String> getResultMap(){
		return resultMap;
	}
	
	public String username(){
		resultMap.clear();
		UserSql usersql = new UserSql();
		resultMap.put("name",usersql.getusernameByid(userid));
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
		paramList.add(this.fbk_content);
		Date now = new Date();
		paramList.add(String.valueOf(now.getTime()));
		
		if(userService.setUserFeedbackContent(paramList) > 0){
			resultMap.put("code", "success");
		}
		return SUCCESS;
	}

}
