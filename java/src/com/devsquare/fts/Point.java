package com.devsquare.fts;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point pt) {
        x = pt.x;
        y = pt.y;
    }

    public int distance(Point pt) {
        int xdist = Math.abs(pt.x - x);
        int ydist = Math.abs(pt.y - y);
        return (xdist + ydist);
    }

    public Point moveInDirection(Direction dir) {
        Point p1 = new Point(x, y);
        p1.x += dir.getAddX();
        p1.y += dir.getAddY();
        return p1;
    }

    public void changeToDirection(Direction dir) {        
        x += dir.getAddX();
        y += dir.getAddY();
    }

    public void changeToPoint(Point pt) {
        x = pt.x;
        y = pt.y;
    }

    public boolean equals(Point pt) {
        if (pt.x == x && pt.y == y)
            return true;
        else
            return false;
    }

    public boolean equals(Object o){
            return (o!=null)
                    && (o instanceof  Point)
                    && (x == ((Point)o).x)
                    && (y == ((Point)o).y);
    }

    public String toString() {
        return "("+x+","+y+")";
    }
    /*public void changeToDirection(Direction dir){
        x = x + dir.addx;
        y = y + dir.addy;
    }*/
}