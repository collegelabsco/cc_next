
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ include file="functions.jsp" %>

<%
	if(request.getMethod().equalsIgnoreCase("get")){
		response.sendRedirect("rescue.jsp");
		return;	
	}
	String NOT_ALLOWED_SPECIAL_CHARS_FOR_EMAIL = "|;$%\\'\"<>()\r\n";
	String NOT_ALLOWED_SPECIAL_CHARS_FOR_PWD = "|;%\\'\"<>()\r\n";	
	 
     java.util.UUID uid = (java.util.UUID)session.getAttribute("greeting");
     java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");     
     
	 String email = null, password = null, hidden_uid = null, hidden_sessionID = null;
	 boolean success = false, insertValue = false, isEmailExist = false;
	 try {		
		if(request.getParameter("email") != null) {
			email = request.getParameter("email");
			if(strHasSpecChar(NOT_ALLOWED_SPECIAL_CHARS_FOR_EMAIL, email)) {
				response.sendRedirect("error.jsp");
				return;
			}
		}
			
		if(request.getParameter("password") != null) { 
			password = request.getParameter("password");			
			if(strHasSpecChar(NOT_ALLOWED_SPECIAL_CHARS_FOR_PWD, password)) {
				response.sendRedirect("error.jsp");
				return;
			}
			password = encryptMessage(password, 10);
		}
		
		if(email == null || password == null){
			response.sendRedirect("rescue.jsp");
			return;
		}
		//System.out.println("here1");
		if(request.getParameter("from_login1") != null) { 
			hidden_uid = request.getParameter("from_login1");			
		}
		if(request.getParameter("from_login2") != null) { 
			hidden_sessionID = request.getParameter("from_login2");
		}		
		if(hidden_uid == null || !uid.toString().equals(hidden_uid)) {
			response.sendRedirect("rescue.jsp");
			return;
		}		
		if(hidden_sessionID == null || !sessionID.toString().equals(hidden_sessionID)) {
			response.sendRedirect("rescue.jsp");
			return;
		}	
		
		int check_user_id = checkLogin(email, password);
		//out.println(check_user_id);
		if (check_user_id == 1) {                    
            session.setAttribute("login","Login Successful");			
			success = true;            
        }else{
            session.setAttribute("login","You are not a registered user");           
			success = false;
        }
			
	} catch(Exception e)
		{

		}
	
%>
<html>
<body>
<p>
	<center>
		
        <% if (success) {
        	java.util.UUID ob = java.util.UUID.randomUUID();        	
            session.setAttribute("loginemail",email);
            session.setAttribute("greeting",ob);
			//out.println("window.location.href='simulation.jsp'");
			%>
			<form method="post" action="<%=response.encodeURL("simulation.jsp")%>" name="login_submit_frm">
			<INPUT TYPE="hidden" NAME="from_login1" value="<%=ob.toString() %>"/>"
			<INPUT TYPE="hidden" NAME="from_login2" value="<%=sessionID%>"/>"
			</form>
			<script type="text/javascript">
				document.login_submit_frm.submit();
			</script>			
       
        <% } else {
				response.sendRedirect("login_error.jsp");
			return;
		}
		%>
    </center>
</p>
</body>
</html>

