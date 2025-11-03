import java.util.Arrays;

public class Playlist {
    private String name;
    private String[] songs;
    private int userId;
    private int songCount;
    
    // Constructeur
    public Playlist(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.songs = new String[0];
        this.songCount = 0;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String[] getSongs() {
        return Arrays.copyOf(songs, songs.length);
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getSongCount() {
        return songCount;
    }
    
    // Ajouter une chanson à la playlist
    public void addSong(String songName) {
        for (String song : songs) {
            if (song.equals(songName)) {
                System.out.println("Song '" + songName + "' is already in the playlist.");
                return;
            }
        }
        
        String[] newSongs = Arrays.copyOf(songs, songs.length + 1);
        newSongs[songs.length] = songName;
        songs = newSongs;
        songCount++;
    }
    
    // Supprimer une chanson de la playlist
    public boolean removeSong(String songName) {
        boolean found = false;
        
        for (String song : songs) {
            if (song.equals(songName)) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            return false;
        }
        
        String[] newSongs = new String[songs.length - 1];
        int index = 0;
        for (String song : songs) {
            if (!song.equals(songName)) {
                newSongs[index++] = song;
            }
        }
        songs = newSongs;
        songCount--;
        return true;
    }
    
    // Vérifier si la playlist contient une chanson
    public boolean containsSong(String songName) {
        for (String song : songs) {
            if (song.equals(songName)) {
                return true;
            }
        }
        return false;
    }
    
    // Afficher toutes les chansons de la playlist
    public void displaySongs() {
        if (songCount == 0) {
            System.out.println("Playlist '" + name + "' is empty.");
            return;
        }
        
        System.out.println("Songs in playlist '" + name + "':");
        for (int i = 0; i < songs.length; i++) {
            System.out.println((i + 1) + ". " + songs[i]);
        }
    }
    
    // Vider la playlist
    public void clearPlaylist() {
        songs = new String[0];
        songCount = 0;
    }
    
    // Méthodes pour la compatibilité avec l'ancien code
    public String[] getList() {
        return Arrays.copyOf(songs, songs.length);
    }
    
    public void setList(String[] list) {
        this.songs = Arrays.copyOf(list, list.length);
        this.songCount = list.length;
    }
    
    // Représentation textuelle de la playlist
    @Override
    public String toString() {
        return "Playlist: " + name + " (" + songCount + " songs)";
    }
    
    // Jouer la playlist
    public String play() {
        if (songCount == 0) {
            return "Playlist '" + name + "' is empty!";
        }
        
        StringBuilder playlistContent = new StringBuilder();
        playlistContent.append("Now playing playlist '").append(name).append("':\n");
        
        for (int i = 0; i < songs.length; i++) {
            playlistContent.append(i + 1).append(". ").append(songs[i]);
            if (i < songs.length - 1) {
                playlistContent.append("\n");
            }
        }
        
        return playlistContent.toString();
    }
}