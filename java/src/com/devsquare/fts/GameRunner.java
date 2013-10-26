package com.devsquare.fts;

import java.io.*;
import java.util.ArrayList;

public class GameRunner extends Thread {
    char board[][];
    Point userPt;
    Point goal;
    Point enemyPos1;
    Point enemyPos2;
    Point itemPos;
    Point left_e1,right_e1,up_e1,down_e1;
    Point left_e2,right_e2,up_e2,down_e2;
    int count;
    int bonus;
    static int MaxMoves = 500;
    Point max;
    static int plposX = 0;
    static int plposY = 0;
    boolean success;
    String boarddata;
    boolean connected;
    TCPClient client;
    String gameid = "";
    String status = "Not started";
    boolean debug = true;
    int gamelevel;
    boolean gameInit = false;
    Direction move;

    public void initialize(String gid, int level) {
        status = "Not started";
        gameid = gid;
        //level++;
        gamelevel = level;        
        RandomMaze d = new RandomMaze();
        boarddata = new String();        
        board = d.generateRandomMaze(gid,level);
        for(int i=0; i<board.length; i++) {
        	for(int j=0; j<board[0].length; j++) {
        		if(board[i][j]=='P')
        			userPt = new Point(i, j);
        		if(board[i][j]=='H')
        			goal = new Point(i, j);
        		if(board[i][j]=='K')
        			itemPos = new Point(i,j);
        		if(board[i][j]=='E')
        			enemyPos1 = new Point(i,j);
        		if(board[i][j]=='e')
        			enemyPos2 = new Point(i,j);
        	}        		
        }
        	/*{       
        {'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'P'},
		{'B', 'B', 'O', 'B', 'B', 'O', 'B', 'O', 'O', 'O'},
		{'O', 'B', 'O', 'B', 'O', 'O', 'O', 'B', 'B', 'B'},
		{'B', 'B', 'O', 'B', 'B', 'O', 'B', 'B', 'B', 'B'},
		{'O', 'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'B', 'B', 'O', 'B', 'B', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'B', 'O', 'O', 'B', 'B', 'B', 'O'},
		{'B', 'O', 'O', 'B', 'B', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'B', 'B', 'O', 'O'},
		{'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'H'}};*/
        
        for (int i = 0; i < board.length; i++)
            boarddata += new String(board[i]);
        //this.board = board;
        
        max = new Point(board.length - 1, board[0].length - 1);
        count = 0;
        bonus = 0;
        success = false;
        connected = false;
        client = new TCPClient();
        plposX = userPt.x; plposY = userPt.y;        


    }

    public int getStep() {
        return count;
    }
    public int getLevel() {
    	return gamelevel;
    }

    ClientData extractInfo(String clientmsg) {
        ClientData cd = null;
        long obtainedID = 0;
        String[] msgs = clientmsg.split(";", 0);
        try {
            obtainedID = Long.parseLong(msgs[0].split("=")[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid gameid obtained.");
            return null;
        }
        if (obtainedID != 0) {
            cd = new ClientData();
            for (int i = 0; i < msgs.length; i++) {
                String data = msgs[i];
                String name = data.split("=", 0)[0];
                String value = data.split("=", 0)[1];
                if (name.compareToIgnoreCase("command") == 0) {
                    //Check Commands
                    //1. Move
                    if (value.compareToIgnoreCase("init") == 0) {
                        cd.init = true;
                        break;
                    }
                    if (value.compareToIgnoreCase("move") == 0) {
                        data = msgs[++i];
                        name = data.split("=", 0)[0];
                        value = data.split("=", 0)[1];
                        if (name.compareToIgnoreCase("type") == 0 && value.compareToIgnoreCase("left") == 0) {
                            cd.dir = new Direction(Direction.Left);
                        } else if (name.compareToIgnoreCase("type") == 0 && value.compareToIgnoreCase("right") == 0) {
                            cd.dir = new Direction(Direction.Right);
                        } else if (name.compareToIgnoreCase("type") == 0 && value.compareToIgnoreCase("up") == 0) {
                            cd.dir = new Direction(Direction.Up);
                        } else if (name.compareToIgnoreCase("type") == 0 && value.compareToIgnoreCase("down") == 0) {
                            cd.dir = new Direction(Direction.Down);
                        }
                    }
                    //2. Use Item
                    if (value.compareToIgnoreCase("item") == 0) {
                        data = msgs[++i];
                        name = data.split("=", 0)[0];
                        value = data.split("=", 0)[1];
                        if (name.compareToIgnoreCase("item") == 0 && value.compareToIgnoreCase("bomb") == 0) {
                            cd.useItem = value;
                        } else if (name.compareToIgnoreCase("item") == 0 && value.compareToIgnoreCase("knife") == 0) {
                            cd.useItem = value;
                        }
                    }
                }
            }

        }
        return cd;
    }

    public char[][] getBoard() {
        return board;
    }

    public String getStatus() {
        return status;
    }
    
    public int getBonus() {
        return bonus;
    }


    public void run() {
        /*
          Create the stub object and access the client data from the stub
         */
        DataInputStream is = client.getInputStream();
        DataOutputStream os = client.getOutputStream();
        
        boolean initState = false;
        int toggle = -1;
        int subtoggle = 1;
        int[] pattern1 = {2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2,2,4,4,1,1,3,3,2};
        int[] pattern2 = {4,1,4,2,4,2,2,3,1,1,4,1,1,3,2,1,2,2,3,1,4,1,1,2,3,2,2,2,4,1,1,3,1,1,2,2,4,2,2,1,3,1,1,1,3,2,2,2,2,4,1,1,1,4,2,2,3,2,3,2,1,1,4,1,1,2,2};
        int pattern1_count=0;
        int pattern2_count=0;	
        try {

            //while(!invalidState && !success){
           
            
            while (true) {
            	
            	if(!gameInit){
            		os.writeBytes("gameid=" + gameid + ";step=0;command=init;source=server;\n");
            		gameInit = true;
            	}
            	
                String clientmsg = null;
                if(debug) System.out.println("GameRunner.java: Waiting to read... ");
                clientmsg = is.readLine();
                if(debug) System.out.println("GameRunner.java: Read Data as: " + clientmsg);
                String servermsg = "gameid=" + gameid + ";step=";
                int upper = 4;
                int lower = 1;                
                
                if(toggle==-1)
                	toggle++;
                else if(toggle==0)
                	toggle++;
                else if(toggle==1)
                	toggle-=2;
                
                if(subtoggle==0)
                	subtoggle=1;
                else
                	subtoggle=0;
                	
                /*for(int i=0; i<board.length; i++) {
                	for(int j=0; j<board[0].length; j++) {
                		//System.out.print(board[i][j]+" ");
                		if(board[i][j]=='E') {
                			enemyPos1 = new Point(i,j);
                		}
                		if(board[i][j]=='e') {
                			enemyPos2 = new Point(i,j);
                		}
                	}                	
                }*/
                if(gamelevel==3) {
                	 left_e1 = new Point(enemyPos1.x, enemyPos1.y-1);
                     right_e1 = new Point(enemyPos1.x, enemyPos1.y+1);
                     down_e1 = new Point(enemyPos1.x+1, enemyPos1.y);
                     up_e1 = new Point(enemyPos1.x-1, enemyPos1.y);                    
                } 
                if(gamelevel==4) {
               	 left_e1 = new Point(enemyPos1.x, enemyPos1.y-1);
                    right_e1 = new Point(enemyPos1.x, enemyPos1.y+1);
                    down_e1 = new Point(enemyPos1.x+1, enemyPos1.y);
                    up_e1 = new Point(enemyPos1.x-1, enemyPos1.y);
                    
                    left_e2 = new Point(enemyPos2.x, enemyPos2.y-1);
                    right_e2 = new Point(enemyPos2.x, enemyPos2.y+1);
                    down_e2 = new Point(enemyPos2.x+1, enemyPos2.y);
                    up_e2 = new Point(enemyPos2.x-1, enemyPos2.y);
                }     

                if (clientmsg != null) {
                    ClientData cd = extractInfo(clientmsg);
                    if (cd.init) {
                        count = 0;
                        userPt = new Point(plposX, plposY);
                        servermsg += + count + ";source=server;" + "row=" + (max.y + 1) + ";board=" + boarddata + ";level=" + gamelevel;
                        initState = true;
                        connected = true;
                    }
                    else if(initState){
                        count++;
                        if (connected && count >= GameRunner.MaxMoves) {
                            status = "Game Over. All moves finished";
                            servermsg += + count + ";source=server;" + "command=stop;msg=All moves finished.Game over;";
                            initState = false;
                            connected = false;
                            userPt = new Point(plposX, plposY);
                            if(debug) System.out.println("GameRunner.java: Writing back data as: " + servermsg);
                            os.writeBytes(servermsg + "\n");

                        } else if (cd == null) {
                            //End Game
                            status = "Game Over: Invalid communication format";
                            initState = false;
                            System.out.println("GameEnd: Data recieved from client not in correct format.");
                            servermsg += + count + ";source=server;" + "command=stop;msg=Data format not correct;";
                            userPt = new Point(plposX, plposY);
                            connected = false;
                            if(debug) System.out.println("GameRunner.java: Writing back data as: " + servermsg);
                            os.writeBytes(servermsg + "\n");
                        }
                        /*else if(cd.init ){
                            servermsg += + count + ";source=server;" + "row=" + (max.y + 1) + ";board=" + boarddata + ";";
                            count=0;
                            initState = true;
                            connected = true;
                        }*/
                        else if (connected && cd.dir != null) {
                            Point nxtPt = userPt.moveInDirection(cd.dir);
                            if (nxtPt.x > max.x || nxtPt.y > max.y || nxtPt.x < 0 || nxtPt.y < 0) {
                                //End game
                                initState = false;
                                status = "Game Over: Invalid Move";
                                System.out.println("GameEnd: Moving out of board limits.");
                                servermsg += + count + ";source=server;" + "command=stop;msg=ClientError: Moving out of board limits;";
                                userPt = new Point(plposX, plposY);
                                connected = false;
                                if(debug) System.out.println("GameRunner.java: Writing back data as: " + servermsg);
                                os.writeBytes(servermsg + "\n");
                                
                            }
                            else if(gamelevel==3 && (nxtPt.equals(enemyPos1) || userPt.equals(enemyPos1))) {
                            	//End game
                                initState = false;
                                connected = false;
                                Thread.sleep(300);
                                status = "Game Over: Caught by Enemy";
                                System.out.println("GameEnd: Caught by Enemy");
                                //count--;
                                count = -1;
                                servermsg += + count + ";source=server;" + "command=stop;msg=Caught by Enemy;";
                               
                                userPt = new Point(plposX, plposY);
                                //enemyPos = new Point(enposX, enposY);                                           
                                if(debug) System.out.println("Writing back data as: " + servermsg);
                                os.writeBytes(servermsg + "\n");                                    
                                //break;
                            }
                            else if(gamelevel==4 && (nxtPt.equals(enemyPos1) || userPt.equals(enemyPos1) ||nxtPt.equals(enemyPos2) || userPt.equals(enemyPos2))) {
                            	//End game
                                initState = false;
                                connected = false;
                                Thread.sleep(300);
                                status = "Game Over: Caught by Enemy";
                                System.out.println("GameEnd: Caught by Enemy");
                                count = -1;
                                servermsg += + count + ";source=server;" + "command=stop;msg=Caught by Enemy;";
                                userPt = new Point(plposX, plposY);
                                //enemyPos = new Point(enposX, enposY);                                           
                                if(debug) System.out.println("Writing back data as: " + servermsg);
                                os.writeBytes(servermsg + "\n");                                    
                                //break;
                            }
                            else{
                                char c = board[nxtPt.x][nxtPt.y];
                                if (c == 'B') {
                                    //End game
                                    initState = false;
                                    connected = false;
                                    status = "Game Over: Invalid Move";
                                    System.out.println("GameEnd: Moving to blocked space.");
                                    servermsg += + count + ";source=server;" + "command=stop;msg=ClientError: Moving to blocked space;";
                                    userPt = new Point(plposX, plposY);
                                    if(debug) System.out.println("GameRunner.java: Writing back data as: " + servermsg);
                                    os.writeBytes(servermsg + "\n");
                                    //break;
                                }
                                else if (nxtPt.equals(goal)) {
                                    //End game, Success
                                    initState = false;
                                    connected = false;
                                    status = "Game Over: Saved the captive";
                                    System.out.println("GameEnd: Success");
                                    servermsg += + count + ";source=server;" + "command=stop;msg=Success;"+"bonus="+bonus +";level=" + gamelevel;
                                    //count = 0;
                                    userPt = new Point(plposX, plposY);
                                    os.writeBytes(servermsg + "\n");
                                    if(gamelevel<4) {
                                    	gamelevel++;
                                        pattern2_count=0;
                                        Thread.sleep(200);
                                    	initialize(gameid, gamelevel);
                                    	gameInit = false;                            
                                    }
                                    else
                                        System.out.println("level 4");
                                    //break;
                                } 
                                else if(c == 'K') {
                                	bonus++;
                                	/*if(debug) System.out.print("Moving in direction: ");
                                    switch(cd.dir.getDirection()){
                                        case 1:
                                            System.out.println("left");
                                            break;
                                        case 2:
                                            System.out.println("right");
                                            break;
                                        case 3:
                                            System.out.println("up");
                                            break;
                                        case 4:
                                            System.out.println("down");
                                            break;
                                    }*/
                                	status = "Running";
                                	board[userPt.x][userPt.y] = 'O';
                                    userPt.changeToDirection(cd.dir);
                                    board[userPt.x][userPt.y] = 'P';
                                    if(gamelevel==1 || gamelevel==2)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + ";bonus="+bonus+";level=" + gamelevel;
                                    if(gamelevel==3)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + 
                                    	";ex="+enemyPos1.x+";ey="+enemyPos1.y+";bonus="+bonus+";level=" + gamelevel;
                                    if(gamelevel==4)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + 
                                    	";ex1="+enemyPos1.x+";ey1="+enemyPos1.y+";ex2="+enemyPos2.x+";ey2="+enemyPos2.y+";bonus="+bonus+";level=" + gamelevel;
                                    
                                }
                                else {
                                    //Move next
                                    status = "Running";
                                    if(debug) System.out.print("Moving in direction: ");
                                    switch(cd.dir.getDirection()){
                                        case 1:
                                            System.out.println("left");
                                            break;
                                        case 2:
                                            System.out.println("right");
                                            break;
                                        case 3:
                                            System.out.println("up");
                                            break;
                                        case 4:
                                            System.out.println("down");
                                            break;
                                    }
                                    
                                    board[userPt.x][userPt.y] = 'O';
                                    userPt.changeToDirection(cd.dir);
                                    board[userPt.x][userPt.y] = 'P';
                                   
                                  //toggling movement
                                    /*if(gamelevel == 3) {
                                    	if(toggle == 0 && subtoggle == 0 && board[enemyPos1.x][enemyPos1.y-1] != 'B') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(1);
                                        	enemyPos1.changeToDirection(move);
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        
                                        else if(toggle == -1 && board[enemyPos1.x][enemyPos1.y+1] != 'B') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(2);
                                        	enemyPos1.changeToDirection(move);
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        	
                                        }
                                        else if(toggle == 0 && subtoggle == 1 && board[enemyPos1.x][enemyPos1.y+1] != 'B') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(2);
                                        	enemyPos1.changeToDirection(move);
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        else if(toggle == 1 && board[enemyPos1.x][enemyPos1.y-1] != 'B') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(1);
                                        	enemyPos1.changeToDirection(move);
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    }*/                      
                                 
                                    /*if(gamelevel==3) {
                                    	Thread.sleep(100);                                 
                                        //random movement of first enemy
                                    	int randomNumber = (int) (Math.random() * (upper - lower + 1) ) + lower;
                                        if(randomNumber==1 && enemyPos1.y!=0 && board[left_e1.x][left_e1.y]!='B' && board[left_e1.x][left_e1.y]!='H') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(randomNumber);
                                        	enemyPos1.changeToDirection(move);
                                        	
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        else if(randomNumber==2 && enemyPos1.y!=9 && board[right_e1.x][right_e1.y]!='B' && board[right_e1.x][right_e1.y]!='H') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	 
                                        	enemyPos1.changeToDirection(move);                                    	
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        else if(randomNumber==3 && enemyPos1.x!=0 && board[up_e1.x][up_e1.y]!='B' && board[up_e1.x][up_e1.y]!='H') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	
                                        	enemyPos1.changeToDirection(move);                                    	
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        else if(randomNumber==4 && enemyPos1.x!=9 && board[down_e1.x][down_e1.y]!='B' && board[down_e1.x][down_e1.y]!='H') {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	 
                                        	enemyPos1.changeToDirection(move);                                    	
                                        	board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                        //random movement of second enemy
                                        randomNumber = (int) (Math.random() * (upper - lower + 1) ) + lower;
                                        if(randomNumber==1 && enemyPos2.y!=0 && board[left_e2.x][left_e2.y]!='B' && board[left_e2.x][left_e2.y]!='H') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(randomNumber);
                                        	enemyPos2.changeToDirection(move);
                                        	
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                        else if(randomNumber==2 && enemyPos2.y!=9 && board[right_e2.x][right_e2.y]!='B' && board[right_e2.x][right_e2.y]!='H') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	 
                                        	enemyPos2.changeToDirection(move);                                    	
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                        else if(randomNumber==3 && enemyPos2.x!=0 && board[up_e2.x][up_e2.y]!='B' && board[up_e2.x][up_e2.y]!='H') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	
                                        	enemyPos2.changeToDirection(move);                                    	
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                        else if(randomNumber==4 && enemyPos2.x!=9 && board[down_e2.x][down_e2.y]!='B' && board[down_e2.x][down_e2.y]!='H') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(randomNumber);                                    	 
                                        	enemyPos2.changeToDirection(move);                                    	
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }                                                             
                                                  	
                                    }*/ 
                                    //toggle movement
                                    /*if(gamelevel == 4) {
                                    	if(toggle == 0 && subtoggle == 0 && board[enemyPos2.x][enemyPos2.y-1] != 'B') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(1);
                                        	enemyPos2.changeToDirection(move);
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                        
                                        else if(toggle == -1 && board[enemyPos2.x][enemyPos2.y+1] != 'B') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(2);
                                        	enemyPos2.changeToDirection(move);
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        	
                                        }
                                        else if(toggle == 0 && subtoggle == 1 && board[enemyPos2.x][enemyPos2.y+1] != 'B') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(2);
                                        	enemyPos2.changeToDirection(move);
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                        else if(toggle == 1 && board[enemyPos2.x][enemyPos2.y-1] != 'B') {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	move = new Direction(1);
                                        	enemyPos2.changeToDirection(move);
                                        	board[enemyPos2.x][enemyPos2.y] = 'e';
                                        }
                                    }*/
                                    //patterned movement
                                    if(gamelevel==3) {
                                    	Thread.sleep(100);
                                    	if(enemyPos1.x!=9 && pattern2[pattern2_count]==2) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                            Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]); 
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            int tries = 0;
                                            do{
                                                tries++;
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.y!=9 && pattern2[pattern2_count]==4) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Up);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.x!=0 && pattern2[pattern2_count]==1) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Right);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.y!=0 && pattern2[pattern2_count]==3) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	System.out.println("Pattern: "+pattern2[pattern2_count]);
                                    	pattern2_count=(pattern2_count+1)%pattern2.length;
                                    }
                                    if(gamelevel==4) {                                    	
                                    	Thread.sleep(100);
                                    	if(enemyPos1.x!=9 && pattern1[pattern1_count]==2) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                            Point tmppt;
                                            Direction nxtdir = new Direction(pattern1[pattern1_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.y!=9 && pattern1[pattern1_count]==4) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern1[pattern1_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Up);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.x!=0 && pattern1[pattern1_count]==1) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern1[pattern1_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Right);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	else if(enemyPos1.y!=0 && pattern1[pattern1_count]==3) {
                                        	board[enemyPos1.x][enemyPos1.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern1[pattern1_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos1.x,enemyPos1.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos1.changeToDirection(move);
                                            board[enemyPos1.x][enemyPos1.y] = 'E';
                                        }
                                    	System.out.println("Pattern: "+pattern1[pattern1_count]);
                                    	pattern1_count=(pattern1_count+1)%pattern1.length;
                                    	if(enemyPos2.x!=9 && pattern2[pattern2_count]==2) {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                            Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos2.x,enemyPos2.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos2.changeToDirection(move);
                                            board[enemyPos2.x][enemyPos2.y] = 'E';
                                        }
                                    	else if(enemyPos2.y!=9 && pattern2[pattern2_count]==4) {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos2.x,enemyPos2.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Up);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos2.changeToDirection(move);
                                            board[enemyPos2.x][enemyPos2.y] = 'E';
                                        }
                                    	else if(enemyPos2.x!=0 && pattern2[pattern2_count]==1) {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos2.x,enemyPos2.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Right);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Up);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos2.changeToDirection(move);
                                            board[enemyPos2.x][enemyPos2.y] = 'E';
                                        }
                                    	else if(enemyPos2.y!=0 && pattern2[pattern2_count]==3) {
                                        	board[enemyPos2.x][enemyPos2.y] = 'O';
                                        	Point tmppt;
                                            Direction nxtdir = new Direction(pattern2[pattern2_count]);
                                            ArrayList<Direction> triedDir = new ArrayList<Direction>();
                                            do{
                                                move = nxtdir;
                                                triedDir.add(move);
                                                tmppt = new Point(enemyPos2.x,enemyPos2.y);
                                                tmppt.changeToDirection(move);
                                                nxtdir = new Direction(Direction.Left);
                                                if(triedDir.contains(nxtdir)){
                                                    nxtdir = new Direction(Direction.Right);
                                                    if(triedDir.contains(nxtdir)){
                                                        nxtdir = new Direction(Direction.Down);
                                                    }
                                                }
                                            }while((tmppt.x >9 || tmppt.y >9 || tmppt.x <0 || tmppt.y <0) || (board[tmppt.x][tmppt.y]== 'K') || (board[tmppt.x][tmppt.y]== 'H') || (board[tmppt.x][tmppt.y]== 'B') && triedDir.size()<4);

                                            enemyPos2.changeToDirection(move);
                                            board[enemyPos2.x][enemyPos2.y] = 'E';
                                        }
                                    	System.out.println("Pattern: "+pattern2[pattern2_count]);
                                    	pattern2_count++;
                                    }
                                    if(gamelevel==1 || gamelevel==2)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + ";bonus="+bonus+";level=" + gamelevel;
                                    if(gamelevel==3)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + 
                                    	";ex="+enemyPos1.x+";ey="+enemyPos1.y+";bonus="+bonus+";level=" + gamelevel;
                                    if(gamelevel==4)
                                    	servermsg += + count + ";source=server;" + "command=nop;x=" + userPt.x + ";y=" + userPt.y + 
                                    	";ex1="+enemyPos1.x+";ey1="+enemyPos1.y+";ex2="+enemyPos2.x+";ey2="+enemyPos2.y+";bonus="+bonus+";level=" + gamelevel;
                                    
                                }
                            }
                        }
                    }
                    if(debug && connected) System.out.println("GameRunner.java: Writing back data as: " + servermsg);
                    if(connected) os.writeBytes(servermsg + "\n");

                }
                Thread.sleep(300);
                
            }
            
            
            
         } catch (Exception e) {
                e.printStackTrace();
         }
         
    }
}
