package com.devsquare.cc.mgr;

import java.util.HashMap;
import java.util.Map;

import com.devsquare.cc.connection.User;

@Deprecated
public class UserManager {
	
	private static UserManager um = new UserManager();
	
	Map<String, User> userMap = new HashMap<String, User>();
	
	public static UserManager getMgr(){
		return um;
	}
	
	public void putUser(User user){
		
		userMap.put(user.getToken(), user);
		
	}
	
	public User getUser(String token){
		return userMap.get(token);
	}

}
