package com.datastax.training;

import java.util.UUID;
import com.datastax.training.*;



public class AudioFile {

	 UUID uUID;
     String artist;
     String album;
     String track_title;
     String genre;
     String language;
     Integer track_length;
     Integer track_number;
     Integer year;
     Integer rating;
     ByteBuffer cover_art;
     ByteBuffer audio_actual;
     
     public  UUID getUuid()
     {
    	 return uUID;
     }
     public String getArtist()
     {
    	 return artist;
     }
    
     public String getAlbum()
     {
    	 return album;
     }
     
     public String getTrackTitle()
     {
    	 return track_title;
     }
     
     public String getGenre()
     {
    	return genre;
     }   
     public String getLanguage()
     {
    	return language;
     }   
     public  Integer getTrackLength()
     {
    	return track_length;
     }   
     public Integer getTrackNumber()
     {
    	 return track_number;
     }
     public Integer getYear()
     {
    	 return year;
     }
     public Integer getRating()
     {
    	 return rating;
     }
     public ByteBuffer getCoverArt()
     {
    	 return cover_art;
     }
     public ByteBuffer getAudioActual()
     {
    	 return audio_actual;
     }
}