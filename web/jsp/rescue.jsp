<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to InStep</title><style type="text/css">
<!--
body {
	margin-top: 5px;
	background-color: #e2e2e2;
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
      <td width="148" rowspan="2" align="center" bgcolor="#FFFFFF"><img src="images/instep_logo.jpg" width="106" height="85" /></td>
      <td height="54" colspan="2" align="right" valign="middle" bgcolor="#000000" class="rightimgpad"><img src="images/infosys_logo.jpg" width="70" height="29"  /></td>
    </tr>
    <tr>
      <td height="30" colspan="2" background="images/menu_bg2.jpg" class="signin2">An Infosys Online Game Programming Contest</td>
   </tr>
    <tr>
      <td colspan="3" background="images/but_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
    </tr>
  
     <tr>     
       <td colspan="3" valign="top" height="20px" bgcolor="#FFFFFF" align="center">
      </br>
        <a class="redtext2" href="enablejs.jsp" ><noscript>Your browser does not support JavaScript, please enable JavaScript. Click here for support.</noscript></a><br />
  	</td>
   </tr>
    
    <tr>
      <td colspan="2" align="right" bgcolor="#FFFFFF"><img src="images/banner22.jpg" width="513" height="254" /></td>
      <td valign="top" bgcolor="#ffffff" class="loginbgimg2">
<form id="form1" name="form1" method="post" action="<%=response.encodeURL("login_submit.jsp")%>">
	  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="96%" align="right"><table width="95%" border="0" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
              <tr>
                <td colspan="2" class="signin">&nbsp;</td>
              </tr>
              <tr>
                <td height="40" colspan="2" valign="top" class="signin">Sign in</td>
              </tr>

               <tr>
                <td height="30" valign="center"  class="signin3">Username</td>
                <td height="30" class="formtext2"  >
				
                    <input title="Use University Email ID only"  style="width:170px" name="email" type="text" id="textfield1" size="25" value="University email-ID only" onfocus="if(this.value=='University email-ID only'){this.value='';}" onblur="if(this.value==''){this.value='University email-ID only';}"/>
                    
               		 <INPUT TYPE="hidden" NAME="from_login1" value="<%=uid.toString() %>"/>
               		 <INPUT TYPE="hidden" NAME="from_login2" value="<%=sessionID.toString() %>"/>
               		 
              </tr>

              <tr>
                <td height="30" valign="center" class="signin3" style="width:170px" >Password</td>
                <td height="30" class="formtext2">
				
                    <input name="password" type="password" style="width:170px" id="textfield" size="25" onkeypress="return submitEnter(this,event)"/>
                </td>
              </tr>

              <tr>
                <td width="24%">&nbsp;</td>
                <td width="76%" align="right" valign="top"><a href="#"><img src="images/login_bt.jpg" width="74" height="20" border="0" class="rightimgpad2" onclick="callSubmitAction()" type="submit"/></a></td>
              </tr>
              <tr>
                <td colspan="2" class="signin2"  ><a class="register" href="sendmail.jsp">Forgot Password</a> <span >|</span> <span class="register"><a class="register" href="registration.jsp">Register&gt;&gt; </a></span></td>
              </tr>
              <tr>
                <td height="40" colspan="2" class="signin2"  ><span class="username7"> Please read the </span><span class="username3 style1"><a class="username3" href="javascript:void(0)" onclick="window.open('detailed_Instructions.html','QuickSteps','scrollbars=yes,location=no,directories=no');return false">Detailed Instructions</a></span><span class="username7"> before starting the game.</span></td>
              </tr>
              <tr>
                <td height="26" colspan="2" class="signin2"  ><span class="username3 style1"><a class="username3" href='http://server.iad.liveperson.net/hc/41340205/?cmd=file&file=visitorWantsToChat&site=41340205&byhref=1' target='chat41340205'  onClick="javascript:window.open('http://server.iad.liveperson.net/hc/41340205/?cmd=file&file=visitorWantsToChat&site=41340205&referrer='+escape(document.location),'chat41340205','width=472,height=320');return false;" >LIVE HELP</a></span><span class="username7">:
		 Please click here for assistance at any time.</span></td>
              </tr>
          </table>
	</form></td>
          <td width="4%" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>
      </table></td>
   </tr>
    <tr>
      <td bgcolor="#FFFFFF">&nbsp;</td>
      <td width="384" bgcolor="#FFFFFF">&nbsp;</td>
      <td width="428" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>   
    
    
    
    <tr>
      <td colspan="3" valign="top" bgcolor="#FFFFFF"><strong class="subheader">About the Game:</strong><br />
        <br />
        <span class="normaltextor"> <B>Rescue</B> is about saving a captive from a prison guarded by enemy guards. The rescue is done by the rescuer navigating the maze like prison, trying to avoid the enemy <span class="normaltextor">guards.</span> 
         The participant needs to code the logic for the rescuer. We request you to refer to the "detailed instruction" link for more details on the contest.<br />
        </span>
       <br />
        </td>
   </tr>
     <tr>
      <td colspan="3" valign="top" bgcolor="#FFFFFF"><strong class="subheader">Eligibility Criteria</strong><br />
        <br />
        <span class="normaltextor"> Only students from  <b>The University of Purdue</b> , <b>The University of Texas at Austin </b> and <b>The University of Illinois at Urbana, Champaign </b> can apply for this contest . 
        <br /><span class="normaltextor">Please give the details as requested for in the registration page.</span><br />        
       <br />
        </span></td>
   </tr>
  
    <tr>
      <td height="30" colspan="3" bgcolor="#FFFFFF" ></td>
    </tr>
    <tr>
      <td colspan="3" bgcolor="#ff6600"><img src="images/spacer.gif" width="1" height="3" /></td>
    </tr>
  </table>


</body>
</html>


