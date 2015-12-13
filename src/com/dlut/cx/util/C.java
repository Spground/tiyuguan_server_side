package com.dlut.cx.util;

import java.util.*;

public class C {
	public static final class code {
        public static final int LOGIN = 1000;
        public static final int RESERVE = 1001;
        public static final int CANCEL = 1002;
        public static final int RECORD = 1003;
        public static final int SPORT = 1004;
        public static final int VENUES = 1005;
        public static final int NEWS = 1006;
        public static final int COURT = 1007;
        public static final int LOCATION = 1008;
    }
	
	public static final class message {
		public static final Map<String,String> SUCCESS = new HashMap<String,String>();
		public static final Map<String,String> FAIL = new HashMap<String,String>();
		
		static {
			SUCCESS.put("result", "success");
			FAIL.put("result", "fail");
		}
	}
	
	public static final class name {
		public static final String LOGIN_MAPNAME = "User";
		public static final String RESERVE_MAPNAME = "Record"; 
		public static final String VENUE_MAPNAME = "Venue";
		public static final String SPORT_MAPNAME = "Sport";
		public static final String NEWS_MAPNAME = "News";
		public static final String LOCATION_MAPNAME = "Location";
	}
	
	public static final class recordStatus {
		
		/**
		 * 用户成功下单，预定的日期还没有来临
		 */
		public static final String NEW = "new";
		
		/**
		 * 用户下单成功以后，预定的日期还没有来临，但是用户取消订单了，且是在合理的时间段类取消的
		 * 不算违约,先前状态:new
		 */
		public static final String CANCELED = "canceled";
		
		/**
		 * 用户没有在合理的日期里面取消订单或者是由管理员在发现到时间了这个人没有到来而手动设置的
		 * 先前状态:new
		 */
		public static final String BREACH = "breach";
		
		/**
		 * 用户成功预订，并且依照指定时间使用了场馆，由管理员在现场确认的时候手动设置
		 * 先前状态:new
		 */
		public static final String FINISHED = "finished";
		
		/**
		 * 订单出现了错误，出现了不知道的错误，该订单应该无效
		 *
		 */
		public static final String ERROR = "error";
	}
}
