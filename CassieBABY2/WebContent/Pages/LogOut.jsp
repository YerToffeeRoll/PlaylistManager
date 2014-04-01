<!DOCTYPE html>
<html lang="en">
  <head>  <meta charset="utf-8">
    <title>CassieBABY2 &middot; LogOut</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="LogOut">
    <meta name="author" content="Sam Killen">

<!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage = "errorPage.jsp" %>
<jsp:useBean id="Log" class="com.cassieBABY2.Beans.UserDAO" scope="session" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    </head>
    <body>
        <%
        if(Log.isLoggedIn() == true){
        Log.LogOut();
        out.println("You have been logged out<br />");}
        %>
        <br>
        <input type="button" value="Home" name="Home" onclick="openPage('http://localhost:8080/CassieBABY2/index.jsp')"/>
        
<script type="text/javascript">
 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 </script>        

    </body>
</html>