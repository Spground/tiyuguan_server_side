package com.dlut.cx.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class VenueServiceTest {
	VenueService venueService = new VenueService();
	List<Object> paramList = new ArrayList<>();
	@Test
	public void testGetAllOpenVenueInfo() {
		List<Map<String, Object>> result = venueService.getAllOpenVenueInfo();
		assertNotNull(result);
		System.err.println(result);
	}

	@Test
	public void testGetVenuesInfo() {
		paramList.add("1");
		Map<String, Object> result = venueService.getVenueInfoByVenueId(paramList);
		assertNotNull(result);
		System.err.println(result);
	}
	
	@Test
	public void testGetUnavailableLocation() {
		paramList.clear();
		paramList.add("1");
		paramList.add(1450587610);//大于2015-12-19 12:00:00  小于2015-12-19 13:00:00
		paramList.add(1450587610);//大于2015-12-19 13:00:00
		System.err.println(paramList);
		List<Map<String, Object>> result = venueService.getUnavailableLocationList(paramList);
		assertNotNull(result);
		System.err.println(result);
	}
	
	@Test
	public void testGetVenueOrderList() {
		paramList.clear();
		paramList.add("1");
		paramList.add("2015-12-19");//大于2015-12-19 12:00:00  小于2015-12-19 13:00:00
		System.err.println(paramList);
		List<Map<String, Object>> result = venueService.getVenueOrderList(paramList);
		assertNotNull(result);
		System.err.println(result);
	}
}
