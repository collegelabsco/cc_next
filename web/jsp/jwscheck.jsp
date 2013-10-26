<html>
<head>


    <style>
	* {
		margin:0;
		padding:0
	}
	body {
		text-align:center;
		color:#536482;
		font-family:"Lucida Grande",Verdana,Helvetica,Arial,sans-serif;
	}
	#container {
		width:920px;
		margin: 0 auto;
		text-align:left;
	}
	.image_header {
		background-image:url(../dsweb/images/dev_logo.gif);
		background-repeat:no-repeat;
		height:90px;
		width:100%;
	}
	.tbl {
		border-bottom: 2px solid #E0E0E0;
	}
	.tbl th, .tbl td {
		width:auto;
		border-top:2px solid #E0E0E0;
		padding:4px;
		text-align:left;
		vertical-align:top;
		width:140px;
	}
	ol ul {
		padding-left:30px;
	}

    </style>

    <script LANGUAGE="JavaScript">
        function jwsInstalled() {
            // For Internet Explorer.
            if (navigator.userAgent.indexOf('MSIE') > -1) {
                try {
                    var jws = new ActiveXObject('JavaWebStart.isInstalled');
                    return true;
                }
                catch (e) {
                    return false;
                }
            }

            // Firefox is happy with "x-java-jnlp-file". For Chrome and Safari
            // this does not work, instead I just check for "x-java-vm".
            // If they have a recent JVM installed, then they usually also have
            // Java WebStart installed.
            navigator.plugins.refresh(true);
            return navigator.mimeTypes &&
                   navigator.mimeTypes.length &&
                   (navigator.mimeTypes['application/x-java-jnlp-file'] != null);
        };
    </script>

    <script type="text/javascript">
        function createUrl(url) {
            SideBar_RedirectUrl = url;
            window.location.href = url;
        };
        function onLoad(url){
            if (jwsInstalled()) {
                document.getElementById('content_div').innerHTML =
                "<ol><li> <p> We have detected that the <b>Java Web Start</b> is already installed and the application would launch automatically now.</p>"
                        + "<ul><br/><li><p>If the browser prompts to open a jnlp file with <b>Java Web Start Launcher</b>, please press <b>OK</b> on this dialog. </p></li>"
                        +  "</ul></li>" + "<br/><li><p>If the application does not get automatically launched, please <a href=\"javascript:void(0)\""
                        + "onclick=\"createUrl('" + url + "')\"> click here</a> to launch the application manually. </p></li></ol>";
                createUrl(url);
            }  else if (navigator.userAgent.indexOf('MSIE') > -1) { // IE use automatic install using activex
                document.getElementById('content_div').innerHTML =
                "<ol><li><p> The <b>Java Web Start</b> does not seem to be installed and you will be prompted by the browser to download and install it automatically."
                        + "<ul><br/><li><p>You may have to click on the Information Bar to confirm Java(TM) SE Runtime Enviornment 6 download from Sun Microsystems.</p></li> </ul>"
                        + "<br/><li><p>After installation, the application will get launched automatically. If the application does not get automatically launched, "
                        + "please <a href=\"javascript:void(0)\" onclick=\"createUrl('" + url + "')\">click here</a> to launch the application manually. </p></li></ol>";

                document.getElementById('object_div').innerHTML = "<OBJECT codebase=\"http://java.sun.com/update/1.6.0/jinstall-6-windows-i586.cab#Version=6,0,0,0\""
                        + "classid=\"clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284\" height=0 width=0><PARAM name=\"app\" value=\"" + url
                        + "\" ><PARAM name=\"back\" value=\"false\"> </OBJECT>";
            } else { // Others, give a installer location
                document.getElementById('content_div').innerHTML =
                "<ol><li><p> The <b>Java Web Start</b> does not seem to be installed, please download and install the Java Web Start from the "
                        + "<A href=\"http://java.com/en/download/index.jsp\" target=\"_blank\">Java web site</A>"
                        + ".</p></li><br/><li><p>After installing Java Web Start, you can launch the application  by clicking on the"
                        + "<a href=\"javascript:void(0)\" onclick=\"createUrl('" + url + "')\"> Launch Application</a>. </p></li></ol>";
            }
        };
    </script>
    <title>Java Web Start</title>
    <link rel="icon" href="../dsweb/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../dsweb/favicon.ico" type="image/x-icon" />
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
</head>
<%
    java.net.InetAddress address = java.net.InetAddress.getLocalHost();
    String ip = address.getHostAddress();
    String gameid = request.getParameter("gameid");
    String jcUrl = "http://" + ip  + "/jcload.jsp?gameid=" + gameid ;
%>

<body onload="onLoad('<%=jcUrl%>')" >
<div id="container">
    <div id="content">

		 <div class="image_header">
			<table border=0 width=100% >
			   <tr>
				<td width=100% align=right>
					<table>
                    <tr>
                        <td valign="top">
                            <a style="text-decoration: none;" href="../dsweb/projects/dsprojects.jsp">
                                <font color="#335489">
                                    <!--<br>Be Sought After--></font>
                            </a>
                        </td>
                        <td>
                            <a style="text-decoration: none;" href="adc/dsweb/projects/dsprojects.jsp">
                                <!--<img src="../images/certificate.gif" alt="DCP" border=0></a>-->
                                <img src="../dsweb/images/certificate.gif" alt="DCP" border=0></a>
                        </td>
                    </tr>
                   </table>
				</td>
			   </tr>
			</table>
		</div>


        <jsp:include page="..\integration\template.jsp">
            <jsp:param name="req" value="header" />
        </jsp:include>


        <div id="page-body">
            <div id="fullPane">
                <br><br>
                &nbsp;
                <div>
                    <table width="70%" align="center" class="tbl" style="margin-left:100px; font-size:12px;"> <tr> <td style="width:auto;">
			<p>This application uses <b>Java Web Start</b>, which is installed along with the <A href="http://java.com/en/download/index.jsp" target="_blank"> Java 			Runtime Environment (JRE) </A> installation.</p><br/>
                        <div id="content_div"> </div>
                        <div id="object_div"> </div>
                    </td> </tr>
                    </table>
		<br/><br/><br/>
                </div>
            </div>
        </div>
        <div id="page-footer">
                <span class="fcorners-bottom"><span></span></span>
        </div>
    </div>

</div>
</body>
</html>


