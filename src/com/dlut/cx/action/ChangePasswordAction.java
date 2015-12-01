package com.dlut.cx.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.UserSql;

public class ChangePasswordAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String newpassword;
	
	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	public String execute(){
		String result="error";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String sessionID = session.getId();
		String username = (String) session.getAttribute(sessionID);
		if(username!=null)
		{
			UserSql usersql = new UserSql();
			boolean bl=usersql.setPassword(username, newpassword);
			if(bl)
			{
				result="success";
			}
		}
		return result;
	}

}
