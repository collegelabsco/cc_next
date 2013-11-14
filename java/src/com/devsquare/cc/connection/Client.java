package com.devsquare.cc.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class Client {
	
	public static void main(String args[]) throws IOException, InterruptedException{
		Socket clientSocket = new Socket("localhost", 8081);
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Sending data to server..");
		output.writeBytes("sessionkey=123&level=1&type=get\n");
		output.flush();
		Thread.sleep(3000);
		int len = input.available();
		byte[] b = new byte[len];
		input.readFully(b);
		String response = new String(b);
		System.out.println("Client:"+response);
		
		URL url = new URL("http://localhost:8082");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write("dictionary=file\n".getBytes());
		os.flush();
		Thread.sleep(3000);
////		//clientSocket.close();
//		output.writeBytes("demo..sample...You");
//		output.flush();
//		Thread.sleep(300);
//		len = input.available();
//		 b = new byte[len];
//		input.readFully(b);
//		System.out.println("Client:"+new String(b));
		
		Thread.sleep(2000);
		//clientSocket.close();
	}

}
