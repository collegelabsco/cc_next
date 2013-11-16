
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="javax.mail.*" %> 
<%@ include file="functions.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String mtd = request.getMethod();
	if(mtd.equalsIgnoreCase("get")){
		response.sendRedirect("rescue.jsp");
		return;
	}
	java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");
	if(sessionID == null) {
		response.sendRedirect("rescue.jsp");
		return;
	}
	
	String email = null;
	String password = null;
	String status = null;
	if(request.getParameter("email")!=null)
		email = request.getParameter("email");
	if(email == null || email.trim().length()<=0) {
		response.sendRedirect("login_error.jsp");
		return;
	}
	//out.println(email);
	
	
	boolean success = false;
	
	try {
	     int userExist = checkUserId(email);
	     //out.println(userExist);
	     if (userExist == 1) {                    
	         session.setAttribute("userExist","");			
				success = true;            
	     }else{
	         session.setAttribute("userExist","You are not a registered user");           
				success = false;
	     }
	     //out.println(email);
	     if(success == true) {
	    	 password = getPassword(email);
	     }
	     if(password!=null) {
	    	 //out.println(password);
		 	 try {
		 		
		 	    password = decryptMessage(password, 10);
		 		boolean flag = false;
		 	    String userDetails = null; 	       
		        userDetails = "<br>Your Password is : <b> " + password + " </b>";        
		 	    String subject = "Infosys Game Programming Contest";   
		 	    
		 	    com.ncstudio.server.util.MailClient mclient = new com.ncstudio.server.util.MailClient();
		        String[] to = {email};        
		        String hostName = Constants.MAIL_HOST;
		        String from = Constants.MAIL_FROM;
		        String info = "";        
		        info += "<br>"+userDetails+"<br/>";
		        mclient.sendMail(hostName, to, (String[]) null, (String[]) null, from, subject, info);
		        
		    	status = "You will receive a mail containing your password\n";
		 	 } catch(Exception me) {
		 		 me.printStackTrace();
		 	 }
	    } else {
	    	//out.println("password not found\n");
	    	status = "You are not a registered user\n";
	    }
	 }
    catch (Exception e) {
        System.out.println("Error occurred: " + e);
    } 
        
 %>
<title>Forgot Password</title>
<link href="CSS/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	background-color: #e2e2e2;
}
-->
</style>
</head>
<body onload="MM_preloadImages('images/home_over.jpg','images/my_space_me_over.jpg','images/communities_over.jpg','images/knowledge_over.jpg','images/news_events_over.jpg','images/careers_over.jpg','images/contact_over.jpg')">
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3" background="images/top_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
  </tr>
  <tr>
    <td width="148" rowspan="2" align="center" bgcolor="#FFFFFF"><img src="images/instep_logo.jpg" width="106" height="85" /></td>
    <td height="54" colspan="2" align="right" valign="middle" bgcolor="#000000" class="rightimgpad"><img src="images/infosys_logo.jpg" width="70" height="29"  /></td>
  </tr>
  
  
  <tr>
     <td height="30" background="images/menu_bg2.jpg" class="signin2">An Infosys Online Game Programming Contest</td>

    <td height="30"  align="right" background="images/menu_bg2.jpg">
    <table border="0" cellpadding="0" cellspacing="0">
   
      <tr>
   
        
        </tr>
    </table>
    </td>
  </tr>

    <tr>
      <td colspan="3" background="images/but_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
    </tr>
    <tr>
      <td colspan="3" align="center" bgcolor="#FFFFFF"><table width="912" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="39" align="left" background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
            <p class="subheader">Password Retrieval</p>
          <!-- InstanceEndEditable --></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF">&nbsp;</td>
      <td width="384" bgcolor="#FFFFFF">&nbsp;</td>
      <td width="428" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>
    <tr>

</script>

      <td colspan="3" valign="center" bgcolor="#FFFFFF" class="leftimgpad"><!-- InstanceBeginEditable name="EditRegion1" -->
	  
        <table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bgcolor="#efefef">
          <tr>
            <td height="30" colspan="2" bgcolor="#c2c2c2" class="sideblue">Forgot Password</td>
          </tr>
       
          <tr>
            <td height="30" align="center"><span class="username"><%=status %></span></td>
            
          </tr>
          <tr>
                <td align="center" height="40">
				<span class="username"> 
				<a href="rescue.jsp">Go back to Main Page</a></span></td>
          </tr>
           
          <tr>
            <td colspan="2" bgcolor="#c2c2c2"><img src="images/spacer.gif" width="1" height="1" /></td>
          </tr>
        </table>
	
      <!-- InstanceEndEditable --></td>
  </tr>   
  
 
  <tr>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" bgcolor="#ff6600"><img src="images/spacer.gif" width="1" height="3" /></td>
  </tr>
</table>
</body>
</html>

