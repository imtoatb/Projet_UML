import java.util.Arrays;

public class Playlist {
    private String name;
    private String[] songs;
    private int userId;
    private int songCount;
    
  
    public Playlist(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.songs = new String[0];
        this.songCount = 0;
    }
    
    
    public String getName() {
        return name;
    }
    
    public String[] getSongs() {
        return Arrays.copyOf(songs, songCount);                  //og array, size
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getSongCount() {
        return songCount;
    }
    

    public void addSong(String songName) {
        for (int i = 0; i < songCount; i++) {                                //songs (tab) -> the playlist 
            String song = songs[i]; 
            if (song.equals(songName)) {
                System.out.println("Song '" + songName + "' is already in the playlist");
                return;
            }
        }
        
        String[] newSongs = Arrays.copyOf(songs, songCount + 1);         //copy the playlist + null at the end
        newSongs[songCount] = songName;
        songs = newSongs;                                                   //Overwrite the playlist
        songCount++;
    }
    

    public boolean removeSong(String songName) {
        boolean found = false;
        
        for (int i = 0; i < songCount; i++) {                                //songs (tab) -> the playlist 
            String song = songs[i]; 
            if (song.equals(songName)) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            return false;
        }
        
        String[] newSongs = new String[songCount - 1];
        int index = 0;

        for (int i = 0; i < songCount; i++) {
            String song = songs[i]; 

            if (!song.equals(songName)) {
                newSongs[index++] = song;
            }
        }
        songs = newSongs;
        songCount--;
        return true;
    }
    

    public boolean containsSong(String songName) {
        for (int i = 0; i < songCount; i++) {                              
            String song = songs[i]; 

            if (song.equals(songName)) {
                return true;
            }
        }
        return false;
    }
    
  
    public void displaySongs() {
        if (songCount == 0) {
            System.out.println("Playlist '" + name + "' is empty");
            return;
        }
        
        System.out.println("Songs in playlist '" + name + "':");
        for (int i = 0; i < songCount; i++) {
           
            System.out.println((i + 1) + ". " + songs[i]);

        }
    }
    
 
    
    public void clearPlaylist() {
        songs = new String[0];
        songCount = 0;
    }
    
    
    
    public String[] getList() {
        return Arrays.copyOf(songs, songCount);
    }
    
    public void setList(String[] list) {
        this.songs = Arrays.copyOf(list, list.length);
        this.songCount = list.length;
    }
    
   
    @Override
    public String toString() {
        return "Playlist : " + name + " (" + songCount + " songs)";
    }
    
 
    public String play() {
        if (songCount == 0) {
            return "Playlist '" + name + "' is empty";
        }
        
        StringBuilder playlistContent = new StringBuilder();
        playlistContent.append("Now playing playlist '").append(name).append("':\n");
        
        for (int i = 0; i < songCount; i++) {
           
            playlistContent.append(i + 1).append(". ").append(songs[i]);
            
            if (i < songCount - 1) {
                playlistContent.append("\n");
            }
        }
        
        return playlistContent.toString();
    }
}