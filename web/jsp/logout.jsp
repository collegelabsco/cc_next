<%@page import="java.util.*" %>
<%
session.invalidate();
response.sendRedirect("index.jsp");
%>