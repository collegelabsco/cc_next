<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="com.ncstudio.dataobjects.Constants" %>
<%@ page import="com.ncstudio.server.util.MailClient" %>
 
 <%@ page import="java.util.*" %>
<%
	UUID uid = UUID.randomUUID();
	session.setAttribute("greeting",uid);
    java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");
	if(sessionID == null) {
		response.sendRedirect("index.jsp");
		return;
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Forgot Password</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" --><!-- InstanceEndEditable -->
<link href="CSS/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	background-color: #e2e2e2;
}
-->
</style>
<script type="text/javascript" src="js/mouseAction.js"></script>
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
   
        <td><a href="<%=response.encodeURL("index.jsp")%>" target="_top" onclick="MM_nbGroup('down','group1','home','images/home_over.jpg',1)" onmouseover="MM_nbGroup('over','home','images/home_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/home.jpg" alt="" name="home" width="91" height="30" border="0" id="home" onload="" /></a></td>
        <td><a href="javascript:;" target="_top" onclick="MM_nbGroup('down','group1','menudivider','',1)" onmouseover="MM_nbGroup('over','menudivider','','',1)" onmouseout="MM_nbGroup('out')"><img src="images/menu_divider.jpg" alt="" name="menudivider" width="2" height="30" border="0" id="menudivider" onload="" /></a></td>
        <td><a href="detailed_Instructions.html" target="_blank" onclick="MM_nbGroup('down','group1','moreinfo','images/more_info_over.jpg',1)" onmouseover="MM_nbGroup('over','moreinfo','images/more_info_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/more_info.jpg" alt="" name="moreinfo"  border="0" id="myspaceme" onload="" /></a></td>
        <td><a href="javascript:;" target="_top" onclick="MM_nbGroup('down','group1','menudivider3','',1)" onmouseover="MM_nbGroup('over','menudivider3','','',1)" onmouseout="MM_nbGroup('out')"><img src="images/menu_divider.jpg" alt="" name="menudivider3" width="2" height="30" border="0" id="menudivider3" onload="" /></a></td>
        <td><a href="<%=response.encodeURL("contact_us.jsp")%>" target="_top" onclick="MM_nbGroup('down','group1','contact','images/contact_over.jpg',1)" onmouseover="MM_nbGroup('over','contact','images/contact_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/contact.jpg" alt="" name="contact" width="99" height="30" border="0" id="contact" onload="" /></a></td>
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

<script language="JavaScript" type="text/JavaScript">


function isInteger(s)
{
    var i;
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function isValidEmail(email) {
    var str = email;
    var valid = true;
    var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    if (!filter.test(str))
    {
        valid = false;
    }
    return valid;
}

String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); };
function validate_registration_form() {
	
    if (document.sendmail.email.value == "") {
        alert("Please enter your email id");
        document.registration.email.focus();
        return false;
    }
       
	else if (isValidEmail(document.sendmail.email.value.trim()) == false) {
		alert("Please enter a valid 'email id'");
		document.registration.email.focus();
		return false;
    }
	else
		return true;
}
</script>

      <td colspan="3" valign="center" bgcolor="#FFFFFF" class="leftimgpad"><!-- InstanceBeginEditable name="EditRegion1" -->
	  <form id="form2" name="sendmail" method="post" action="<%= response.encodeURL("sendmail_submit.jsp") %>">
        <table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bgcolor="#efefef">
          <tr>
            <td height="30" colspan="2" bgcolor="#c2c2c2" class="sideblue">Type your email ID here and your password will be sent</td>
          </tr>
          
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          
          <tr>
            <td height="30"><span class="username">Email ID</span><span class="redtext">*</span></td>
            <td>
              <label>
                <input name="email" color="#999999" type="text" class="formtext2" id="textfield3" size="40" value="University email-ID only" onfocus="if(this.value=='University email-ID only'){this.value='';}" onblur="if(this.value==''){this.value='University email-ID only';}"/>
              </label>
            </td>
          </tr> 
         
          </tr>
          
          
           <tr>
            <td height="40">            </td>
            <td>
                <input name="button" type="submit" id="button" value="Submit" onclick="return validate_registration_form()"/>              </td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#c2c2c2"><img src="images/spacer.gif" width="1" height="1" /></td>
          </tr>
        </table>
	</form> 
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
<!-- InstanceEnd --></html>
 


