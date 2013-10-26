<%@page import="java.util.*" %>
<%
session.invalidate();
response.sendRedirect("rescue.jsp");
%>