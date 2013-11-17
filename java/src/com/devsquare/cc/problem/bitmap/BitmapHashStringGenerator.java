package com.devsquare.cc.problem.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.devsquare.cc.log.Log;

public class BitmapHashStringGenerator {
	
    private DigestInputStream dis;
    private MessageDigest md;
    String fname = null;

	public BitmapHashStringGenerator(File file, String hashAlgo) throws FileNotFoundException, NoSuchAlgorithmException {
			md = MessageDigest.getInstance(hashAlgo); // initializing
			InputStream fis = new FileInputStream(file);
			dis = new DigestInputStream(fis, md); // getting stream
			fname = file.getName();
	}

	public String generateHash() throws IOException {
		byte[] buf = new byte[4096]; // setting buffer
		while (dis.read() != -1)
			; // reading and updating message digest
		byte[] output = md.digest(); // getting hash data in bytes
		BigInteger bi = new BigInteger(1, output);
		String hashText = bi.toString(16); // getting hex value of hash
		dis.close(); // closing stream
		Log.info("hash text for file : "+fname+" = "+hashText);
		return hashText;
	}
}
