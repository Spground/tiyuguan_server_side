package com.dlut.cx.model;

import java.sql.Timestamp;

public class Venues {
	private int id;
	
	private int gym_id;
	
	private int sport_id;
	
	private String v_name;
	
	private int num;
	
	private float charge;
	
	private int is_open;
	
	private Timestamp open_time;
	
	private Timestamp close_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGym_id() {
		return gym_id;
	}

	public void setGym_id(int gym_id) {
		this.gym_id = gym_id;
	}

	public int getSport_id() {
		return sport_id;
	}

	public void setSport_id(int sport_id) {
		this.sport_id = sport_id;
	}

	public String getV_name() {
		return v_name;
	}

	public void setV_name(String v_name) {
		this.v_name = v_name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getCharge() {
		return charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public int getIs_open() {
		return is_open;
	}

	public void setIs_open(int is_open) {
		this.is_open = is_open;
	}

	public Timestamp getOpen_time() {
		return open_time;
	}

	public void setOpen_time(Timestamp open_time) {
		this.open_time = open_time;
	}

	public Timestamp getClose_time() {
		return close_time;
	}

	public void setClose_time(Timestamp close_time) {
		this.close_time = close_time;
	}
	
	
}
