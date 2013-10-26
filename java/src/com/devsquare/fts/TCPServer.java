package com.devsquare.fts;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
	   
		try {			
			ServerSocket serverSocket = new ServerSocket(443, 36);
            //ServerSocket serverSocket = new ServerSocket(80, 36);

            while (true) {
               
                Socket serviceSocket= serverSocket.accept();
				new Connectivity(serviceSocket);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}         

	}

}
