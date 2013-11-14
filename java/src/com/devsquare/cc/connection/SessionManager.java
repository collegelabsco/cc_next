package com.devsquare.cc.connection;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	
	private static SessionManager sm = new SessionManager();
	
	Map<String, User> userMap = new HashMap<String, User>();
	
	public static SessionManager getInstance(){
		return sm;
	}
	
	public User getUser(String token){
		return userMap.get(token);
	}
	
	public void addUser(String token,User user){
			userMap.put(token, user);
	}

}
