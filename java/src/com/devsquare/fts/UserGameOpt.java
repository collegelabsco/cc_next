package com.devsquare.fts;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 22 Jul, 2010
 * Time: 12:30:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGameOpt {
	static boolean debug = true;
    static ArrayList<Point> directions = new ArrayList<Point>();
    char mat[][];
    final int max = 9;
    ArrayList<ArrayList<Point>> pathsToGoal = new ArrayList<ArrayList<Point>>();

    class State{
        public Point curPt;
        public int x_direction;
        public int y_direction;
        public ArrayList<Point> path;
        int x_goal;
        int y_goal;
        State(int x, int y, int x_direction, int y_direction, ArrayList<Point> path, int max_x, int max_y){
            this.curPt = new Point(x,y);
            this.x_direction = x_direction;
            this.y_direction = y_direction;
            x_goal = max_x;
            y_goal = max_y;
            if(path != null){
                this.path = new ArrayList<Point>(path);
                this.path.add(new Point(x,y));
            }
            else
                this.path = new ArrayList<Point>();
        }

        public boolean pointedVisited(int x, int y){
            for(Point pt : path){
            if(pt.x == x && pt.y == y)
                return true;
            }
            return false;
        }

        public State moveInDirection(int x_direction,int y_direction){
            State new_state = new State(this.curPt.x+x_direction,this.curPt.y+y_direction,x_direction,y_direction,this.path,this.x_goal,this.y_goal);
            return new_state;
        }

        public State moveInSameDirection(){
            State new_state = new State(curPt.x+x_direction,curPt.y+y_direction,x_direction,y_direction,this.path,this.x_goal,this.y_goal);
            return new_state;
        }
    }


    int getMinimumCost(char mat[][], Point player, Point goal){
        if(mat == null)
            return -1;
        System.out.println("Printing Matrix: ");
        for(int i=0;i<mat.length;i++){
            System.out.print("[ ");
            for(int j=0;j<mat[0].length;j++){
                    System.out.print(mat[i][j] + " ");
            }
            System.out.println(" ]");
        }
        this.mat = mat;

        ArrayList<Point>Path = new ArrayList<Point>();
        State ini_state = new State(player.x,player.y,0,0,Path,goal.x,goal.y);
        return this.getMinCost(ini_state);
        //return -1;
    }

    int getMinCost(State curstate){

        if(curstate == null)
            return -1;
        if(curstate.curPt.x == -1 || curstate.curPt.y == -1 || (curstate.x_direction == -1 && curstate.y_direction == 1) || (curstate.x_direction == 1 && curstate.y_direction == -1) || (curstate.x_direction == 1 && curstate.y_direction == 1) || (curstate.x_direction == -1 && curstate.y_direction == -1))
            return -1;
        int m_cost = 9999;

        //End point Reached
        if(curstate.curPt.x == curstate.x_goal && curstate.curPt.y == curstate.y_goal){
            pathsToGoal.add(new ArrayList<Point>(curstate.path));
            return 0;
        }

        //Check all 4 Directions
        for(Point direction : directions){
            //Check only those directions in which valid path exists and tht direction has not been previously traversed and it is within limits of given matrix.
            if(debug) System.out.println("Current Direction: (" + direction.x + "," + direction.y + ")");
            if( curstate.curPt.x + direction.x <= max && curstate.curPt.x + direction.x >= 0 && curstate.curPt.y + direction.y >= 0 && curstate.curPt.y + direction.y <= max && mat[curstate.curPt.x+direction.x][curstate.curPt.y+direction.y] != 'B' && mat[curstate.curPt.x+direction.x][curstate.curPt.y+direction.y] != 'E' && !curstate.pointedVisited(curstate.curPt.x+direction.x,curstate.curPt.y+direction.y)){
                State new_state;
                if(curstate.x_direction == 0 && curstate.y_direction==0){
                    new_state = new State(curstate.curPt.x+direction.x,curstate.curPt.y+direction.y,direction.x,direction.y,curstate.path,curstate.x_goal,curstate.y_goal);
                }
                else
                    new_state = curstate.moveInDirection(direction.x,direction.y);
                 if(debug)System.out.print("Cur Node: (" + new_state.curPt.x + "," + new_state.curPt.y +")");
                int cost = getMinCost(new_state);
                if(debug)System.out.println(", Cost: " + cost);
                    cost+=1;
                if(cost < m_cost)
                    m_cost = cost;
            }

        }
        return m_cost;
    }

    public static void main(String[] args) {
        directions.add(new Point(1 , 0));
        directions.add(new Point(0 , 1));
        directions.add(new Point(-1 , 0));
        directions.add(new Point(0, -1));
        try{
			if (args.length == 0) {
	            System.out.println("Please pass the game id as an argument.");
	            return;
	        }
	        System.out.println("Starting with gameid: " + args[0]);
	        GameInterface game = new GameInterface(args[0], 1);
	        char maze[][] = null;

	        int rows = game.getBoard().length;
	        int cols = game.getColumnCount();
	        if(game.getBoard() != null) {
	        	maze = new char[rows][cols];
	        }
	        maze = game.getBoard();

            Point player = new Point(0,0);
            Point goal = new Point(0,0);
            for(int i=0; i<rows; i++) {
	        	for(int j=0; j<cols; j++) {
	        		if(maze[i][j] == 'P') {
	        			player = new Point(i,j);
	        		}
	        		if(maze[i][j] == 'H') {
	        			goal = new Point(i,j);
	        		}
	        	}
	        }

            UserGameOpt ug = new UserGameOpt();
            int min_path = ug.getMinimumCost(maze,player,goal);
            ArrayList shortestPath = new ArrayList();
            Iterator paths = ug.pathsToGoal.iterator();
            while(paths.hasNext()){
                ArrayList<Point> path = (ArrayList<Point>)paths.next();
                if(path.size() == min_path){
                    shortestPath = path;
                    break;
                }
            }

            //Iterator<Point> p = shortestPath.iterator();
            for(int i=0,j=i+1;i<shortestPath.size()-1;i++,j++) {
                Point curr = (Point)shortestPath.get(i);
                Point nxt = (Point)shortestPath.get(j);
                if(nxt.x>curr.x) {
	        		System.out.print("down");
	        		game.makeMove("move","down");
	        	}
	        	if(nxt.y>curr.y) {
	        		System.out.print("right");
	        		game.makeMove("move","right");
	        	}
	        	if(nxt.y<curr.y) {
	        		System.out.print("left");
	        		game.makeMove("move","left");
	        	}
	        	if(nxt.y<curr.x) {
	        		System.out.print("up");
	        		game.makeMove("move","up");
	        	}

	        }

		}
		catch(Exception e) {
			System.out.println("Unable to Solve Maze");
		}

	}

}
