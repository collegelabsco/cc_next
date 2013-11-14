package com.devsquare.fts;

import javax.swing.*;
//import java.applet.Applet;
import java.awt.*;
import java.text.*;

public class Simulation extends JFrame {
  Dimension d;
  Font largefont = new Font("Helvetica", Font.BOLD, 28);
  Font smallfont = new Font("Helvetica", Font.BOLD, 14);
 
  FontMetrics fmsmall, fmlarge;
  Graphics goff;
  Image ii;
  Thread thethread;
  MediaTracker thetracker = null;
  Color dotcolor = new Color(192, 192, 0);
  int bigdotcolor = 192;
  int dbigdotcolor = -2;
  Color mazecolor;
  int[] dx, dy;
  final int width = 10;
  final int blockSize = 40;
  int userposx, userposy;
  Image userimg; 
  GameRunner mGame;  

  public void initialize(GameRunner game) {
      mGame = game;
      JPanel p = new JPanel();
      this.setSize(480,575);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.add(p);
      this.setVisible(true);
      this.setResizable(false);
      Graphics g;
      d = size();
      setBackground(Color.black);
      g = getGraphics();
      g.setFont(smallfont);
      fmsmall = g.getFontMetrics();
      g.setFont(largefont);
      fmlarge = g.getFontMetrics();
      dx = new int[4];
      dy = new int[4];
      mazecolor = new Color(32, 192, 255);   
      
      
  }

  public void DrawMaze() {
      bigdotcolor = bigdotcolor + dbigdotcolor;
      if (bigdotcolor <= 64 || bigdotcolor >= 192)
          dbigdotcolor = -dbigdotcolor;
      for (int i = 0; i < width; i++) {
          for (int j = 0; j < width; j++) {
              int y = i + 1;
              int x = j + 1;
              goff.setColor(Color.WHITE); 
              if( i == mGame.userPt.x && j == mGame.userPt.y){
                  goff.setColor(Color.GREEN);
                  goff.fillRect(x * 40, y * 40, 40, 40);
                  goff.setColor(Color.BLACK);
                  goff.setFont(largefont);
                  goff.drawString("P", x * 40 + 15, y * 40 + 30);
              }
              if (mGame.getBoard()[i][j] == 'O') {
                  goff.setColor(Color.white);
                  goff.fillRect(x * 40, y * 40, 40, 40);
              } else if (mGame.getBoard()[i][j] == 'B') {
                  goff.setColor(Color.DARK_GRAY);
                  goff.fillRect(x * 40, y * 40, 40, 40);
              } else if (mGame.getBoard()[i][j] == 'H') {
                  goff.setColor(Color.RED);
                  goff.fillRect(x * 40, y * 40, 40, 40);
                  goff.setColor(Color.BLACK);
                  goff.setFont(largefont);
                  goff.drawString("G", x * 40 + 15, y * 40 + 30);
              } else if (mGame.getBoard()[i][j] == 'E') {
                  goff.setColor(Color.MAGENTA);
                  goff.fillRect(x * 40, y * 40, 40, 40);
                  goff.setColor(Color.BLACK);
                  goff.setFont(largefont);
                  goff.drawString("E", x * 40 + 15, y * 40 + 30);
              } else if (mGame.getBoard()[i][j] == 'K') {
                  goff.setColor(Color.YELLOW);
                  goff.fillRect(x * 40, y * 40, 40, 40);
                  goff.setColor(Color.BLACK);
                  goff.setFont(largefont);
                  goff.drawString("K", x * 40 + 15, y * 40 + 30);
              }else if (mGame.getBoard()[i][j] == 'e') {
                  goff.setColor(Color.MAGENTA);
                  goff.fillRect(x * 40, y * 40, 40, 40);
                  goff.setColor(Color.BLACK);
                  goff.setFont(largefont);
                  goff.drawString("E", x * 40 + 15, y * 40 + 30);
              }
         
              goff.setColor(Color.BLACK);
              goff.drawLine(x * 40, y * 40, x * 40 + 40, y * 40);
              goff.drawLine(x * 40, y * 40, x * 40, y * 40 + 40);
              goff.drawLine(x * 40 + 40, y * 40, x * 40 + 40, y * 40 + 40);
              goff.drawLine(x * 40, y * 40 + 40, x * 40 + 40, y * 40 + 40);
          }
          goff.setColor(Color.BLACK);
          goff.setFont(smallfont);
          goff.drawString("Number of Steps :  " + mGame.getStep(), 125, 475);
          goff.drawString("Cities Visited :  " + mGame.getBonus(), 125, 500);            
          goff.drawString("Status :  " + mGame.getStatus(), 125, 525);            
          goff.setColor(Color.RED);
          goff.drawString("Level: " + mGame.getLevel(), 125, 550);           

      }
  }

  public void paintComponent(Graphics g) {

      if (goff == null && d.width > 0 && d.height > 0) {
          ii = createImage(d.width, d.height);
          goff = ii.getGraphics();
      }
      if (goff == null || ii == null)
          return;

      goff.setColor(Color.WHITE);
      goff.fillRect(0, 0, d.width, d.height);


      DrawMaze();
      g.drawImage(ii, 0, 0, this);
  }

  public void doRefresh() {
      Graphics g;

      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
      g = getGraphics();
      while (true) {
          try {
              paintComponent(g);
              Thread.sleep(250);
          }
          catch (InterruptedException e) {
              break;
          }
      }
  }

  public static void main(String[] args) {
      // pass game id as args[0]
      /*if (args.length == 0) {
          System.out.println("Please pass the game id as an argument.");
          return;
      }*/
      int level = 1;

      //System.out.println("Starting with gameid: " + args[0]);
      //System.out.println("Starting with gameid: " + 1234);
      GameRunner game = new GameRunner();
      game.initialize("1406990789",level);
      game.start();

      Simulation simulation = new Simulation();
      simulation.initialize(game);
      simulation.doRefresh();
     
  }
}