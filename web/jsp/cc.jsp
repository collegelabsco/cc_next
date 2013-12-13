<%-- <%response.sendRedirect("http://www.collegelabs.co/adc/dsweb/contest/codechampion/cc.jsp");%> --%>
<%
	java.net.InetAddress address = java.net.InetAddress.getLocalHost(); 
    String ip = address.getHostAddress();
    String cp = request.getContextPath();
    String path="http://"+ip+( (cp!=null && cp.length()>0)?cp:"")+"/";
    String error = (String) session.getAttribute("error");
    if(error != null) session.removeAttribute("error");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Code Champion</title>
    <link href="../../style/default.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="../../favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="../../favicon.ico" type="image/x-icon"/>
<script>
   function validate_email(field)
    {
          var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
          if (!filter.test(field)) {
              return false;
          } else {
              return true;
          }

    }

   function validate_form(thisform)
    {
        with (thisform)
        { 
              var index1 = document.form1.email.value;
              var int1 = index1.indexOf("@");
              var len = index1.length;
              var valid1 = index1.substring(int1, len);
            if(email.value==null || email.value=="") 
            {
                alert("Please enter your Infosys Email Address");
                document.form1.email.focus();
                return false;
            }
           
            if (((valid1.toLowerCase() == "@devsquare.com")) || (valid1.toLowerCase() == "@infosys.com") || (valid1.toLowerCase() == "satyamsr@gmail.com")) {
				
                return true;
            } else {
				alert("Please enter your Infosys Email Address");
				return false;
			}
            return false;

        }
    }
   </script>

</head>

<body>

<div class="body_wrapper">

<div class="image_header">

</div>
<b><font size=+1>Welcome to The Infosys Code Champion Contest 2013</font></b>
<br><br>

<%-- The Preliminary Round is now CLOSED<br><br>

The Final Round will be open from 12:00 AM to 11:59 PM IST tomorrow. <br><br>

--%>

<%--
The Preliminary Round would be open till November 20th, 2012 23:59 IST. The contest is open 24hrs during these 19 days. <b>Participants can take part in this contest anytime, anywhere during this period.</b><br><br>

--%>

Participants would need to enter their Infosys email address and click on submit button to register for the contest. Login credentials will be sent to the registered email address.<br><br>

Infoscions who would like to opt for Mainframe technologies in the Infosys Code Champion Contest, would first need to participate in the preliminary round consisting of objective assessment in programming related topics. The final round however will be a programming contest in Mainframe Technologies. The preliminary round will be opened on December 4th, 2013.<br><br>

Infoscions who opt for technologies other than Mainframes would need to appear for a programming contest both in the preliminary and the final round. The programming solution(s) can be written in Java, C#.Net, C or C++. A few sample programming questions can be accessed <a href="http://107.23.11.198/openpbt/codechampion/login.jsp" target=_blank>here</a>.<br><br>


<a href="http://107.23.11.198/openpbt/codechampion/login.jsp" target=_blank>Click</a> here to login and participate in the Preliminary Round Programming Questions.<br><br>

<a href="http://athena.devsquare.com/adc/mcq/cc_objprelims.jsp" target=_blank>Click</a> here to login and participate in the Multiple Choice Format Questions (For Mainframe Technologies)<br><br>

<br><br>


  <form action="registration_submit.jsp" name="form1" method="POST" onsubmit="return validate_form(this)">
               <table align="center">
                   <tr><td>Please enter your official Infosys email ID to register for the contest</td></tr>
                   <tr><td class="txt">
                       <table border="0" align="center" cellpadding="5" cellspacing="5" class="table1">
                       <tr><td class="txt" align="right" >E-mail<font color="red">*</font></td>
                       <td><input type="text" name="email" value="" class = "textBox"></td></tr>
                       
                       <tr><td colspan="2" align="center">
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="button" type="submit" value="Submit">&nbsp;&nbsp;
                   </td></tr>
               </table>
           </td></tr>

                   <tr>
     <td width="100%" align="center" colspan="2">
    <%

       if(error!=null) {
        out.println(error);
      }

    %>
   </td>
   </tr>
           </table>
   </form>
   
   Write to <a href="mailto:help@devsquare.com" target=_blank>help@devsquare.com</a> for enquiries.
</div>

</body>

</html>



 