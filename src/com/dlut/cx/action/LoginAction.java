package com.dlut.cx.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dlut.cx.service.UserService;
import com.dlut.cx.util.C;
import com.dlut.cx.util.GeneralUtil;

/**
 * 
 * @author asus
 * last modified at 2015/12/04 by wujie
 */
public class LoginAction extends BaseAction implements SessionAware, ServletRequestAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String password;
	private String needCookie;
	private String nowTime;
	
	private final String userNameCookie="SESSION_LOGIN_USERNAME";
	private HttpServletResponse response;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setNeedCookie(String needCookie) {
		this.needCookie = needCookie;
	}
	
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
	public String execute() {
		String result="error";
		setResultMap(C.code.LOGIN, C.message.FAIL);
		
		if(password != null && password != "") {
			paramList.clear();
			paramList.add(userId);
			
			UserService userService = new UserService();
			String userPassword = userService.getPassword(paramList);
			String sha = GeneralUtil.getPwdSHA256(userPassword, nowTime);
			if(password.equalsIgnoreCase(sha)) {
				result="success";
				//查询成功以后默认返回用户的基本信息
				setResultMap(C.code.LOGIN, C.message.SUCCESS, C.name.LOGIN_MAPNAME, 
						userService.getUserInfo(paramList));
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String sessionID = request.getSession().getId();
			    request.getSession().setAttribute(sessionID, userId);
			 
			    String role = userService.getRole(paramList);
	
			    request.getSession().setAttribute(userId, role);
			    
				if(null != needCookie && needCookie.equalsIgnoreCase("remember-me")) {
					Cookie cookie = new Cookie(userNameCookie,sessionID);
					cookie.setMaxAge(60 * 60 * 24 * 14);
					
					paramList.clear();
					paramList.add(cookie);
					paramList.add(userId);
					
					userService.setCookie(paramList);
					
					response.addCookie(cookie);
				}
			}
		}
		
		return result;
	}
	

	
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setSession(java.util.Map<String, Object> arg0) {
		// TODO Auto-generated method stub
			
	}

}
