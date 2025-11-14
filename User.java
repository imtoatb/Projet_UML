
import java.util.List;

public class User {
    private String name;
    private int userId;
    boolean connected;
    private int currentSongIndex = 0; // Nouveau champ pour suivre la chanson actuelle
    
    public User(String name, int userId){
        this.name = name;
        this.userId = userId;
        this.connected = false;
        this.currentSongIndex = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.userId;
    }

    public String register(String name, int userId){
        this.name = name;
        this.userId = userId;
        return "Your account has been created";
    }

    public String logIn(){
        if (connected == true){
            return "You're already connected";
        }    
        else{
            connected = true;
            return "You're now on your User account";
        }
    }

    public String logOut(){
        if (connected == false){
            return "A problem has occur, please reach the admin for more informations";
        }
        else{
            connected = false;
            return "You've been disconnected";
        }
    }

    public String playSong(Song song){
        return song.name + " is playing";
    }

    public String pauseSong(Song song){
        return song.name + " has been paused at " + song.playingtime;
    }
    
    // NOUVELLE MÉTHODE : Next Song
    public String nextSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No songs available in the library.";
        }
        
        currentSongIndex = (currentSongIndex + 1) % songList.size();
        Song nextSong = songList.get(currentSongIndex);
        return "▶ Next: " + nextSong.name + " - " + nextSong.artist + " is now playing";
    }
    
    // NOUVELLE MÉTHODE : Previous Song
    public String previousSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No songs available in the library.";
        }
        
        currentSongIndex = (currentSongIndex - 1 + songList.size()) % songList.size();
        Song previousSong = songList.get(currentSongIndex);
        return "◀ Previous: " + previousSong.name + " - " + previousSong.artist + " is now playing";
    }
    
    // NOUVELLE MÉTHODE : Get Current Song
    public String getCurrentSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No song is currently playing.";
        }
        
        Song currentSong = songList.get(currentSongIndex);
        return "Now Playing: " + currentSong.name + " - " + currentSong.artist + " (" + currentSong.duration + "s)";
    }
    
    // NOUVELLE MÉTHODE : Reset song index (quand on choisit une nouvelle chanson)
    public void resetSongIndex(int newIndex) {
        this.currentSongIndex = newIndex;
    }
}