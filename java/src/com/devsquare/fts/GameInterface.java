package com.devsquare.fts;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.Point;

//Please DO NOT change this file.

public class GameInterface implements GameHandle{

    char[][] boardLayout;
    TCPClient tcpUser = null;
    DataInputStream input;
    DataOutputStream output;
    public String uid;
    boolean stopFlag = false;
    String userMessage = null;

    boolean debug = true;

    //Current user variables
    int step = 0;
    int bonus = 0;
    String status;
    int boardcols;
    int xPosition;
    int yPosition;
    int exPosition=-1;
    int eyPosition=-1;
    int ex1Position=-1;
    int ey1Position=-1;
    int gamelevel;

    public String getUserMessage() {
        return userMessage;
    }

    public int getxPosition() {
        return xPosition;
    }

    private void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    private void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }  
    
    public int getexPosition() {
        return exPosition;
    }

    private void setexPosition(int exPosition) {
        this.exPosition = exPosition;
    }

    public int geteyPosition() {
        return eyPosition;
    }

    private void seteyPosition(int eyPosition) {
        this.eyPosition = eyPosition;
    }

    public int getex1Position() {
        return ex1Position;
    }

    private void setex1Position(int ex1Position) {
        this.ex1Position = ex1Position;
    }

    public int getey1Position() {
        return ey1Position;
    }

    private void setey1Position(int ey1Position) {
        this.ey1Position = ey1Position;
    }


    public ArrayList<Point> getPointsbyType() {
    	char maze[][] = getBoard();
    	int rows = getBoard().length;
    	int cols = getBoard()[0].length;
    	Point location = null;
        char c = 'K';
        ArrayList<Point> locationList = new ArrayList<Point>();
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<cols; j++) {
    			if(maze[i][j]==c) {
    				location = new Point(i,j);
    				locationList.add(location);
    			}
    		}
    	}
    	return locationList;
    }   

    public GameInterface(String uid, int level) {
    	gamelevel = level;
        tcpUser = new TCPClient();
        input = tcpUser.getInputStream();
        output = tcpUser.getOutputStream();
        this.uid = uid;
        step = 0;
        bonus = 0;
        status = "";
        boardcols = 0;
        boardLayout = null;
        userMessage = "gameid=" + uid + ";" + "step=0;"
                + "command=init;source=user;level=" + gamelevel+";bonus=0";
        if(debug) System.out.println("GameInterface.java: Initial Message : " + userMessage);
        String inputString = null;
        try {
            output.writeBytes(userMessage + "\n");
            if(debug) System.out.println("GameInterface.java: Waiting to read ....");
            inputString = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(debug) System.out.println("GameInterface.java: Read data as : " + inputString);
        if(inputString != null){
            ServerData sdata = extractServerMsg(inputString);
            if(sdata != null)
                updateGame(sdata);
            else
            {
                //Invalid message format from server.
                status = "Invalid message format from server.";
                System.out.println("Invalid message format from server.");
                stopFlag = true;
            }
        }
    }

    public void updateGame(ServerData sdata){
        if(sdata.command == ServerData.BOARD && sdata.col != -1){
            status = "Board Initialized.";
            this.setxPosition(sdata.x);
            this.setyPosition(sdata.y);
            if(sdata.ex!=-1&&sdata.ey!=-1){
                this.setexPosition(sdata.ex);
                this.seteyPosition(sdata.ey);
            }
            if(sdata.ex1!=-1&&sdata.ey1!=-1){
                this.setex1Position(sdata.ex1);
                this.setey1Position(sdata.ey1);
            }
            boardLayout = sdata.board;
            boardcols = sdata.col;
            gamelevel = sdata.level;
        }
        else if(sdata.command == ServerData.STOP){
            status = sdata.msg;
            stopFlag = true;
        }
        
        else if(sdata.command == ServerData.INIT){
            status = "Connected to server";
        }
        else if(sdata.command == ServerData.NOP){
            status = "Player position changed. x=" + sdata.x + " ,y=" + sdata.y;
            boardLayout[getxPosition()][getyPosition()] = 'O';
            setxPosition(sdata.x);
            setyPosition(sdata.y);
            if(sdata.ex!=-1&&sdata.ey!=-1){
                boardLayout[getexPosition()][geteyPosition()] = 'O';
                this.setexPosition(sdata.ex);
                this.seteyPosition(sdata.ey);
                boardLayout[getexPosition()][geteyPosition()] = 'E';
            }
            if(sdata.ex1!=-1&&sdata.ey1!=-1){
                boardLayout[getex1Position()][getey1Position()] = 'O';
                this.setex1Position(sdata.ex1);
                this.setey1Position(sdata.ey1);
                boardLayout[getex1Position()][getey1Position()] = 'e';
            }

            boardLayout[getxPosition()][getyPosition()] = 'P';
        }
    }

    public void makeMove(String command, String type) {

        try {
            //int count = 0;
            String messageToServer = "gameid=" + uid + ";" + "step=" + step
                    + ";" +"source=user;"+ "command=" + command + ";" + "type=" + type + ";bonus="+bonus+";level="+gamelevel;
            output.writeBytes(messageToServer + "\n");
            if(debug) System.out.println("GameInterface.java: Writing back data as : " + messageToServer);
            	step++;
            if(debug) System.out.println("GameInterface.java: Waiting to read ....");
			if(!isFinished()){
				String inputString = input.readLine();
				if(debug) System.out.println("Read data as : " + inputString);
				if(inputString != null){
					ServerData sdata = extractServerMsg(inputString);
					bonus = sdata.bonus;
					if(sdata != null)
						updateGame(sdata);
					else
					{
						//Invalid message format from server.
						status = "Invalid message format from server.";
						System.out.println("Invalid message format from server.");
						stopFlag = true;
					}
				}
			}

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public ServerData extractServerMsg(String serverMsg){
        ServerData sd = null;

        if(serverMsg != null)
        {
            String[] msgs = serverMsg.split(";",0);
            sd = new ServerData();
            sd.col = -1;
            sd.x = -1;
            sd.y = -1;
            sd.ex = -1;
            sd.ey = -1;
            sd.ex1 = -1;
            sd.ey1 = -1;
            sd.bonus = 0;
            sd.level = 1;
            boolean rowfound = false;
            boolean userpos = false;
            boolean enemypos = false;
            for(int i=0;i<msgs.length;i++){
                String[] message = msgs[i].split("=",0);
                if(message[0].compareToIgnoreCase("gameid")==0 && message.length>1){
                    sd.gameid = message[1];
                }
                if(message[0].compareToIgnoreCase("step")==0 && message.length>1){
                    try{
                        sd.step = Integer.parseInt(message[1]);
                    }
                    catch(NumberFormatException e){
                        return null;
                    }
                }
                if(message[0].compareToIgnoreCase("bonus")==0 && message.length>1){
                    try{
                        sd.bonus = Integer.parseInt(message[1]);
                    }
                    catch(NumberFormatException e){
                        return null;
                    }
                }
                if(message[0].compareToIgnoreCase("command")==0 && message.length>1){
                    if(message[1].compareToIgnoreCase("init")==0){
                        sd.command = ServerData.INIT;
                    }
                    else if(message[1].compareToIgnoreCase("stop")==0){
                        sd.command = ServerData.STOP;
                    }
                    else if(message[1].compareToIgnoreCase("nop")==0){
                        sd.command = ServerData.NOP;
                        userpos = true;
                        enemypos = true;
                    }
                }
                if(message[0].compareToIgnoreCase("msg")==0 && message.length>1){
                    sd.msg = message[1];
                }
                if(message[0].compareToIgnoreCase("row")==0 && message.length>1){
                    try{
                        sd.col = Integer.parseInt(message[1]);
                        rowfound = true;
                    }
                    catch(NumberFormatException e){
                        return null;
                    }
                }
                if(message[0].compareToIgnoreCase("board")==0 && message.length>1 && rowfound){
                    sd.command = ServerData.BOARD;
                    int noofrows = message.length/sd.col;
                    sd.rows = noofrows;
                    //sd.board = new char[noofrows][sd.rowele];
                    sd.board = new char[10][10];
                    char boardchars[] = message[1].toCharArray();
                    int k=0;
                    int l=0;
                    for(int j=0;j<boardchars.length;j++){
                        k =j/sd.col;
                        l =j%sd.col;
                        if(boardchars[j] == 'P'){
                            sd.x = k;
                            sd.y = l;
                        }
                        if(boardchars[j] == 'E'){
                            sd.ex = k;
                            sd.ey = l;
                        }
                        if(boardchars[j] == 'e'){
                            sd.ex1 = k;
                            sd.ey1 = l;
                        }
                        sd.board[k][l] = boardchars[j];
                    }
                    
                }
                if(message[0].compareToIgnoreCase("level")==0 && message.length>1){
                    try{
                         sd.level = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                 }
                if(message[0].compareToIgnoreCase("x")==0 && message.length>1 && userpos){
                   try{
                        sd.x = Integer.parseInt(message[1]);
                    }
                    catch(NumberFormatException e){
                        return null;
                    }
                }
                if(message[0].compareToIgnoreCase("y")==0 && message.length>1 && userpos){
                   try{
                        sd.y = Integer.parseInt(message[1]);
                    }
                    catch(NumberFormatException e){
                        return null;
                    }
                }
                if(message[0].compareToIgnoreCase("ex")==0 && message.length>1 && enemypos){
                    try{
                         sd.ex = Integer.parseInt(message[1]);     
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                }
                if(message[0].compareToIgnoreCase("ey")==0 && message.length>1 && enemypos){
                    try{
                         sd.ey = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                }
                if(message[0].compareToIgnoreCase("ex1")==0 && message.length>1 && enemypos){
                    try{
                         sd.ex = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                 }
                 if(message[0].compareToIgnoreCase("ey1")==0 && message.length>1 && enemypos){
                    try{
                         sd.ey = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                }
                if(message[0].compareToIgnoreCase("ex2")==0 && message.length>1 && enemypos){
                    try{
                         sd.ex1 = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                 }
                 if(message[0].compareToIgnoreCase("ey2")==0 && message.length>1 && enemypos){
                    try{
                         sd.ey1 = Integer.parseInt(message[1]);
                     }
                     catch(NumberFormatException e){
                         return null;
                     }
                 }
            }
        }
        return sd;
    }

    public int getCurrentStep(){
        return step;
    }

    public char[][] getBoard(){
        return boardLayout;
    }

    public int getColumnCount(){
        return boardcols;
    }

    public int getLevel() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStatus(){
        return status;
    }

    public char[][] getBoardFrommessage(int rowCount, String message) {
        String[] tempArray = message.trim().split("=");
        int countValue = 0;
        char[] tempCharArray = tempArray[1].trim().toCharArray();

        int columnCount = (tempCharArray.length) / rowCount;
        char[][] finalArray = new char[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                finalArray[i][j] = tempCharArray[countValue];
                countValue++;
            }
        }
        return finalArray;
    }

    public boolean isFinished() {
        return stopFlag;
    }

}