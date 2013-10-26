<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
<%
java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");
String mtd = request.getMethod();
if(sessionID!=null) {%>
	<b>You are not a Registered User, Please Register.</b>	
<%} else {%>
	<b>You are not logged in, please sign in.</b>
<%}%>
</head>
<body>

</body>
</html>