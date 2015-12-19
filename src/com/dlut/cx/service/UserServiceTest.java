package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class UserServiceTest {
	UserService userService = new UserService();
	@Test
	public void testGetUserInfo() {
		List<Object> params = new ArrayList<>();
		params.add("1");
		Map<String, Object> result = userService.getUserInfo(params);
		
		assertEquals("1", result.get("userId"));
		assertEquals("admin", result.get("role"));
	}
	
	@Test
	public void testGetUserAllOrderByStarttimeAndEndtime() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("1");
		List<Map<String, Object>> result = userService.getUserAllOrderByStarttimeAndEndtime(paramList);
		assertNotNull(result);
		assertEquals(4, result.size());
		System.err.println(result.get(0));
		
		paramList.clear();
		paramList.add("1");
		paramList.add(1000);
		List<Map<String, Object>> result0 = userService.getUserAllOrderByStarttimeAndEndtime(paramList);
		assertNotNull(result0);
		assertEquals(4, result0.size());
		System.err.println(result0.get(0));
		
		paramList.clear();
		paramList.add("1");
		paramList.add(1000);
		paramList.add(1000);
		List<Map<String, Object>> result00 = userService.getUserAllOrderByStarttimeAndEndtime(paramList);
		assertNotNull(result00);
		assertEquals(2, result00.size());
		System.err.println(result00.get(0));
	}
	
	@Test
	public void testGetUserAllOrderByMakeDate() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("1");
		paramList.add("2015-12-19");
//		paramList.add("2015-12-21");
		List<Map<String, Object>> result = userService.getUserAllOrderByMakeDate(paramList);
		assertNotNull(result);
		assertEquals(3, result.size());
		System.err.println(result.get(0));
	}

		
	@Test
	public void testGetUserAllOrderByStatus() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("1");
//		paramList.add(C.recordStatus.FINISHED);
		List<Map<String, Object>> result = userService.getUserAllOrderByStatus(paramList);
		assertNotNull(result);
		assertEquals(1, result.size());
		System.err.println(result.get(0));
	}
}
