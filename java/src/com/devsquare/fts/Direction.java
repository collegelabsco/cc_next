package com.devsquare.fts;

public final class Direction {
    public static int Left = 1;
    public static int Right = 2;
    public static int Up = 3;
    public static int Down = 4;
    private int addx;   //Add value of this to the Point.x to move point in that direction.
    private int addy;   //Add value of this to the Point.y to move point in that direction.
    int curDirection;

    public Direction(int dir) {
        curDirection = dir;
        switch (dir) {
            case 1://left
                addx = 0;
                addy = -1;
                break;
            case 2://right
                addx = 0;
                addy = 1;
                break;
            case 3://up
                addx = -1;
                addy = 0;
                break;
            case 4://down
                addx = 1;
                addy = 0;
                break;
            default:
                addx = 0;
                addy = 0;
                break;
        }
    }

    public boolean equals(Object o){
        boolean success = false;
        if(o instanceof Direction){
            Direction d = (Direction)o;
            if(d.getDirection() == curDirection){
                return true;
            }
        }
        return success;
    }

    public int getDirection() {
        return curDirection;
    }

    public int getAddX() {
        return addx;
    }

    public int getAddY() {
        return addy;
    }

    public void changeDirection(Direction dir) {
        curDirection = dir.curDirection;
        addx = dir.addx;
        addy = dir.addy;
    }

    public static int getDirection(int x, int y) {
        //int direction;
        if (x == -1 && y == 0)
            return Direction.Left;
        if (x == 1 && y == 0)
            return Direction.Right;
        if (x == 0 && y == 1)
            return Direction.Up;
        if (x == 0 && y == -1)
            return Direction.Down;
        return 0;
    }
}