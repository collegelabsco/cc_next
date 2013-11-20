package com.devsquare.cc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.devsquare.cc.connection.User;
import com.devsquare.cc.log.Log;
import com.devsquare.fts.DBConnection;


public class DBMgr {
	
	private static DBMgr SINGLETON = new DBMgr();
	
	public PreparedStatement INSERT_RECORD = null;
	
	
	private DBMgr() {}
	
	public static DBMgr get(){
		return SINGLETON;
	}
	
	public void init() throws Exception{
		/**
		 * Health check up
		 */
		Log.info("Setting DB connection..");
		Connection conn = DBConnection.getConnection();
		DBConnection.closeConnection(conn);
		
	}
	
	
	public User fetchUser(String sessionkey) throws Exception{
		
		Connection conn = DBConnection.getConnection();
		User u = null;
		try{
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT email FROM game_rescue where sessionkey="+sessionkey);
			if(rs.next()){
				u = new User();
				u.setToken(sessionkey);
				u.setEmail(rs.getString("email"));
			}
			
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return u;
		
		
	}
	
	public void executeLoggerInsert(String email, String sessionkey,int level,String type, String time) throws Exception{
		
		Connection conn = DBConnection.getConnection();
		try{
			Statement stm = conn.createStatement();
			stm.executeUpdate("INSERT INTO rescue_logger (email,sessionkey,level, type, time) VALUES ('"+email+"','"+sessionkey+"',"+level+",'"+type+"','"+time+"')");
			
		}finally{
			DBConnection.closeConnection(conn);
		}
		
	}
	
	public void executeResult(String sessionkey,int level,int score,String time) throws Exception{
		
		Connection conn = DBConnection.getConnection();
		try{
			Statement stm = conn.createStatement();
			stm.executeUpdate("INSERT INTO rescue_scores (sessionkey,level,score, time) VALUES ('"+sessionkey+"',"+level+","+score+",'"+time+"')");
			
		}finally{
			DBConnection.closeConnection(conn);
		}
	}
	
	

}
