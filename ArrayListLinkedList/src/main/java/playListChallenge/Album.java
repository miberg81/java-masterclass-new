package playListChallenge;

import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    private String name;
    private String artist;
    private ArrayList<Song> songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<Song>();
    }

    public boolean addSong(String title, double duration) {
        final Song newSong = new Song(title, duration);
        Song existing = findSong(title);
        if (existing == null) {
            songs.add(newSong);
            return true;
        }
        return false;
    }

    private Song findSong(String title) {
        for (Song song : songs) {
            if(song.getTitle().equals(title)) {
                return song;
            }
        }
        return null;
    }

    /**
     * addToPlayList(), has two parameters of type int (track number of song in album)
     * and LinkedList (the playlist) that holds objects of type Song, and returns a boolean.
     * Returns true if it exists and it was added successfully using the track number, or false otherwise.
     */
    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {
        if (trackNumber > songs.size() || trackNumber<=0) {
            return false;
        }
        Song songToAdd = songs.get(trackNumber-1);
        if (songToAdd != null) {
            playList.add(songToAdd);
            return true;
        }
        return false;
    }

    /**
     -  addToPlayList(), has two parameters of type String (title of song)
     and LinkedList (the playlist) that holds objects of type Song, and returns a boolean.
     Returns true if it exists and it was added successfully
     using the name of the song, or false otherwise.
     */
    public boolean addToPlayList(String title, LinkedList<Song> playList) {
        Song song = findSong(title);
        if (song != null) {
            playList.add(song);
            return true;
        }
        return false;
    }

}
