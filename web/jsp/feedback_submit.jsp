<%@ page import="com.ncstudio.dataobjects.Constants" %>
<%@ page import="com.ncstudio.server.util.MailClient" %>
<html>
<head>
<title>Feedback</title>
<link href="CSS/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	background-color: #e2e2e2;
}
-->
</style>
<%  
	String mtd = request.getMethod();
	if(mtd.equalsIgnoreCase("get")){
		response.sendRedirect("index.jsp");
		return;
	}
	java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");
	if(sessionID == null) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	String name=null,contact=null,subject=null,topic=null,comments=null;
	boolean insertValue = true;
	if(request.getParameter("name")!=null)
		name = request.getParameter("name");
	if(request.getParameter("contact")!=null)
		contact = request.getParameter("contact");
	if(request.getParameter("subject")!=null)
		subject = request.getParameter("subject");	
	if(request.getParameter("topic")!=null)
		topic = request.getParameter("topic");
	if(request.getParameter("comments")!=null)
		comments = request.getParameter("comments");
	
	if(name==null || contact==null || subject==null || topic==null || comments==null) {
		response.sendRedirect("index.jsp");
		return;	
	}
	String status = null;

	try {
	    //UserManager um = AppServer.getUserManager();
	    //boolean exists = um.doesUserExist(email);	    
	    boolean flag = false;
	    String userDetails = null;
	    String[] gen_q = {"Satyanarayana_Ve151@infosys.com"};
	    String[] rep_b = {"zohaib@devsquare.com"};
        userDetails = "<br>Name : <b> " + name + " </b>";
        userDetails += "<br/><br>Email ID :<b> " + contact + " </b>";
        userDetails += "<br><br>";
	       
	    
        MailClient mclient = new MailClient();
        String[] to = null;
        if(topic.equalsIgnoreCase("General Queries"))
        	to = gen_q;
        else if(topic.equalsIgnoreCase("Report Bugs"))
        	to = rep_b;
        String hostName = Constants.MAIL_HOST;
        String from = Constants.MAIL_FROM;
        String info = "<br>";      
        info += "<br/>" + comments;
        info += "<br>"+userDetails+"<br/>";
        mclient.sendMail(hostName, to, (String[]) null, (String[]) null, from, subject, info);
        status = "Thanks for your feedback\n";

	   
    } catch (Throwable e) {
	    e.printStackTrace();
		status = "Error in submitting your feedback, please try again later.\n";
	}

%>
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
            <td height="30" colspan="2" bgcolor="#c2c2c2" class="sideblue">Feedback Submit</td>
          </tr>
       
          <tr>
            <td height="30" align="center"><span class="username"><%=status %></span></td>
            
          </tr>
          <tr>
                <td align="center" height="40">
				<span class="username"> 
				<a href="index.jsp">Go back to Main Page</a></span></td>
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
</head>
</html>
