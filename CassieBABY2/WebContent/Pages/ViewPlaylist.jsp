<!DOCTYPE html>
<html lang="en">
  <head>  <meta charset="utf-8">
    <title>CassieBABY2 &middot; View Playlist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ViewPlaylist">
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
if(Log.isLoggedIn()){
    out.println("Hello user " + Log.getUsername() + " <br />");%>
<h3>Please select a playlist and hit the button, then select a song in that playlist and enter a new position, must be a number</h3>

<form action="ViewPlaylist.jsp">
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
<input type="submit" value="View">
<br><br>
</form>

<%
if(request.getParameter("Playlist") == null){
	out.println("You must select a playlist to view its contents");
}else{
	ResultSet pl = Playlist.getPlaylist(request.getParameter("Playlist"));
	for (Row row : pl) {
		%>
		<table border="1" width="80%">
		<%
			if(row.getInt("PlaylistPos") == 0){
				%>
					<tr>
					<th>PlaylistPos</th>
					<th>Track Title</th>
					<th>Artist Name</th>
					<th>Album Name</th>
					</tr>
				<%
			}
			if(row.getInt("PlaylistPos") > 0){
			%>
			<tr>
			<td><p><%=row.getInt("PlaylistPos")%></p></td>
			<td><p><%=row.getString("TrackTitle")%></p></td>
			<td><p><%=row.getString("Artist")%></p></td>
			<td><p><%=row.getString("Album")%></p></td>
			</tr>
			<%
		}
		%>
		</table>
		<%
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