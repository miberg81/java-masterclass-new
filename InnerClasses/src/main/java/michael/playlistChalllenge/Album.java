package michael.playlistChalllenge;

import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    private String name;
    private String artist;
    private SongList songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }

    /**
     * addToPlayList(), has two parameters of type int (track number of song in album)
     * and LinkedList (the playlist) that holds objects of type Song, and returns a boolean.
     * Returns true if it exists and it was added successfully using the track number, or false otherwise.
     */
    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {
        if (trackNumber > songs.songs.size() || trackNumber<=0) {
            System.out.println("This album does not have a track " + trackNumber);
            return false;
        }
        Song songToAdd = songs.songs.get(trackNumber-1);
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
        Song song = songs.findSong(title);
        if (song != null) {
            playList.add(song);
            return true;
        }
        System.out.println("The song " + title + " is not in this album");
        return false;
    }

    public boolean addSong(String title, double duration) {
        final Song newSong = new Song(title, duration);
        Song existing = songs.findSong(title);
        if (existing == null) {
            songs.songs.add(newSong);
            return true;
        }
        return false;
    }
    
    public static class SongList {
        private ArrayList<Song> songs;

        private SongList() {
            this.songs = new ArrayList<>();
        }

        private boolean add(Song newSong) {
            Song existing = findSong(newSong.getTitle());
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

        private Song findSong(int trackNumber) {
            int index = trackNumber -1;
            if(index> songs.size()-1) {
                System.out.println("No song in that index...");
                return null;
            }
            return songs.get(index);
        }


    }

}
