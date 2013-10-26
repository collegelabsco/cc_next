package com.devsquare.fts;

public class RandomMaze
{	
	public char[][] generateRandomMaze(String gid, int level) {
    	//initialize
    	char[][] maze = {       
    	        {'B', 'B', 'O', 'B', 'B', 'B', 'O', 'B', 'B', 'B'},
    			{'O', 'B', 'B', 'O', 'O', 'B', 'B', 'B', 'O', 'O'},
    			{'B', 'O', 'O', 'B', 'B', 'O', 'O', 'O', 'B', 'B'},
    			{'O', 'B', 'B', 'B', 'O', 'B', 'B', 'B', 'O', 'B'},
    			{'B', 'B', 'O', 'O', 'B', 'B', 'O', 'B', 'B', 'O'},
    			{'O', 'O', 'B', 'B', 'O', 'O', 'B', 'O', 'B', 'B'},
    			{'B', 'B', 'O', 'B', 'B', 'B', 'O', 'B', 'B', 'O'},
    			{'O', 'B', 'B', 'O', 'O', 'B', 'B', 'B', 'O', 'B'},
    			{'B', 'O', 'O', 'B', 'B', 'O', 'O', 'O', 'B', 'B'},
    			{'O', 'B', 'B', 'B', 'O', 'B', 'B', 'B', 'O', 'O'}};
    	
 
    	//int pl_row = (int) (Math.random() * (2 - 0 + 1) ) + 0;
        //int pl_col = (int) (Math.random() * (9 - 0 + 1) ) + 0;
    	int randir = 0;
        for(int i=0; i<10; i++) {
        	for(int j=0; j<10; j++) {
        		//left=1,right=2,up=3,down=4
        		
        		if(i==0 && j==0)
        			randir = 2;
        		else if(i==9 && j==9)
        			randir = 3;
        		else
        			randir = (int) (Math.random() * (4 - 1 + 1) ) + 1;
        		
        		if(randir == 1 && j != 0 && maze[i][j-1] == 'B') {
        			maze[i][j-1] = 'O';        			
        		}
        		else if(randir == 2 && j != 9 && maze[i][j+1] == 'B') {
        			maze[i][j+1] = 'O';
        		}
        		else if(randir == 3 && i != 0 && maze[i-1][j] == 'B') {
        			maze[i-1][j] = 'O';
        		}        	
        		else if(randir == 4 && i != 9 && maze[i+1][j] == 'B') {
        			maze[i+1][j] = 'O';
        		}
        		else if(j==0) {
        			maze[i][j+1] = 'O';
        		}
        		else if(j==9) {
        			maze[i][j-1] = 'O';
        		}
        		else if(i==0) {
        			maze[i+1][j] = 'O';
        		}
        		else if(i==9) {
        			maze[i-1][j] = 'O';
        		}        		
        	}
        }    
       
		//maze[(int) (Math.random() * (7 - 4 + 1) ) + 4][(int) (Math.random() * (9 - 0 + 1) ) + 0] = 'E';
        int randitem = -1;
        int lvl3enemy = 0;
        int lvl4enemy = 0;
        int count = 0;
        if(level==2) {
        	for(int i=3; i<8; i+=3) {        		
    			randitem = (int) (Math.random() * (8 - 2 + 1) ) + 2;        			
    			maze[i][randitem] = 'K';   			
        		
        	}        		
        }
        if(level==3) {
        	for(int i=3; i<8; i+=4) {        		
    			randitem = (int) (Math.random() * (8 - 2 + 1) ) + 2;        			
    			maze[i][randitem] = 'K';
    			lvl3enemy = i;   			      		
        	} 
        	randitem = (int) (Math.random() * (8 - 2 + 1) ) + 2;
        	maze[lvl3enemy-2][5] = 'E';
        }
        if((level==4)) {			
        	for(int i=4; i<8; i+=3) {        		
    			randitem = (int) (Math.random() * (7 - 2 + 1) ) + 2;        			
    			maze[i][randitem] = 'K';
    			lvl4enemy = i;
    			count++;
    			if(count==1)
    				maze[lvl4enemy-1][randitem] = 'E';
    			if(count==2)
    				maze[lvl4enemy-1][5] = 'e';
        	}
		}
		
		maze[(int) (Math.random() * (1 - 0 + 1) ) + 0][(int) (Math.random() * (9 - 0 + 1) ) + 0] = 'P';
		maze[(int) (Math.random() * (9 - 8 + 1) ) + 8][(int) (Math.random() * (9 - 0 + 1) ) + 0] = 'H';
		
		
        return maze;
    }
	

}
