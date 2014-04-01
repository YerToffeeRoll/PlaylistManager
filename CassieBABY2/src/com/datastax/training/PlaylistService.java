/* all code by Laurent Weichberger: laurent@datastax.com */
package com.datastax.training;

import java.util.UUID;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class PlaylistService {

CassandraDAO dao = null;



//PlaylistDAO works with myplaylist keyspace by default
dao=new PlaylistDAO(); 


public void connect() {

//let the DAO create the right table for us... But only once.
dao.createTable(); 

/* ByteBuffers for blobs: coverArt and audioTrack */
ByteBuffer coverArt = this.readCoverArt();
ByteBuffer audioActual = this.readAudioTrack();

AudioFile af = new AudioFile(
UUID.randomUUID(), 
"Kami Nixon", 
"Fertile Girl", 
"Don't Matter Much", 
"Crossover", 
"English", 
227, //seconds 
1, 
2007, 
5, 
coverArt, 
audioActual);

System.out.println("Call saveOrUpdate with this audio file: " 
+ af.toString());

dao.saveOrUpdate(af);

dao.close();

}

public ByteBuffer readCoverArt(){

FileInputStream fis = null;
FileChannel channel = null;

try {

fis = new FileInputStream("file:///C:/Users/SK/Pictures/haters.jpg");

channel = fis.getChannel();

} catch (FileNotFoundException e1) {

e1.printStackTrace();

} catch (NullPointerException npe){

npe.printStackTrace();
}
//file is actually: 548380
ByteBuffer coverArt = ByteBuffer.allocate(600000); 

try {
if (channel.isOpen()){

channel.read(coverArt);
}
} catch (IOException e) {

e.printStackTrace();
}
finally{

try{ 

channel.close();
fis.close();
}
catch(Exception e){

e.printStackTrace();
}
}

System.out.println("** Read Cover Art ** worked!");

return coverArt;

}

public static void main(String[] args) {

PlaylistService starter = new PlaylistService();

starter.connect();
}
}

//for brevity (and it's almost the same), readAudioTrack() not shown...


