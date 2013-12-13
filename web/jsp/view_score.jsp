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
    String email = null;
    if(session.getAttribute("loginemail")!=null) {
        email = (String)session.getAttribute("loginemail");
    }
    else {
        response.sendRedirect("index.jsp");
        return;
    }
    String sessionkey=null;

    if(email!=null){
        sessionkey= getSessionKey(email);
   }





%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- InstanceBeginEditable name="doctitle" -->
    <title>Code Champion</title>


</head>

<body onload="MM_preloadImages('images/home_over.jpg','images/contact_over.jpg','images/more_info_over.jpg')">

<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">

    <tr>

    </tr>
    <tr>
        <td colspan="3" background="images/but_line.jpg"><img src="images/spacer.gif" width="1" height="3" /></td>
    </tr>
    <tr>
        <td colspan="3" align="center" bgcolor="#FFFFFF"><table width="960" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="39" align="left" background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->
                    <p class="subheader">Welcome to The Infosys Code Champion Contest 2013
                    </p>
                <td height="39"  background="images/sub_header_bg.jpg"><!-- InstanceBeginEditable name="EditRegion4" -->

                    <!-- InstanceEndEditable --></td>

            </tr>
        </table>
        </td>
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
        <td colspan="3" bgcolor="#FFFFFF" class="leftimgpadt"><!-- InstanceBeginEditable name="EditRegion1" -->

            <table width="975" border="0" cellpadding="0" cellspacing="0" bgcolor="#dddddd">
                <tr>
                    <td width="136" height="40" class="subtitle22aa"><img src="images/arrow2.jpg" width="8" height="5" />&nbsp;&nbsp;&nbsp;&nbsp;Level 1:</td>
                    <td width="4" rowspan="5" bgcolor="#FFFFFF">&nbsp;</td>
                    <td class="subtitle22bb">Score :<%=getScore(sessionkey,1)%></td>
                </tr>

                <tr>
                    <td width="136" height="40" class="subtitle22aa"><img src="images/arrow2.jpg" width="8" height="5" />&nbsp;&nbsp;&nbsp;&nbsp;Level 2:</td>
                    <td class="subtitle22bb">&nbsp;Score :<%=getScore(sessionkey,2)%></td>

                </tr>
                <tr>

                </tr>

                <tr>
                    <td height="40" class="subtitle22bb"><img src="images/arrow2.jpg" width="8" height="5" />&nbsp;&nbsp;&nbsp;&nbsp;Level 3:</td>
                    <td class="subtitle22bb">Score: <%=getScore(sessionkey,4)%></td>
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
    </td>
    </tr>
    </table>
</body>

</html>
