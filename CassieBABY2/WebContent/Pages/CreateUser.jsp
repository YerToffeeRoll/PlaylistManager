<!DOCTYPE html>
<html lang="en">
  <head>  <meta charset="utf-8">
    <title>CassieBABY2 &middot; CreateAUser</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Create a User">
    <meta name="author" content="Sam Killen">

<!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="Log" class="com.cassieBABY2.Beans.UserDAO" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Please enter a username and password then hit the button to create an account</h1>
<form name="CreateUser" method="post" action="CreateUser.jsp">
Username:
<input type="text" name="myUsername" id="myUsername" value="">
<br/>
<br/>
Password:
<input type="password" name="myPassword" id="myPassword" value="">
<br/>
<br/>
<input type="Submit" value="Create Account">
</form>

<%
String user = request.getParameter("myUsername");
String password = request.getParameter("myPassword");
if(request.getParameter("myUsername") != null){
	if(request.getParameter("myPassword") != null){
		Log.CreateAccount(request.getParameter("myUsername"), request.getParameter("myPassword"));
		if(Log.getNameExists() == false){
		response.sendRedirect("http://localhost:8080/CassieBABY2/index.jsp");
		}
		else {
			out.println("<br> Unfortunately that username has already been taken");
			Log.setNameExists(false);
		}
	}
}
%>
<br><br>
<input type="button" value="Home" name="Home" onclick="openPage('http://localhost:8080/CassieBABY2/index.jsp')"/>

<script type="text/javascript">
 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 </script>
 
</script>
</body>
</html>