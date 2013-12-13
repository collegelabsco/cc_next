<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage = "error.jsp" %>
<%


     String sessionkey = "123";
    String type= "get";
   int level =2;

  try{
      URL url = new URL("http://localhost:8080//cc/cc?sessionkey="+sessionkey+"&type="+type+"&level="+level+"");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      InputStream stream = connection.getInputStream();
      InputStreamReader in = new InputStreamReader(stream);
      BufferedReader buff = new BufferedReader(in);
      String line;
      while((line=buff.readLine())!=null) {
          out.println(line);

      }
  }
      catch(Exception e){
        e.printStackTrace();
      }


%>