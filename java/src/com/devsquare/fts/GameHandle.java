package com.devsquare.fts;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 7 Sep, 2010
 * Time: 12:39:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GameHandle {
    int getLevel();
    String getStatus();
    char[][] getBoard();
    void makeMove(String dir, String type);
}
