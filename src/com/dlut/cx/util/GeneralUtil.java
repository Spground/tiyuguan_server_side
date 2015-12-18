package com.dlut.cx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;

public class GeneralUtil {
	
	/**
	 * SHA256算法实现
	 * @param passwordSHA256
	 * @param time
	 * @return
	 */
	public static String getPwdSHA256(String passwordSHA256,String time) {
		MessageDigest digest;
		String sha256 = passwordSHA256 + time;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(sha256.getBytes("UTF-8"));
			sha256 = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return sha256;
	}
	
	/**
	 * 生成订单号订单号生成规则
	 * @return
	 */
	public static String generateOrderId(int timeStamp) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		long millseconds = (long)timeStamp * 1000;
		Date date = new Date(millseconds);
		String orderId = df.format(date);
		//后三位是随机数
		int random = (int) (Math.random() * 1000);
		String suffix =  String.format("%03d", random);
		return orderId + suffix;
	}
	
	/**
	 * 得到现在的UNIX时间戳
	 * @return
	 */
	public static int getNowTimeStamp() {
		Date date = new Date();
		return (int) (date.getTime() / 1000);
	}
	
}
