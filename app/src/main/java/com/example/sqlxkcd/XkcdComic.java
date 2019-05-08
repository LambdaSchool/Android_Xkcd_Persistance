package com.example.sqlxkcd;

import org.json.JSONObject;

public class XkcdComic {
	int id;
	boolean isRead;
	String timeStamp;
	
	public XkcdComic(int id, String timeStamp, boolean isRead) {
		this.id = id;
		this.timeStamp = timeStamp;
		this.isRead = isRead;
	}
	
	public XkcdComic(int id, String timeStamp, int isRead) {
		
		this.id = id;
		this.timeStamp = timeStamp;
		
		if (isRead == 1) {
			this.isRead = true;
		} else {
			this.isRead = false;
		}
	}
	
	public XkcdComic(){
		isRead = false;
		timeStamp = String.valueOf(System.currentTimeMillis());
	}
	
	public XkcdComic(int id, String timeStamp){
		this(id,timeStamp,0);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isRead() {
		return isRead;
	}
	
	public void setRead(boolean read) {
		isRead = read;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
