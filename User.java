import java.util.List;

public class User {
    private String name;
    private int userId;
    boolean connected;
    int currentSongIndex = 0;
    private List<Song> currentSongList; 
    private int volume = 50;
    
    public User(String name, int userId){
        this.name = name;
        this.userId = userId;
        this.connected = false;
        this.currentSongIndex = 0;
        this.currentSongList = null;
        this.volume = 50;
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



    //Log In/OUt
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



    //For Songs
    public String playSong(Song song){
        return song.name + " is playing";
    }

    public String pauseSong(Song song){
        return song.name + " has been paused at " + song.playingtime;
    }
    
    public String nextSong() {
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No songs available in the library. Please play a song first.";
        }
        
        currentSongIndex = (currentSongIndex + 1) % currentSongList.size();
        Song nextSong = currentSongList.get(currentSongIndex);
        return "Next: " + nextSong.name + " - " + nextSong.artist + " is now playing";
    }
    
    public String previousSong() {
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No songs available in the library. Please play a song first.";
        }
        
        currentSongIndex = (currentSongIndex - 1 + currentSongList.size()) % currentSongList.size();
        Song previousSong = currentSongList.get(currentSongIndex);
        return "Previous: " + previousSong.name + " - " + previousSong.artist + " is now playing";
    }
    
    public String getCurrentSong() {
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No song is currently playing.";
        }
        
        Song currentSong = currentSongList.get(currentSongIndex);
        return "Now Playing: " + currentSong.name + " - " + currentSong.artist + " (" + currentSong.duration + "s)";
    }
    
    public void setCurrentSongList(List<Song> songList) {
        this.currentSongList = songList;
    }
    
    public void resetSongIndex(int newIndex) {
        this.currentSongIndex = newIndex;
    }
    
    public int getCurrentSongIndex() {
        return currentSongIndex;
    }


    //For Volume
    public String increaseVolume() {
        if (volume < 100) {
            volume += 10;
            if (volume > 100) volume = 100;
            return displayVolume();
        } else {
            return displayVolume() + " (Volume maximum atteint!)";
        }
    }
    
    public String decreaseVolume() {
        if (volume > 0) {
            volume -= 10;
            if (volume < 0) volume = 0;
            return displayVolume();
        } else {
            return displayVolume() + " (Volume minimum atteint!)";
        }
    }
    
    public String setVolume(int newVolume) {
        if (newVolume >= 0 && newVolume <= 100) {
            volume = newVolume;
            return displayVolume();
        } else {
            return "Volume must be between 0 and 100";
        }
    }
    
    public String displayVolume() {
        System.out.println('\n');
    
        int bars = volume / 10;
        StringBuilder volumeBar = new StringBuilder("[ ");
        for (int i = 0; i < 10; i++) {
            if (i < bars) {
                volumeBar.append("H");
            } else {
                volumeBar.append("-");
            }
        }
        volumeBar.append(" ] ");
        
        return "Volume: " + volumeBar.toString() + volume + "%";
    }
    
    public int getVolume() {
        return volume;
    }
    
    
    public String getVolumeDisplay() {
        return displayVolume();
    }
}