<!DOCTYPE html>
<html lang="en">
  <head>  <meta charset="utf-8">
    <title>CassieBABY2 &middot; Rearrange Playlist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="RearrangePlaylist">
    <meta name="author" content="Sam Killen">

<!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.datastax.driver.core.ResultSet" %>
<%@ page import="com.datastax.driver.core.Row" %>
<jsp:useBean id="Log" class="com.cassieBABY2.Beans.UserDAO" scope="session" />
<jsp:useBean id="Playlist" class="com.cassieBABY2.Beans.PlaylistDAO" scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<%
int place;
if(Log.isLoggedIn()){
    out.println("Hello user " + Log.getUsername() + " <br />");%>
<h3>Please select a playlist and hit the button, then select a song in that playlist and enter a new position, must be a number</h3>

<form action="RearangePlaylist.jsp">
<select name="Playlist">

<%
Playlist.setUsername(Log.getUsername());
ResultSet rs = Playlist.getPlaylists();
if(rs != null){
for (Row row : rs) {
	%>
		<option value="<%=row.getString("PlaylistName")%>"><%=row.getString("PlaylistName")%></option>
	<%
}
%>
</select>
<br><br>
<input type="submit" value="Select Playlist">
<br><br>
</form>

<%
if(request.getParameter("Playlist") == null){
	out.println("You must select a playlist <br><br>");
}else if(request.getParameter("Song") == null){
	Playlist.setPlaylistName(request.getParameter("Playlist"));
	%>
	<form action="RearangePlaylist.jsp">
	<select name="Song">

	<%
	rs = Playlist.getSongs(request.getParameter("Playlist"));
	for (Row row : rs) {
		%>
			<option value="<%=row.getString("TrackTitle")%>"><%=row.getString("TrackTitle")%></option>
		<%
	}
	%>
	</select>
	<br><br>
	Please provide a new position to place the song
	<br>
	<input type="text" name="myPlace" id="myPlace" value="">
	<br><br>
	<input type="submit" value="Rearange">
	<br><br>
	</form>
	<%
}
if(request.getParameter("myPlace") != null){
	String Place = request.getParameter("myPlace");
	 try {
	        place = Integer.parseInt( Place );
	      	Playlist.RearangePlaylist(Playlist.getPlaylistName(), request.getParameter("Song"), request.getParameter("myPlace"));
	    }
	    catch( Exception e ) {
	        out.println("You must provide a valid number to be able to rearange the order of the playlist");
	    }
}
}else{
	out.println("You must of created at least one playlist to be able to use this feature");
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