package com.cassieBABY2.models;



import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class PlaylistModel {
Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
public PlaylistModel(){

}

public void setCluster(Cluster cluster){
this.cluster=cluster;
}

public void CreateDatabase() {
	
Session session = cluster.connect();
session.execute("CREATE KEYSPACE IF NOT EXISTS UserDetails WITH replication "
         + "= {'class':'SimpleStrategy', 'replication_factor':3};");
session.execute("CREATE TABLE IF NOT EXISTS UserDetails.Users (UserName text, Password text, PRIMARY KEY (UserName));");
session.execute("CREATE TABLE IF NOT EXISTS UserDetails.UserPlaylists (Username text, PlaylistName text, PlaylistPos int, TrackTitle text, Artist text, Album text, Trackid text, PRIMARY KEY (Username, PlaylistName, PlaylistPos));");
session.execute("CREATE INDEX IF NOT EXISTS PlaylistPos ON UserDetails.UserPlaylists (PlaylistPos);");
session.execute("CREATE INDEX IF NOT EXISTS TrackTitle ON UserDetails.UserPlaylists (TrackTitle);");
session.close();
}
}