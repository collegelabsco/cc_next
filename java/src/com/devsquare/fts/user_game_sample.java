package com.devsquare.fts;

//import java libraries as per needed

public class user_game_sample {

	public static void main(String[] args) {
		try{
			if (args.length == 0) {
	            System.out.println("Please pass the game id as an argument.");
	            return;
	        }
	        System.out.println("Starting with gameid: " + args[0]);
	        GameInterface game = new GameInterface(args[0], 1); //change this to 2 for level-2 and so on
			//write your code here
			//example
		        game.makeMove("move","down"); //moves player block down by one step
			//example
			game.makeMove("move","right"); //moves player block right by one step
			game.makeMove("move","down"); //moves player block right by one step
            game.makeMove("move","right"); //moves player block right by one step
			game.makeMove("move","left"); //moves player block right by one step

            if(game.getexPosition()!=-1&&game.geteyPosition()!=-1){
                //Getting first enemy position
                System.out.println("Enemy1 Pos: ("+ game.getexPosition() +","+game.geteyPosition() +")");
            }
            if(game.getex1Position()!=-1&&game.getey1Position()!=-1){
                //Getting second enemy position
                System.out.println("Enemy2 Pos: ("+ game.getex1Position() +","+game.getey1Position() +")");
            }
            //game.makeMove("move","right"); //moves player block right by one step
        }
		catch(Exception e) {
			System.out.println("Unable to Solve Maze");
		}

	}

}