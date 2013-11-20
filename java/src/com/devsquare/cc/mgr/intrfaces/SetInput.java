package com.devsquare.cc.mgr.intrfaces;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

public interface SetInput {
	
	public void process(ZipOutputStream zos) throws IOException;

	public boolean isInputAvailable();

}
