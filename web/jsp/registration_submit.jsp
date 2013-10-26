<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ include file="functions.jsp" %>

<%
	String connectionURL = null;
	String NOT_ALLOWED_IN_NAME = "[0-9][|;$%\\'\"<>()\r\n]";
	String NOT_ALLOWED_IN_PASSWORD = "|;$%\\'\"<>()\r\n";
	String NOT_ALLOWED_IN_UNIVERSITYID = "|;$%\\'\"<>()\r\n";
	String NOT_ALLOWED_IN_EMAILID = "|;$%\\'\"<>()\r\n";
	String NOT_ALLOWED_IN_UNIVERSITY = "[0-9][|;$%\\'\"<>()\r\n]";
	String NOT_ALLOWED_IN_SEMESTER = "[a-z][A-Z][|;$%\\'\"<>()\r\n]";
	
	
    String f_name = null, l_name = null;
    String universityID = null; String email = null, password = null, university = null, academia = null;
    String semester = null,hidden = null;
	String status = null;
    java.util.UUID uid = (java.util.UUID)session.getAttribute("greeting");

    boolean success = false, insertValue = false, isEmailExist = false;
    int key=10;
    try {
		
        if(request.getParameter("f_name") != null) {
            f_name = request.getParameter("f_name");
            if(strHasSpecChar(NOT_ALLOWED_IN_NAME,f_name)) {
            	response.sendRedirect("error.jsp");
            	return;
            }
        }
		if(request.getParameter("l_name") != null) {
			l_name = request.getParameter("l_name");
        	if(strHasSpecChar(NOT_ALLOWED_IN_NAME,l_name)) {
        		response.sendRedirect("error.jsp");
        		return;
       		}           
		}
		
		if(request.getParameter("universityID") != null) {
            universityID = request.getParameter("universityID");
            if(strHasSpecChar(NOT_ALLOWED_IN_UNIVERSITYID, universityID)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
		}

        if(request.getParameter("email") != null) {
            email = request.getParameter("email");
            if(strHasSpecChar(NOT_ALLOWED_IN_EMAILID, email)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
        }
		if(request.getParameter("password") != null) {
            password = request.getParameter("password");
            if(strHasSpecChar(NOT_ALLOWED_IN_PASSWORD, password)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
            password = encryptMessage(password, key);
		}
		if(request.getParameter("university") != null) {
            university = request.getParameter("university");
            if(strHasSpecChar(NOT_ALLOWED_IN_UNIVERSITY, university)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
		}

		if(request.getParameter("academia") != null) {
            academia = request.getParameter("academia");
            if(strHasSpecChar(NOT_ALLOWED_IN_UNIVERSITY, academia)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
		}
        
        if (request.getParameter("semester") != null) {
            semester = request.getParameter("semester");
            if(strHasSpecChar(NOT_ALLOWED_IN_SEMESTER, semester)) {
            	response.sendRedirect("error.jsp");
        		return;
            }
        }
		int sem = Integer.parseInt(semester);
		
		if(request.getParameter("from_registration") != null) { 
			hidden = request.getParameter("from_registration");
		}	
		if(!uid.toString().equals(hidden)) {
			response.sendRedirect("rescue.jsp");
			return;
		}
		
		int check_user_id = checkExistence(email);
		
        if (check_user_id == -1) {
            
            insertValue = insertRegisteredUsers(f_name, l_name, universityID, email, password, university, academia, sem);
            session.setAttribute("userExist","You have successfully registered, Use the same Email id and password to Login");
			
			success = insertValue;
			if(insertValue) {				
				status = "You are successfully registered, use the same email id and password to login\n";
			}
            
        }else{
            session.setAttribute("userExist","You have already registered for the contest.");
            insertValue = true;
            isEmailExist = true;
			success = false;
			status = email + " is already registered for the contest\n";
        }
	} catch(Exception e)
		{

		}
%>

<html>
<head>
<title>Registration</title>
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
<p>
<center>
        <%
			java.util.UUID ob = java.util.UUID.randomUUID();
            session.setAttribute("greeting",ob);
			//out.println("window.location.href='simulation.jsp'");
			%>
			<form  method="post" action="<%=response.encodeURL("rescue.jsp")%>" name="registration_submit_frm">
				<INPUT TYPE="hidden" NAME="from_registration" value="<%=ob.toString()%>"/>
			</form>
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
		            <p class="subheader"></p>
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
		
		      <td colspan="3" valign="center" bgcolor="#FFFFFF" class="leftimgpad"><!-- InstanceBeginEditable name="EditRegion1" -->
			  
		        <table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bgcolor="#efefef">
		          <tr>
		            <td height="30" colspan="2" bgcolor="#c2c2c2" class="sideblue">Registration</td>
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
		  <tr>
		    <td height="43" colspan="2" background="images/bott_bg.jpg" ><a class="copyrights2" href="#">Sitemap</a></td>
		    <td background="images/bott_bg.jpg" class="rightimgpad"><img src="images/rss.jpg" width="37" height="14"  /></td>
		  </tr>
		 <tr>
			    <td height="39" colspan="2" bgcolor="#FFFFFF" class="normaltext3">Copyrights ©2010 Infosys Technologies Limited</td>
			    <td bgcolor="#FFFFFF"><a class="copyrights" href="#">Privacy Statement</a> | &nbsp;&nbsp;<a class="copyrights" href="#">Safe Harbor Provision</a> | &nbsp;&nbsp;<a class="copyrights" href="#">Terms of use</a> | &nbsp;&nbsp;<a class="copyrights" href="#">Trademarks</a></td>
		  	</tr>
		</table>
			
		
        <!--You have successfully registered, Login details are sent to your email address.-->
        
    </center>
</p>
<br>
<br>

</body>
</html>
