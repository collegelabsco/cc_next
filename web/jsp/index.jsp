<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to Code Champion</title><style type="text/css">
<!--
body {
	margin-top: 5px;
	background-color: #FFFFFF;
    }
-->
</style>
<link href="CSS/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #f87c0c}
-->
</style>


<script type="text/javascript">

      function callSubmitAction() {
			var flag = true;
			if(document.form1.email.value == "" || document.form1.password.value == "" || document.form1.email.value == "University email-ID only"){
                alert("Please fill both username and password");
                document.form1.focus();
                flag = false;
            }
			var emailID=document.form1.email;

			if (flag && echeck(emailID.value)==false){
				emailID.value=""
				emailID.focus()
				flag = false;
			}
				
			if(flag) {
				document.form1.action="login_submit.jsp";
				document.form1.submit(); 	
			}
			
           
        }
		function echeck(str) {

		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

 		 return true;				
	}

function ValidateForm(){
	var emailID=document.form1.email
	
	if ((emailID.value==null)||(emailID.value=="")){
		alert("Please Enter your Email ID")
		emailID.focus()
		return false
	}
	if (echeck(emailID.value)==false){
		emailID.value=""
		emailID.focus()
		return false
	}
	
 }

function submitEnter(myfield,e)
{
	var keycode;
	if (window.event) keycode = window.event.keyCode;
	else if (e) keycode = e.which;
	else return true;
	
	if (keycode == 13)
	   {
		   return !callSubmitAction();
		   
	   }
	else
	   return true;
}

</script>
<%  
	
	UUID uid = UUID.randomUUID();
	session.setAttribute("greeting",uid);
	UUID sessionID = UUID.randomUUID();
	session.setAttribute("sessionCheck",sessionID);	
	
%>
</head>

<body>

 <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
      <td colspan="3" background="images/top_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
  </tr>


     <tr>     
       <td colspan="3" valign="top" height="20px" bgcolor="#FFFFFF" align="center">
      </br>
        <a class="redtext2" href="enablejs.jsp" ><noscript>Your browser does not support JavaScript, please enable JavaScript. Click here for support.</noscript></a><br />
  	</td>
   </tr>

   <tr><td></td></tr>
     <tr bgcolor="#FFFFFF"><td><b><font size=+1>Welcome to The Infosys Code Champion Contest 2013</font></b></td></tr>
     <tr bgcolor="#FFFFFF"><td>Participants would need to enter their Infosys email address and click on submit button to register for the contest. Login credentials will be sent to the registered email address.<br><br></td></tr>
     <tr bgcolor="#FFFFFF"><td>Infoscions who would like to opt for Mainframe technologies in the Infosys Code Champion Contest, would first need to participate in the preliminary round consisting of objective assessment in programming related topics. The final round however will be a programming contest in Mainframe Technologies. The preliminary round will be opened on December 4th, 2013.<br><br></td></tr>
     <tr bgcolor="#FFFFFF"><td>Infoscions who opt for technologies other than Mainframes would need to appear for a programming contest both in the preliminary and the final round. The programming solution(s) can be written in Java, C#.Net, C or C++. A few sample programming questions can be accessed <a href="http://107.23.11.198/openpbt/codechampion/login.jsp" target=_blank>here</a>.<br><br></td></tr>

       <br><br>
  </table>

 <form id="form1" name="form1" method="post" action="<%=response.encodeURL("login_submit.jsp")%>">
     <table align="center" >

                 <tr>
                  <td>UserName</td>
                 <td class="formtext2"  >

                         <input title="Use University Email ID only"   name="email" type="text" id="textfield1" size="25" value="Email-ID" onfocus="if(this.value=='Email-ID'){this.value='';}"/>

                         <INPUT TYPE="hidden" NAME="from_login1" value="<%=uid.toString() %>"/>
                         <INPUT TYPE="hidden" NAME="from_login2" value="<%=sessionID.toString() %>"/>
                 <td>
                 </tr>

                 <tr>
                     <td>Password</td>
                     <td height="30" class="formtext2">

                         <input name="password" type="password" id="textfield" size="25" onkeypress="return submitEnter(this,event)"/>
                     </td>
                 </tr>

                 <tr>
                     <td></td>
                     <td  align="center" valign="top"><a href="#"><img src="images/login_bt.jpg" height="20" border="0" class="rightimgpad2" onclick="callSubmitAction()" type="submit"/></a></td>
                 </tr>
     </table>



 </form>

  <br>
 <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">

     <tr>
         <td colspan="3" bgcolor="#ff6600"><img src="images/spacer.gif" width="1" height="3" /></td>
     </tr>
 </table>


</body>
</html>


