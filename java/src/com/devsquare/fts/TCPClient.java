package com.devsquare.fts;

//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;

public class TCPClient {
    
    Socket clientSocket;
    DataInputStream input;
    DataOutputStream output;
    ServerSocket serverSocket;

    public TCPClient() {

        try {        	 
            final int portNumber = 443;
            //final int portNumber = 80;
            /*InetAddress ownIP=InetAddress.getLocalHost();
        	final String machineName = ownIP.getHostAddress();*/
            final String machineName = "207.211.87.41";
            System.out.println("Connecting to : " + machineName + ", port: " + portNumber);
            clientSocket = new Socket(machineName, portNumber);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public DataInputStream getInputStream() {
        return input;
    }

    public DataOutputStream getOutputStream() {
        return output;
    }

    public void closeInputStream() {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeOutputStream() {
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
