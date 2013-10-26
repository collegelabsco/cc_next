
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> 

<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.sql.Connection" %> 

<html> 
<head> 
<title>Connection with mysql database</title> 
</head> 

<body>
<h1>Connection status </h1>
<% 
	try {
	/* Create string of connection url within specified format with machine name, 
	port number and database name. Here machine name id localhost and 
	database name is usermaster. */ 
	String connectionURL = "jdbc:mysql://localhost:3306/fts_game"; 

	// declare a connection by using Connection interface 
	Connection connection = null; 
	Statement stmt = null;
	ResultSet rst = null;

	// Load JBBC driver "com.mysql.jdbc.Driver" 
	Class.forName("com.mysql.jdbc.Driver").newInstance(); 

	/* Create a connection by using getConnection() method that takes parameters of 
	string type connection url, user name and password to connect to database. */ 
		// check weather connection is established or not by isClosed() method 
	connection = DriverManager.getConnection(connectionURL, "root", "root");
	if(!connection.isClosed()) {
		

		out.println("Successfully connected to " + "MySQL server using TCP/IP...");
		
		stmt = connection.createStatement();
		rst = stmt.executeQuery("select * from scores");

		%>
		<center>
		<h2>Scores</h2>
		<table border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td><b>Game id</b></td>
			<td><b>level</b></td>
			<td><b>steps</b></td>
			<td><b>bonus</b></td>
			<td><b>time</b></td>
		</tr>
		<%
		
		while(rst.next()){
		%>
			<tr>
			  <td><%=rst.getString("gameid")%></td>
			  <td><%=rst.getInt("level")%></td>
			  <td><%=rst.getInt("steps")%></td>
			  <td><%=rst.getInt("bonus")%></td>
			  <td><%=rst.getString("timecapture")%></td>
			</tr>
			</table>
		</center>

		<% }
		rst.close();
		stmt.close();
		connection.close();	

	}
}

     catch (SQLException e) {
         System.out.println("Error occurred " + e);
     }
%>
	
	
	
	



</body> 
</html> 
