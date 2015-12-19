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
	
	/**
	 * 每一次请求返回的json对象
	 */
	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	/**
	 * 
	 * @param code     	响应码 对应的代表请求什么样的数据
	 * @param message	关于本次请求的信息，例如失败、成功还是其他原因
	 * @param mapName   返回数据model的名字
	 * @param data		返回的数据实体
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
