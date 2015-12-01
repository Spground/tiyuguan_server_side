package com.dlut.cx.action;

import java.util.*;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 	@Note define resultMap(code, message, data) as return value
 */
public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Object> paramList = new ArrayList<Object>();
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	/**
	 * 
	 * @param code     	��Ϣ����
	 * @param message	������������Ϣ�������ݿ�ִ�в�ѯ�����Ľ��
	 * @param mapName   data��װ��������
	 * @param data		data����
	 */
	protected void setResultMap(int code, Map<String, String> message, String mapName, Object data) {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put(mapName, data);
		
		resultMap.clear();
		resultMap.put("code", code);
		resultMap.put("message", message);
		resultMap.put("data", mapData);
	}
	
	/**
	 * 
	 * @param code		��Ϣ����
	 * @param message	������������Ϣ�������ݿ�ִ������ɾ���Ĳ����Ľ��
	 */
	protected void setResultMap(int code, Map<String, String> message) {
		resultMap.clear();
		resultMap.put("code", code);
		resultMap.put("message", message);
		resultMap.put("data", null);
	}
	
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

}
