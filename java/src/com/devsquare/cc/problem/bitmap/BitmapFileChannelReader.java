package com.devsquare.cc.problem.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.NoSuchAlgorithmException;

import com.devsquare.cc.problem.bitmap.interfaces.IReader;

public class BitmapFileChannelReader implements IReader {
	
	FileInputStream fis = null;
	FileChannel fChannel = null;
	int size = 0;
	String filename = null;
	
	String fileHash = null;
	

	public BitmapFileChannelReader(File file) throws IOException, NoSuchAlgorithmException{
		fis = new FileInputStream(file);
		fileHash = new BitmapHashStringGenerator(file, "MD5").generateHash();
		this.size = fis.available();
		this.fChannel = fis.getChannel();
		this.filename = file.getName();
	}
	
	

	@Override
	public byte[] read(int position, int length) throws IOException {
		MappedByteBuffer byteBuffer = this.fChannel.map(MapMode.READ_ONLY, position, length);
		byteBuffer.load();
		byte b[] = new byte[length];
		byteBuffer.get(b);
		return b;
	}

	/**
	 * For test it's written
	 * @param dest
	 * @param position
	 * @param length
	 * @throws IOException
	 */
	public void read(byte[] dest,int position, int length) throws IOException {
		MappedByteBuffer byteBuffer = this.fChannel.map(MapMode.READ_ONLY, position, length);
		byteBuffer.load();
		byteBuffer.get(dest,position,length);
		byteBuffer.clear();
		byteBuffer = null;
	}

	@Override
	public void close() {
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				//Nothing can be done.
			}
		}
	}


	@Override
	public int size() {
		return this.size;
	}



	@Override
	public String getFileName() {
		return this.filename;
	}

	public String getHash(){
		return this.fileHash;
	}
}
