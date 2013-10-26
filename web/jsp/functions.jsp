<%@ page import="java.io.*" %> 
<%@ page import="java.util.regex.*" %>
<%@ include file="constants.jsp" %>  
<%!
	private static String countUserId = "select email,pwd from game_rescue where email = ? and pwd = ?";
	private static String checkUserExistence = "select email from game_rescue where email = ?";
	private static String insertLogDetails = "insert into rescue_logger(email,gameid,time) values (?,?,?)";
	private static String insertUserDetails = "insert into game_rescue(fname,lname,universityid,email,pwd,university,academic,sem) values (?,?,?,?,?,?,?,?)";
	private static String get_password = "select pwd from game_rescue where email = ?";
	
	public static int checkUserId(String email) {
		
        int exp = -1; 
        int key = 10;
        PreparedStatement pstmt = null;
        Connection con = null;
        String eid = null;
		String passwrd = null;
		ResultSet rst = null;
        try {
        	Class.forName(driverName).newInstance();
        	con = DriverManager.getConnection(connectionString, db_username, db_password);		
            pstmt = con.prepareStatement(checkUserExistence);
            pstmt.setString(1, email);			
            
            rst = pstmt.executeQuery();
            while(rst.next()) {
				
                eid = rst.getString("email");
				//passwrd = rst.getString("pwd");				
				
				if(eid.equalsIgnoreCase(email)) {
					exp = 1;
					break;
				}
            }

						
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {				
                if (rst != null) rst.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return exp;
    }
	public static boolean insertLogDetails(String email, String gameid, String time) {	   
	    PreparedStatement pstmt = null;
	    boolean flag = false;
	    Connection con = null;
	    try {
	    	Class.forName(driverName).newInstance();
        	con = DriverManager.getConnection(connectionString, db_username, db_password);		
	        pstmt = con.prepareStatement(insertLogDetails);
	        pstmt.setString(1, email);
	        pstmt.setString(2, gameid);
	        pstmt.setString(3, time);
	               
	        pstmt.executeUpdate();
	        flag = true;
	
			//DBConnection.closeConnection(con);
			
	    } catch (Exception e) {
	        System.out.println("Exception ariser: " + e);
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	       
	    }
	    return flag;
	}
	
	private static String encryptMessage(String msg, int k) {
	      String result = "";    	
	      for (int i = 0; i < msg.length(); i++)
	          result += encryptChar(msg.charAt(i), k);
	      return result;
	  } 
	  private static char encryptChar(char c, int k) {
		  if (c+k <= 126)
			  return (char) (c+k);
		  else 
			  return (char) (c+k-94);
	  }
	  private static String decryptMessage(String msg, int k) {
	      String result = "";    	
	      for (int i = 0; i < msg.length(); i++)
	          result += decryptChar(msg.charAt(i), k);
	      return result;
	  }
	 
	  private static char decryptChar(char c, int k) {
		  if (c-k >= 33)
			  return (char) (c-k);
		  else 
			  return (char) (c+94-k);
	  }
	  public static int checkExistence(String email) {
	        int exp = -1;
	        Connection con = null;
	        PreparedStatement pstmt = null;
	        String eid = null;
			ResultSet rst = null;
	        try {
	        	Class.forName(driverName).newInstance();
	        	con = DriverManager.getConnection(connectionString, db_username, db_password);
	            pstmt = con.prepareStatement(checkUserExistence);
	            pstmt.setString(1, email);	            
	            rst = pstmt.executeQuery();
	            while(rst.next()) {
					//System.out.println(rst.getInt(1));
	                eid = rst.getString("email");
					if(eid.equalsIgnoreCase(email)) {
						exp = 1;
						break;
					}
	            }

							
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
					//DBConnection.closeConnection(con);

	                if (rst != null) rst.close();
	                if (pstmt != null) pstmt.close();
	                if (con != null) con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	        }
	        return exp;
	    }
	    public static boolean insertRegisteredUsers(String f_name, String l_name, String universityid, String email, String password, String university, String academia, int sem) {
	        
	        PreparedStatement pstmt = null;
	        boolean flag = false;
	        Connection con = null;
	        try {
	        	Class.forName(driverName).newInstance();
	        	con = DriverManager.getConnection(connectionString, db_username, db_password);
	            pstmt = con.prepareStatement(insertUserDetails);
	            pstmt.setString(1, f_name);
	            pstmt.setString(2, l_name);
	            pstmt.setString(3, universityid);
	            pstmt.setString(4, email);
	            pstmt.setString(5, password);
	            pstmt.setString(6, university);
	            pstmt.setString(7, academia);
	            pstmt.setInt(8, sem);           
	            pstmt.executeUpdate();
	            flag = true;

				//DBConnection.closeConnection(con);
				
	        } catch (Exception e) {
	            System.out.println("Exception ariser" + e);
	            e.printStackTrace();
	        } finally {
	            try {
	                if (pstmt != null) pstmt.close();
	                if (con != null) con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	           
	        }
	        return flag;
	    }
	    public static boolean strHasSpecChar(String notAllowedChars, String str) {
	    	boolean isStrHasSpecChar = false;

	    	Pattern p = Pattern.compile(buildPattern(notAllowedChars));

	    	Matcher m = p.matcher(str);

	    	if(m.find())
	    	{
	    		isStrHasSpecChar = true;
	    	}
	    	return isStrHasSpecChar;
	    }
	    public static String buildPattern(String notAllowedChars)
	    {
	    	StringBuffer pattern = new StringBuffer();	
	    	Character c = null;	
	    	pattern.append("[") ;	
	    	int strLen = notAllowedChars.length();	
	    	for(int i = 0; i < strLen; i++ )	{
	    		c = Character.valueOf(notAllowedChars.charAt(i));		
	    		if( (c.charValue() == '"') || (c.charValue() == '\\')) {
	    			pattern.append("\\");
	    		}		
	    		pattern.append(c);		
	    	}	
	    	pattern.append("]");	
	    	return pattern.toString();
	    }
	   
		
		public static String getPassword(String email) {
			String password = null;
			Connection con = null;
			PreparedStatement pstmt = null;        
	        ResultSet rst = null;
	        try {
	        	Class.forName(driverName).newInstance();
	        	con = DriverManager.getConnection(connectionString, db_username, db_password);
	        	pstmt = con.prepareStatement(get_password);
	        	pstmt.setString(1, email);
	        	rst = pstmt.executeQuery();
				while(rst.next()) {				
	                password = rst.getString("pwd");			
	            }
	        	
	        }
	        catch(Exception sql) {
	        	sql.printStackTrace();
	        }
	        finally {
	            try {				
	                if (rst != null) rst.close();
	                if (pstmt != null) pstmt.close();
	                if (con != null) con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	        }
	        return password;
		}
		
		public static int checkLogin(String email, String password) {
			
	        int exp = -1; 
	        int key = 10;
	        PreparedStatement pstmt = null;
	        Connection con = null;
	        String eid = null;
			String passwrd = null;
			ResultSet rst = null;
	        try {
	        	Class.forName(driverName).newInstance();
	        	con = DriverManager.getConnection(connectionString, db_username, db_password);		
	            pstmt = con.prepareStatement(countUserId);
	            pstmt.setString(1, email);			
	            pstmt.setString(2, password);
	            rst = pstmt.executeQuery();
	            while(rst.next()) {
					
	                eid = rst.getString("email");
					passwrd = rst.getString("pwd");				
					
					if(eid.equalsIgnoreCase(email) && passwrd.equalsIgnoreCase(password)) {
						exp = 1;
						break;
					}
	            }

							
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {				
	                if (rst != null) rst.close();
	                if (pstmt != null) pstmt.close();
	                if (con != null) con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	        }
	        return exp;
	    }
	   
%>