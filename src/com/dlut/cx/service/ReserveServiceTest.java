package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReserveServiceTest {

	@Test
	public void testMakeReserve() {
		ReserveService reserveService = new ReserveService();
		List<Object> paramList = new ArrayList<>();
		paramList.add("1000");
		paramList.add("1");
		paramList.add("1");
		paramList.add("1");
		paramList.add(1000);
		paramList.add(1000);
		paramList.add(1000);
		assertEquals(1,  reserveService.makeReserve(paramList));
	}

//	@Test
//	public void testCancelReserve() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckReserve() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetReserve() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetReserveByDate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUseableReserve() {
//		fail("Not yet implemented");
//	}

}
