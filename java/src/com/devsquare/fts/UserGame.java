package com.devsquare.fts;

import java.util.ArrayList;
//import java.awt.Point;

class Cell {
	  public final int row;
	  public final int col;
	  
	  public Cell(int row, int col) {
	    this.row = row;
	    this.col = col;
	  }
	  
	  public boolean equals(Object other) {
	    return other != null
	        && (other instanceof Cell)
	        && this.row == ((Cell)other).row
	        && this.col == ((Cell)other).col;
	  }
	  public String toString() {
	    return ""+row+" "+col+"|";
	  }
}
public class UserGame {
	private static Cell ENTRY = null;
	private static Cell EXIT = null;
	private static ArrayList<String> sol = new ArrayList<String>();
	private static int countRecursion = 0;
	
	//private static boolean itemFound = false;
	//private static String item = "";
	
	public static void main(String[] args) {
		try{
			if (args.length == 0) {
	            System.out.println("Please pass the game id as an argument.");
	            return;
	        }
	        System.out.println("Starting with gameid: " + args[0]);
	        GameInterface game = new GameInterface(args[0], 0);
	        char maze[][] = null;
	        
	        int rows = game.getBoard().length;
	        int cols = game.getColumnCount();
	        if(game.getBoard() != null) {
	        	maze = new char[rows][cols];
	        }
	        maze = game.getBoard();
	        
	        for(int i=0; i<rows; i++) {
	        	for(int j=0; j<cols; j++) {	        		
	        		if(maze[i][j] == 'P') {
	        			ENTRY = new Cell(i,j);	        			
	        		}
	        		if(maze[i][j] == 'H') {
	        			EXIT = new Cell(i,j);
	        		}
	        	}
	        }
	        /*ArrayList<Point> points = new ArrayList<Point>();
	        points = game.getPointsbyType('I');*/
	       
	        
	        //Cell itemLoc = null; // cell containing location of items
	        // itemLocStr = ""; // string containing location of items
	        
	        //Cell enemyLoc = null;
	        //Cell enemyLocLeft = null;
	        //Cell enemyLocRight = null;
	        //Cell enemyLocUp = null;
	        //Cell enemyLocDown = null;
	        
	        //String enemyLocStr = "";
	        
	        Cell itemLoc = null;
	        String itemLocStr = "";
	        
	        //finding items
	        /*for(int i=0; i<rows; i++) {
	        	for(int j=0; j<cols; j++) {
	        		maze[i][j] = game.getBoard()[i][j];
	        		if(maze[i][j] == 'I') {
	        			itemLoc = new Cell(i,j);
	        			itemLocStr += itemLoc.toString();
	        		}
	        	}
	        }*/
	        //finding enemy location
	        /*for(int i=0; i<rows; i++) {
	        	for(int j=0; j<cols; j++) {
	        		maze[i][j] = game.getBoard()[i][j];
	        		if(maze[i][j] == '#') {
	        			enemyLoc = new Cell(i,j);
	        			//enemyLocLeft = new Cell(i,j-1);
	        			//enemyLocRight = new Cell(i,j+1);
	        			//enemyLocUp = new Cell(i-1,j);
	        			//enemyLocDown = new Cell(i+1,j);	        			
	        		}
	        	}
	        }*/
	        
	        //String enemyPos = enemyLoc.toString();
	
	        String path1 = null;
	        UserGame ug = new UserGame();
	
	        ug.step(ENTRY, "", maze, rows, cols);
	
	        int[] pathLen = new int[sol.size()];
	        int j=0;
	        int i=0;
	        boolean morepaths = false;
	        
	        //getting the path avoiding enemies
	        //level 3
	   
	        /*enemyLocStr = enemyLocLeft.toString()+enemyLocRight.toString()+enemyLocUp.toString()+enemyLocDown.toString();
	        String[] proximity = enemyLocStr.split("\\|+");
	           
	        for(i=0; i<sol.size(); i++) {
	        	path1 = sol.get(i);
	        	if(!path1.contains(proximity[0])||!path1.contains(proximity[1])||!path1.contains(proximity[2])||!path1.contains(proximity[3]) ) {
	        		path1 = sol.get(i);
	        		break;
	            }
	        }
	        System.out.println(enemyLocStr);*/
	        //System.out.println(path1.contains(enemyPos));
	
	        //getting the shortest path covering items
	        //level 2   
	        
	        
	        /*for(i=0; i<sol.size(); i++) {
	        	for(j=0; j<itemLocStr.length()-2; j+=4) {
                    System.out.println("Test");
                    path1 = sol.get(i);
	            	if(path1.contains(itemLocStr.substring(j,j+3)) && path1.length()>0) {
	            		morepaths = true;
	            		pathLen[i] = path1.length();               
	            	}
	            	else {
	            		morepaths = false;
	            		break;
	            	}            		
	            		
	        	}
	        }*/
	       
	        //getting the index of the shortest path
	        //level 1

            int smallest_path = 999;
            for(i=0; i<sol.size(); i++) {
	        	path1 = sol.get(i);
	        	if(path1.length()>0) {
	        		morepaths = true;
	        		pathLen[i] = path1.length();
	        	}
	        	else {
	        		morepaths = false;
	        		break;
	        	}
	        }
	        System.out.println("Path found: " + path1);
	        
	        int bestPath = 0;
	        if(morepaths == true) {
	        	bestPath = getMinValue(pathLen);        	
	        }
	        path1 = sol.get(bestPath);        
	        String[] coordinates = path1.split("\\|+");
	        int[] pointsX = new int[50];
	        int[] pointsY = new int[50];
	        	     
	        for(i=0; i<coordinates.length; i++) {
	        	pointsX[i] = Integer.parseInt(coordinates[i].substring(0,1));
	        	pointsY[i] = Integer.parseInt(coordinates[i].substring(2,3));	        	
	        }
	      
	
	        for(i=0,j=i+1; i<coordinates.length && j<coordinates.length; i++,j++) {
	        	if(pointsX[j]>pointsX[i]) {
	        		//System.out.print("down");
	        		game.makeMove("move","down");
	        	}
	        	if(pointsY[j]>pointsY[i]) {
	        		//System.out.print("right");
	        		game.makeMove("move","right");
	        	}
	        	if(pointsY[j]<pointsY[i]) {
	        		//System.out.print("left");
	        		game.makeMove("move","left");
	        	}
	        	if(pointsX[j]<pointsX[i]) {
	        		//System.out.print("up");
	        		game.makeMove("move","up");
	        	}
                System.out.println("User Pos: ("+ game.getxPosition() +","+game.getyPosition() +")");
                
                System.out.println("Enemy1 Pos: ("+ game.getexPosition() +","+game.geteyPosition() +")");
                System.out.println("Enemy2 Pos: ("+ game.getex1Position() +","+game.getey1Position() +")");
                for(int k=0; k<10; k++) {
                    for(int l=0; l<10; l++) {
                        System.out.print(game.getBoard()[k][l]+" ");
                    }
                }
	        }	        
	       
		}
		 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}			

	}
	
	
	public static int getMinValue(int[] numbers) {
		int minValue = numbers[0];
		int index = 0;
		int i = 0;
		for (i=1;i<numbers.length;i++) {
			if (numbers[i] < minValue) {
				minValue = numbers[i];
				index = i;
			}
			
		}
		return index;
	}

	public boolean step(Cell cell,String path, char[][] arr, int rows, int cols) {
		int r = cell.row;
		int c = cell.col;
		//boolean itemFound = false;
		if (r<0 || r>=rows || c<0 || c>=cols || arr[r][c]=='B') {
		     return false;
		}
		
		
	    path += cell.toString();
	    //enemyPos += enemyLoc.toString();

	    if (cell.equals(EXIT)) {
				//System.out.println(count);
				sol.add(path);
				countRecursion++;
				return true;
	    }
	  
	    if(countRecursion >= 1) {
	    	return true;
	    }

		Cell left  = new Cell(r, c-1);
	    Cell right = new Cell(r, c+1);
	    Cell down  = new Cell(r+1, c);
	    Cell up    = new Cell(r-1, c);
	    
	    boolean found = false;
	    if(!path.contains(down.toString()))  found |= step(down, path, arr, rows, cols);
	    if(!path.contains(left.toString()))  found |= step(left, path, arr, rows, cols);
	    if(!path.contains(right.toString())) found |= step(right, path, arr, rows, cols);
	    if(!path.contains(up.toString()))    found |= step(up, path, arr, rows, cols);
	    
	    return found;
	}
}