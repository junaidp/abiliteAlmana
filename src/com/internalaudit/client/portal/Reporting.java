package com.internalaudit.client.portal;

import java.io.Serializable;
import java.util.Date;

public class Reporting implements Serializable {
	
	private int id;
	private String name;
	private String status;
	private Date date = new Date();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
