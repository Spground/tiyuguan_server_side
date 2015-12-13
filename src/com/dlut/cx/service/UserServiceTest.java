package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class UserServiceTest {

	@Test
	public void testGetUserInfo() {
		List<Object> params = new ArrayList<>();
		params.add("1");
		UserService userService = new UserService();
		Map<String, Object> result = userService.getUserInfo(params);
		
		assertEquals("1", result.get("userId"));
		assertEquals("admin", result.get("role"));
	}

}
