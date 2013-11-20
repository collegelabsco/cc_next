package com.devsquare.cc.interfaces;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public interface Callback {
	
	void process(ZipOutputStream zos) throws IOException;
	
	OutputStream getOutputStream() throws IOException;

	boolean isFileAvailable();

}
