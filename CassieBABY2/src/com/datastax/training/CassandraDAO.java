package com.datastax.training;

import com.datastax.driver.core.*;
import com.datastax.driver.core.Requests.Query;
import com.datastax.training.*;

public abstract class CassandraDAO {

Session session = null; // if you have the session
Cluster cluster = null; // if you use the default ctor

public CassandraDAO(Session session) {

this.session = session;

}
public CassandraDAO() {

this("127.0.0.1", "127,0.0.2");

}
public CassandraDAO(String contactPoint1, String contactPoint2){

cluster = Cluster.builder().
addContactPoints(contactPoint1, contactPoint2).
build();

Metadata metadata = cluster.getMetadata();
System.out.printf("Connected to cluster: %s\n", 
metadata.getClusterName());

for ( Host host : metadata.getAllHosts() ) {
System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
host.getDatacenter(), host.getAddress(), host.getRack());
}

this.connect("myplaylist");

}

protected void connect(String keyspace){

if (session == null)
{
session = cluster.connect(keyspace);
}
}

protected ResultSet findByCQL(String CQL){

Query cqlQuery = new SimpleStatement(CQL);
cqlQuery.setConsistencyLevel(ConsistencyLevel.ONE);
cqlQuery.enableTracing();

return session.execute(cqlQuery);

}

protected Session getSession(){

return this.session;
}

public abstract void saveOrUpdate(CassandraEntity ce);

public abstract void createTable();

public void close() {

session.shutdown();

if (cluster != null){

cluster.shutdown();
}

}
}
/* Here is the AudioFile constructor for those interested... 

public AudioFile(UUID uuid, String artist, 
String album, String trackTitle,
String genre, String language, 
int trackLength, int trackNumber,
int year, int rating, ByteBuffer coverArt, 
ByteBuffer audioActual) 
*/