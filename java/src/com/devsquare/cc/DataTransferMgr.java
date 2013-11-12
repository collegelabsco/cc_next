package com.devsquare.cc;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.devsquare.cc.interfaces.Callback;

public class DataTransferMgr {
	
	public void prepareZipFromStream(Callback cb) throws IOException{
		
		ZipOutputStream zos = new ZipOutputStream(cb.getOutputStream());
		
		int count=1;
		while(cb.isFileAvailable()){
			ZipEntry entry = new ZipEntry("file-"+count+++".txt");
			zos.putNextEntry(entry);
			cb.process(zos);
			zos.flush();
		}
		
		zos.finish();
		
	}
	
	

}
