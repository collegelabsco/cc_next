package com.devsquare.fts;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.*;

public class DBConnection
{

   public MysqlDataSource ds;

   String DB_SERVER="207.211.87.41";//Enter your database server name
   //String DB_SERVER="10.74.65.45";//Enter your database server name
   //String DB_SERVER="192.168.0.12";//Enter your database server name
   int DB_SERVER_PORT=3306; //Enter your database port number
   String DB_NAME="code4jobs"; //Enter your database name
   //String DB_NAME="devsquare"; //Enter your database name
   String USER_NAME="root"; //Enter your mysql username
   String USER_PASSWORD="dsroot123";//Enter your mysql password*/
   //String USER_PASSWORD="root";//Enter your mysql password*/


   public DBConnection() throws Exception {
      if (DB_NAME.length()==0 || USER_NAME.length()==0 || USER_PASSWORD.length()==0) {
         throw (new Exception("Please fill in the DB_NAME, USER_NAME and USER_PASSWORD."));
      }
      ds = new MysqlDataSource();
      ds.setServerName(DB_SERVER);
      ds.setPort(DB_SERVER_PORT);
      ds.setDatabaseName(DB_NAME);
      ds.setUser(USER_NAME);
      ds.setPassword(USER_PASSWORD);
   }

   static DBConnection SINGLETON = null;
   public static Connection getConnection() throws Exception {
      if (SINGLETON==null) {
         SINGLETON = new DBConnection();
      }

      try {
         return SINGLETON.ds.getConnection();
      } catch (Exception sqle) {
         System.out.println("Conn[DBConnection.getConnection]:" + sqle.toString());
         throw sqle;
      }
   }

   public static void closeConnection(Connection connection) {
      try {
         if (connection != null) {
            connection.close();
         }
      } catch (SQLException sqle) {
         System.out.println("Conn[DBConnection.Close]:" + sqle.toString());
      }

   }
}
