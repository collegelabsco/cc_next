package com.devsquare.cc.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.devsquare.cc.log.Log;


public class Server {
	
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws IOException {
			//ServerSocket serverSocket = new ServerSocket(443, 36);
			Log.info("Starting server..");
			final ServerSocket serverSocket = new ServerSocket(8081);
			
            Runtime.getRuntime().addShutdownHook(new Thread(){
            	public void run(){
            		try {
						serverSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            	
            });
            
            while (true) {
            	Log.info("Listening for request...");
                Socket serviceSocket= serverSocket.accept();
                try{
				//executorService. execute(new Event(serviceSocket));
                }catch(Throwable t){
                	t.printStackTrace();
                }
            }

	}

}
