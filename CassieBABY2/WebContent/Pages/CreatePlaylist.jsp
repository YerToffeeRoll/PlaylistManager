<!DOCTYPE html>
<html lang="en">
  <head>  <meta charset="utf-8">
    <title>CassieBABY2 &middot; Create Playlist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="CreatePlaylist">
    <meta name="author" content="Sam Killen">

<!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="Log" class="com.cassieBABY2.Beans.UserDAO" scope="session" />
<jsp:useBean id="Playlist" class="com.cassieBABY2.Beans.PlaylistDAO" scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<%
if(Log.isLoggedIn()){
    out.println("Hello user " + Log.getUsername() + " <br />");%>
<h3>Please enter a name for your playlist and hit the button</h3>

<form name="CreatePlaylist" method="post" action="CreatePlaylist.jsp">
Name for Playlist:
<input type="text" name="myPlaylistName" id="myPlaylistName" value="">
<br><br>
<input type="Submit" value="Create Playlist!">
<br><br>
</form>

<%  
if(request.getParameter("myPlaylistName") != null){
 	Playlist.setUsername(Log.getUsername());
 	Playlist.setPlaylistName(request.getParameter("myPlaylistName"));
 	Playlist.CreatePlaylist();
	if(Playlist.getPlaylistExists() == false){
	response.sendRedirect("http://localhost:8080/CassieBABY2/index.jsp");
	}
	else {
		out.println("<br> Unfortunately a playlist with that name already exists");
		Playlist.setPlaylistExists(false);
	}
}
else{
	out.println("Please Provide a Name for your Playlist");
}
}else{
	%>
	<h1>You must be logged into an account to access this feature</h1>
	<%
}
%> 

<br><br>
<input type="button" value="Home" name="Home" onclick="openPage('http://localhost:8080/CassieBABY2/index.jsp')"/>
<br><br>
<input type="button" value="Log Out" name="Logout" onclick="openPage('http://localhost:8080/CassieBABY2/Pages/LogOut.jsp')"/>

<script type="text/javascript">
 function openPage(pageURL)
 {
 window.location.href = pageURL;
 }
 </script>

</body>
</html>