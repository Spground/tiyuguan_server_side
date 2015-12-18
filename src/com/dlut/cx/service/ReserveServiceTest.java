package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReserveServiceTest {
	ReserveService reserveService = new ReserveService();
	@Test
	public void testMakeReserve() {
		reserveService = new ReserveService();
		List<Object> paramList = new ArrayList<>();
		paramList.add("10000");
		paramList.add("1");
		paramList.add("1");
		paramList.add("1");
		paramList.add(1000);
		paramList.add(1000);
		paramList.add(1000);
		assertEquals(1,  reserveService.makeReserve(paramList));
	}

	@Test
	public void testCancelReserve() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("2");
		assertEquals(1,  reserveService.cancelReserve(paramList));
	}
	
	@Test
	public void testFinishReserve() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("2");
		assertEquals(1,  reserveService.finishReserve(paramList));
	}
	
	@Test
	public void testErrorReserve() {
		List<Object> paramList = new ArrayList<>();
		paramList.add("2");
		assertEquals(1,  reserveService.errorReserve(paramList));
	}
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
