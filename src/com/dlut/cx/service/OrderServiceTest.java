package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class OrderServiceTest {

	@Test
	public void testGetUserHistoryOrder() {
		OrderService orderService = new OrderService();
		List<Object> paramList = new ArrayList<>();
		paramList.add("1");
		List<Map<String, Object>> result = orderService.getUserHistoryOrder(paramList);
		assertNotNull(result);
		assertEquals(1450001398, (int)result.get(0).get("makeTime"));
		assertEquals("1", result.get(0).get("orderId"));
		assertEquals("1", result.get(0).get("locationId"));
		assertEquals(1450001398, (int)result.get(0).get("startTime"));
		assertEquals(1450001398, (int)result.get(0).get("endTime"));
		assertEquals(20.0f, (float)result.get(0).get("totalMoney"), 0);
		assertEquals("finished", result.get(0).get("status"));
	}
	
	@Test
	public void testGetReserveOrder() {
		OrderService orderService = new OrderService();
		List<Map<String, Object>> result = orderService.getEndtimeInFutureOrder();
		assertNotNull(result);
		assertEquals(1450011398, (int)result.get(0).get("startTime"));
	 //....
	}
	
	
}
