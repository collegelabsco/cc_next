package com.devsquare.cc.problem.bitmap.interfaces;

import java.io.IOException;

public interface IReader {
	
	byte[] read(int position, int size) throws IOException;
	
	void close();

	int size();
	
	String getFileName();
	
}
