package com.devsquare.fts;

public class ServerData {
    public static int STOP = 1;
    public static int NOP = 2;
    public static int INIT = 3;
    public static int BOARD = 4;
    int command;
    String msg;
    String gameid;
    int step;
    int bonus;
    int level;
    char[][] board = null;
    int col;
    int rows;
    int x,y;
    int ex,ey;
    int ex1, ey1;
    
}
