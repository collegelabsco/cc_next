<% response.setContentType("application/x-java-jnlp-file"); %>

<%    
   String gameid= request.getParameter("gameid");
   System.out.println("jcload.jsp: Starting JNLP with gameid=" + gameid);
%>


<?xml version="1.0" encoding="utf-8"?>

<jnlp spec="1.0+"  codebase="http://202.138.101.231/rescue">
  <information>
    <title>DevSquare</title><vendor>DevSquare</vendor>
    <homepage href="index.jsp"/>
    <description>DevSquare</description>
    <icon href="DevLogo.gif"/>
    <offline/>
  </information>
  <security><all-permissions/></security>
  <resources>
    <j2se version="1.5+" initial-heap-size="64m" max-heap-size="256m"/>
    <jar href="libs/fts.jar" kind="eager"/>
  </resources>

  <application-desc  main-class="com.devsquare.fts.Simulation">
		<argument><%=gameid%></argument>
  </application-desc>

</jnlp>
