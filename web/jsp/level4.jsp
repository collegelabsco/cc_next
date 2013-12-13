<%@ page import="java.util.*" 
		import="java.io.*"
		import="java.text.*"
		import="java.sql.*"%>
<%@ include file="functions.jsp" %>

<%--
  Created by IntelliJ IDEA.
  User: administrator
  Date: 21 Dec, 2009
  Time: 10:26:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage = "error.jsp" %>
<%	

//System.out.println("sim: here1");
/*
java.util.UUID uid = (java.util.UUID)session.getAttribute("greeting");
java.util.UUID sessionID = (java.util.UUID)session.getAttribute("sessionCheck");
if(uid == null) {
	response.sendRedirect("index.jsp");
	return;	
}
if(sessionID==null) {
	response.sendRedirect("index.jsp");
	return;
}

*/



    

    Random rand = new Random();
	double gid = 1000000000+Math.random()*999999999;
	String sessionkey = Integer.toString((int)gid);

	SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	java.util.Date date = new java.util.Date ();
	String dateStr = dateFormat.format (date);
	Connection con = null; 
	
	
	String hidden_sessionID = null, hidden_uid = null;	
	String email = null;
	
//	hidden_uid = request.getParameter("from_login1");
//	hidden_sessionID = request.getParameter("from_login2");
	
/*
	if(!uid.toString().equals(hidden_uid)) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	if(!sessionID.toString().equals(hidden_sessionID)) {
		response.sendRedirect("index.jsp");
		return;
	}	
	
	java.util.UUID ob = UUID.randomUUID();
    session.setAttribute("greeting",ob); 
*/

    
    

	if(session.getAttribute("loginemail")!=null) {
		email = (String)session.getAttribute("loginemail");		
	}
	else {		
		response.sendRedirect("index.jsp");
		return;
	}
    /*
        System.out.println("sessionID: " + sessionID);

    */

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Level - Statement</title>
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
    nbArr = document[grpName];
    if (nbArr)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  } 
    document.form2.submit;
    }
}
//-->
</script>
</head>

<body onload="MM_preloadImages('images/home_over.jpg','images/contact_over.jpg','images/more_info_over.jpg')">
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3" background="images/top_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
  </tr>

    <tr>
      <td colspan="3" align="center" bgcolor="#FFFFFF"><table width="912" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="39" align="left" background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
            <p class="subheader">Welcome to Champion Contest 2013</p>
            <td height="39"  background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
		  <p class="bluetextsub2"><%=email%> | <A class="bluetext" HREF="logout.jsp" >Logout</A></p>
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
      <td colspan="3" bgcolor="#FFFFFF" class="leftimgpadt"><!-- InstanceBeginEditable name="EditRegion1" -->

<table width="975" border="0" cellpadding="0" cellspacing="0" bgcolor="#dddddd">
 
    <tr>
        <td height="40" class="subtitle22bb">Level 4 - QAD MapReduce</td>
    </tr>
    <tr>
        <td class="subtitle22b">
            <br><br>
            Given multiple files, analyse to get insights.
            <br><br>
        </td>
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
