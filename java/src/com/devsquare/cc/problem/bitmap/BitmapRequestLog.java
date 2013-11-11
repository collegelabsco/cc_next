package com.devsquare.cc.problem.bitmap;

import com.devsquare.cc.interfaces.IRequestLog;

public class BitmapRequestLog implements IRequestLog,Comparable<BitmapRequestLog> {
	
	long time;
	int position;
	int length;
	String fileId;
	
	public BitmapRequestLog(long time,int position, int length, String fileId){
		this.time = time;
		this.position = position;
		this.length = length;
		this.fileId =fileId;
	}

	@Override
	public int compareTo(BitmapRequestLog log) {
		return this.position > log.position ?1:0;
	}

	public long getTime() {
		return time;
	}

	public int getPosition() {
		return position;
	}

	public int getLength() {
		return length;
	}

	public String getFileId() {
		return fileId;
	}
	

}
