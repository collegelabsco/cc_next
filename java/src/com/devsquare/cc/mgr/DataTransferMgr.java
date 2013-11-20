package com.devsquare.cc.mgr;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.devsquare.cc.interfaces.Callback;
import com.devsquare.cc.mgr.intrfaces.SetInput;

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
	
	public static DataTransferMgr get(){
		return new DataTransferMgr();
	}
	
	ZipOutputStream zos = null;
	SetInput si = null;
	
	public DataTransferMgr getZipStream(OutputStream os){
		 zos = new ZipOutputStream(os);
		return this;
	}
	
	public DataTransferMgr setInputStream(SetInput si){
		this.si = si;
		return this;
	}
	
	public void process() throws IOException{
		int count=1;
		while(si.isInputAvailable()){
			ZipEntry entry = new ZipEntry("file-"+count+++".txt");
			zos.putNextEntry(entry);
			si.process(zos);
			zos.flush();
		}
		
		zos.finish();
	}
	

}
