
package com.datastax.training;

import com.datastax.driver.core.*;
import java.util.UUID;

public class PlaylistDAO extends CassandraDAO {

PlaylistDAO(Session session){

super(session);
}
PlaylistDAO(){

super();

}

public void createTable(){

String tableCQL = "CREATE TABLE audiofile ( " +
"audioid uuid PRIMARY KEY, artist text, " + 
"album text, track_title text, genre text, " +
"language text, track_length int, track_num int, year int, " +
"rating int, cover_art blob, audio_actual blob);"; 

Query cqlQuery = new SimpleStatement(tableCQL);

session.execute(tableCQL);

}public void saveOrUpdate(CassandraEntity ce) {

AudioFile af = (AudioFile) ce;

//Use ByteBuffer for Blob

//Use BoundStatement

PreparedStatement statement = getSession().prepare(
"INSERT INTO myplaylist.audiofile " +
"(audioid, artist, album, track_title, genre," +
" language, track_length, " +
"track_num, year, rating, cover_art, audio_actual) " +
"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

BoundStatement boundStatement = new BoundStatement(statement);

getSession().execute(
boundStatement.bind(
af.getUuid(),
af.getArtist(),
af.getAlbum(),
af.getTrackTitle(),
af.getGenre(),
af.getLanguage(),
af.getTrackLength(),
af.getTrackNumber(),
af.getYear(),
af.getRating(),
af.getCoverArt(),
af.getAudioActual()
)
);

} 

}