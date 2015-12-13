package com.dlut.cx.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeneralUtilTest {

	@Test
	public void testGenerateOrderId() {
		String orderId = GeneralUtil.generateOrderId(1450015618);
		System.out.println(orderId);
	}
	
	@Test
	public void testGetNowTimeStamp() {
		System.out.println(GeneralUtil.getNowTimeStamp());
		System.out.println(String.format("%03d", 1));
	}

}
