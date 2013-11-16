<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.util.*" 
		import="java.io.*"
		import="java.text.*"%>

<%
	String email = null;
	UUID uid = (UUID) session.getAttribute("greeting");
	UUID sessionID = (UUID) session.getAttribute("sessionCheck");
	if(session.getAttribute("loginemail")!=null)
	{
		email = (String)session.getAttribute("loginemail");
	}

%>

<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Contact Us</title>
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
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_nbGroup(event, grpName) { //v6.0
  var i,img,nbArr,args=MM_nbGroup.arguments;
  
  if (event == "init" && args.length > 2) {
    if ((img = MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : ((args[i+1])? args[i+1] : img.MM_up);
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
    for (i=0; i < document.MM_nbOver.length; i++) {
      img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }
  } else if (event == "down") {
	  document.from_contact.submit();
    nbArr = document[grpName];    
    if (nbArr)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  }    
    } 
}
//-->
</script>
</head>
<%System.out.println("sessionID in contact_us: " + sessionID); %>
<body onload="MM_preloadImages('images/home_over.jpg','images/my_space_me_over.jpg','images/communities_over.jpg','images/knowledge_over.jpg','images/news_events_over.jpg','images/careers_over.jpg','images/contact_over.jpg')">
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">

<form method="post" action="simulation.jsp" name="from_contact">  
	 <INPUT TYPE="hidden" NAME="from_login1" value="<%=uid.toString() %>">
	 <INPUT TYPE="hidden" NAME="from_login2" value="<%=sessionID.toString()%>">	 
</form>

	
  <tr>
    <td colspan="3" background="images/top_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
  </tr>
  <tr>
    <td width="148" rowspan="2" align="center" bgcolor="#FFFFFF"><img src="images/instep_logo.jpg" width="106" height="85" /></td>
    <td height="54" colspan="2" align="right" valign="middle" bgcolor="#000000" class="rightimgpad"><img src="images/infosys_logo.jpg" width="70" height="29"  /></td>
  </tr>
  <tr>
  <td height="30" background="images/menu_bg2.jpg" class="signin2">An Infosys Online Game Programming Contest</td>
    <td height="30" align="right" background="images/menu_bg2.jpg">
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><a href="javascript:document.from_contact.submit()" target="_top" onclick="MM_nbGroup('down','group1','home','images/home_over.jpg',1)" onmouseover="MM_nbGroup('over','home','images/home_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/home.jpg" alt="" name="home" width="91" height="30" border="0" id="home" onload="" /></a></td>
        <td><a href="javascript:;" target="_top" onclick="MM_nbGroup('down','group1','menudivider','',1)" onmouseover="MM_nbGroup('over','menudivider','','',1)" onmouseout="MM_nbGroup('out')"><img src="images/menu_divider.jpg" alt="" name="menudivider" width="2" height="30" border="0" id="menudivider" onload="" /></a></td>
        <td><a href="detailed_Instructions.html" target="_blank" onclick="MM_nbGroup('down','group1','moreinfo','images/more_info.jpg',1)" onmouseover="MM_nbGroup('over','moreinfo','images/more_info_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/more_info.jpg" alt="" name="moreinfo"  border="0" id="myspaceme" onload="" /></a></td>
        <td><a href="javascript:;" target="_top" onclick="MM_nbGroup('down','group1','menudivider3','',1)" onmouseover="MM_nbGroup('over','menudivider3','','',1)" onmouseout="MM_nbGroup('out')"><img src="images/menu_divider.jpg" alt="" name="menudivider3" width="2" height="30" border="0" id="menudivider3" onload="" /></a></td>
        <td><a href="#" target="_top" onclick="MM_nbGroup('down','group1','contact','images/contact_over.jpg',1)" onmouseover="MM_nbGroup('over','contact','images/contact_over.jpg','',1)" onmouseout="MM_nbGroup('out')"><img src="images/contact_over.jpg" alt="" name="contact" width="99" height="30" border="0" id="contact" onload="" /></a></td>
        </tr>
    </table>
    </td>
  </tr>
  
  <script language="JavaScript" type="text/JavaScript">


function isValidEmail(contact) {
    var str = contact;
    var valid = true
    var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    if (!filter.test(str))
    {
        valid = false;
    }
    return valid;
}

String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); };
function validate_registration_form() {

    if (document.registration.name.value.trim() == "") {
		alert("Please enter your first name");
		document.registration.name.focus();
		return false;
	}	
	else if (document.registration.contact.value == "") {
        alert("Please enter your email ID");
        document.registration.contact.focus();
        return false;
    }
    else if (document.registration.subject.value == "") {
        alert("Please enter subject line");
        document.registration.subject.focus();
        return false;
    } 
    else if (document.registration.comments.value == "") {
        alert("No body !!!");
        document.registration.comments.focus();
        return false;
    }        
	else if (isValidEmail(document.registration.contact.value.trim()) == false) {
		alert("Please enter a valid 'email id'");
		document.registration.contact.focus();
		return false;
    }
	else
		return true;
}
</script>      
		
  

    <tr>
      <td colspan="3" background="images/but_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
    </tr>
    <tr>
      <td colspan="3" align="center" bgcolor="#FFFFFF"><table width="912" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="39" align="left" background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
            <p class="subheader2">Contact Us</p>
          <!-- InstanceEndEditable --></td>
		   <td height="39"  background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
		  <%if(email!=null){ %><p class="bluetextsub2"><%=email%> | <A HREF="logout.jsp" class="bluetext">Logout</A></p><%} %></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF">&nbsp;</td>
      <td width="384" bgcolor="#FFFFFF">&nbsp;</td>
      <td width="428" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" valign="top" bgcolor="#FFFFFF" class="leftimgpad"><!-- InstanceBeginEditable name="EditRegion1" -->
      <form id="form2" name="registration" method="post" action="feedback_submit.jsp">
        <table width="50%" border="0" cellpadding="0" cellspacing="0" bgcolor="#efefef">
          
          
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="138" height="30" valign="top"><span class="username">Name</span></td>
            <td width="342" height="30">
              <input name="name" type="text" class="formtext2" id="textfield2" size="40" />                        </td>
          </tr>
          
          <tr>
            <td height="25" valign="top"><span class="username">Email</span><span class="redtext"></span></td>
            <td>
                <input name="contact" type="text" class="formtext2" id="textfield" size="40" />                       </td>
          </tr>
          
          <tr>
            <td height="30" valign="top"><span class="username">Subject</span><span class="redtext"></span></td>
            <td>
              <input name="subject" type="text" class="formtext2" id="textfield3" size="40" />                       </td>
          </tr>
          
          <tr>
            <td height="30" valign="top"><span class="username">Areas</span><span class="redtext"></span></td>
            <td>    
              <select name="topic" class="formtext2" id="select">
                <option value="General Queries">General Queries</option>
                <option value="Report Bugs">Report Bugs</option>
              </select>                 
            </td>
          </tr>
          
          <tr>
            <td height="25" valign="top"><span class="username">Comments</span><span class="redtext"></span></td>
            <td>
                <textarea name="comments" cols="45" rows="5" class="formtext2" id="textarea"></textarea>                         </td>
          </tr>
          
          
          
          <tr>
            <td height="40">            </td>
            <td>
                <%-- <input name="button" type="submit" id="button" value="Submit" onclick="return validate_registration_form()"/> --%>
                <INPUT name="button" id="button" TYPE="image" SRC="images/submit_bt.jpg" onclick="return validate_registration_form()"> </td>
          </tr>
          <tr>
            <td></td>
            <td>&nbsp;</td>
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
