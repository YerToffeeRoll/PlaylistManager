package com.cassieBABY2.Beans;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.code.jspot.*;

public class PlaylistDAO {
	String username, playlistname, title, artist, album, id;
	boolean playlistexists;
	Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	PreparedStatement statement;
	int playlistpos = 0;
	ResultSet rs;
	Object results;
	
	public PlaylistDAO(){
		
	}
	
	public void setUsername(String user)
	{
		username = user;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setPlaylistName(String playlist)
	{
		playlistname = playlist;
	}
	
	public String getPlaylistName()
	{
		return playlistname;
	}
	
	public void setPlaylistPos(int pos)
	{
		playlistpos = pos;
	}
	
	public int getPlatlistPos()
	{
		return playlistpos;
	}
	
	public void setTitle(String Title)
	{
		title = Title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setArtist(String Artist)
	{
		artist = Artist;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	public void setAlbum(String Album)
	{
		album = Album;
	}
	
	public String getAlbum()
	{
		return album;
	}
	
	public void setSongID(String Songid)
	{
		id = Songid;
	}
	
	public String getSongID()
	{
		return id;
	}
	
	public void reset()
	{
		username = null;
		playlistname = null;
		playlistpos = 0;
		title = null;
		artist = null;
		album = null;
		id = null;
	}
	
	public void resetResults()
	{
		rs = null;
	}
	
	public void CreatePlaylist()
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists");
		BoundStatement boundStatement = new BoundStatement(statement);
		rs = session.execute(boundStatement);
		for (Row row : rs) {
			if(this.username.equals(row.getString("Username"))){
				if(this.playlistname.equals(row.getString("PlaylistName"))){
					playlistexists = true;
				}
			}
		}
		if(playlistexists != true){
		   statement = session.prepare("INSERT INTO UserDetails.UserPlaylists (Username, PlaylistName, PlaylistPos, TrackTitle, Artist, Album, Trackid) VALUES(?, ?, 0, ?, ?, ?, ?)");
		   session.execute(statement.bind(this.username, this.playlistname, "", "", "", ""));
		}
		session.close();
	}
	
	public void AddSong()
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists");
		BoundStatement boundStatement = new BoundStatement(statement);
		rs = session.execute(boundStatement);
		for (Row row : rs) {
			if(this.username.equals(row.getString("Username"))){
				if(this.playlistname.equals(row.getString("PlaylistName"))){
					playlistexists = true;
				}
			}
		}
		if(playlistexists == true){
			statement = session.prepare("INSERT INTO UserDetails.UserPlaylists (Username, PlaylistName, PlaylistPos, TrackTitle, Artist, Album, Trackid) VALUES(?, ?, ?, ?, ?, ?, ?)");
		   session.execute(statement.bind(this.username, this.playlistname, this.playlistpos, this.title, this.artist, this.album, this.id));
		}
		playlistexists = false;
		session.close();
	}
	
	public ResultSet getPlaylists()
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT PlaylistName FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistPos = 0");
		rs = session.execute(statement.bind(this.username));
		session.close();
		return rs;
	}
	
	public ResultSet getPlaylist(String playlist)
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT PlaylistPos, TrackTitle, Artist, Album FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
		ResultSet pl = session.execute(statement.bind(this.username, playlist));
		session.close();
		return pl;
	}
	
	public ResultSet getFullPlaylist(String playlist)
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
		rs = session.execute(statement.bind(this.username, playlist));
		session.close();
		return rs;
	}
	
	public ResultSet getSongInfo(String playlist, String track)
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ? AND TrackTitle = ?");
		rs = session.execute(statement.bind(this.username, playlist, track));
		session.close();
		return rs;
	}
	
	public void deletePlaylist(String playlist)
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("DELETE FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
		session.execute(statement.bind(this.username, playlist));
		session.close();
	}
	
	public void RearangePlaylist(String playlist, String track, String place)
	{
		int oldposition = 0;
		int Place = Integer.parseInt(place);
		Session session = cluster.connect("UserDetails");
		
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ? AND PlaylistPos >= ?");
		rs = session.execute(statement.bind(this.username, playlist, Place));
		for (Row row : rs) {
			statement = session.prepare("INSERT INTO UserDetails.UserPlaylists (Username, PlaylistName, PlaylistPos, TrackTitle, Artist, Album, Trackid) VALUES(?, ?, ?, ?, ?, ?, ?)");
			session.execute(statement.bind(this.username, playlist, Place+1, row.getString("TrackTitle"), row.getString("Artist"), row.getString("Album"), row.getString("Trackid")));
			Place ++;
		}
		
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ? AND TrackTitle = ?");
		ResultSet s = session.execute(statement.bind(this.username, playlist, track));
		for (Row row : s) {
			Place = Integer.parseInt(place);
			oldposition = row.getInt("PlaylistPos");
			statement = session.prepare("INSERT INTO UserDetails.UserPlaylists (Username, PlaylistName, PlaylistPos, TrackTitle, Artist, Album, Trackid) VALUES(?, ?, ?, ?, ?, ?, ?)");
			session.execute(statement.bind(this.username, playlist, Place, row.getString("TrackTitle"), row.getString("Artist"), row.getString("Album"), row.getString("Trackid")));
			statement = session.prepare("DELETE FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ? AND PlaylistPos = ?");
			session.execute(statement.bind(this.username, playlist, row.getInt("PlaylistPos")));
		}
		
		int previouspos = 0, currentpos;
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
		rs = session.execute(statement.bind(this.username, playlist));
		for (Row row : rs) {
			if(row.getInt("PlaylistPos") > 0){
				currentpos = row.getInt("PlaylistPos");
				if((currentpos - previouspos) != 1){
					statement = session.prepare("INSERT INTO UserDetails.UserPlaylists (Username, PlaylistName, PlaylistPos, TrackTitle, Artist, Album, Trackid) VALUES(?, ?, ?, ?, ?, ?, ?)");
					session.execute(statement.bind(this.username, playlist, (previouspos+1), row.getString("TrackTitle"), row.getString("Artist"), row.getString("Album"), row.getString("Trackid")));
					statement = session.prepare("DELETE FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ? AND PlaylistPos = ?");
					session.execute(statement.bind(this.username, playlist, currentpos));
					previouspos ++;
				}else{
				previouspos ++;
				}
			}
		}
		
		session.close();
	}
	
	public ResultSet getSongs(String playlist)
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT TrackTitle FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
		rs = session.execute(statement.bind(this.username, playlist));
		session.close();
		return rs;
	}
	
	public int getSongCount()
	{
		Session session = cluster.connect("UserDetails");
		statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists");
		BoundStatement boundStatement = new BoundStatement(statement);
		rs = session.execute(boundStatement);
		for (Row row : rs) {
			if(this.username.equals(row.getString("Username"))){
				if(this.playlistname.equals(row.getString("PlaylistName"))){
					playlistexists = true;
					playlistpos = 0;
				}
			}
		}
		if(playlistexists == true){
			statement = session.prepare("SELECT * FROM UserDetails.UserPlaylists WHERE Username = ? AND PlaylistName = ?");
			rs = session.execute(statement.bind(this.username, this.playlistname));
			for (Row row : rs) {
			playlistpos = row.getInt("PlaylistPos");
			}
		}
		playlistexists = false;
		session.close();
		return playlistpos;
}
	
	public boolean getPlaylistExists()
	{
		return playlistexists;
	}
	
	public void setPlaylistExists(boolean state)
	{
		playlistexists = state;
	}
	
	public Object getResults()
	{
		return results;
	}
	
	public void setResults(Object Results)
	{
		results = Results;
	}
			
}
