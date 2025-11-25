import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PremiumUser extends User {
    private List<Song> shuffledPlaylist;
    private boolean shuffleMode = false;
    private Random random = new Random();
    
    public PremiumUser(String name, int userId){
        super(name, userId);
    }


  
    @Override
    public String logIn(){
        if (connected == true){
            return "You're already connected";
        }    
        else{
            connected = true;
            return "You're now on your Premium User account";
        }
    }



  
    public String createPlaylist(String playlistName){
        Playlist playlist = new Playlist(playlistName, this.getId());
        MusicDatabase.addPlaylist(playlist);
        return "Your playlist '" + playlistName + "' has been created";
    }

    public String addToPlaylist(Song song, Playlist playlist){
        playlist.addSong(song.name);
        return "'" + song.name + "' added to playlist '" + playlist.getName() + "'";
    }

    public String removeFromPlaylist(Song song, Playlist playlist){
        boolean removed = playlist.removeSong(song.name);
        
        if (removed) {
            return "'" + song.name + "' removed from playlist '" + playlist.getName() + "'";
        } 
        else {
            return "Song not found in playlist '" + playlist.getName() + "'";
        }
    }

    public String playPlaylist(Playlist playlist){
        return playlist.play();
    }


    public String deletePlaylist(String playlistName){
        boolean deleted = MusicDatabase.deletePlaylist(playlistName, this.getId());
        if (deleted) {
            return "Playlist '" + playlistName + "' has been deleted";
        } 
        else {
            return "Playlist '" + playlistName + "' not found";
        }
    }
    
    public List<Playlist> getMyPlaylists() {
        return MusicDatabase.getUserPlaylists(this.getId());
    }



    public String downloadSong(Song song){
        return "'" + song.name + "' has been downloaded";
    }




    public String toggleShuffle(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No songs available to shuffle.";
        }
        
        if (!shuffleMode) {
            shuffledPlaylist = new java.util.ArrayList<>(songList);
            Collections.shuffle(shuffledPlaylist, random);
            shuffleMode = true;
            setCurrentSongList(shuffledPlaylist);
            resetSongIndex(0);
            return "Shuffle mode activated! Playlist has been shuffled.";
        } 
        
        else {
            shuffleMode = false;
            shuffledPlaylist = null;
            setCurrentSongList(songList);
            return "Shuffle mode deactivated. Returning to normal order.";
        }
    }
    



    @Override
    public String nextSong() {
        if (shuffleMode && shuffledPlaylist != null) {
            currentSongIndex = (currentSongIndex + 1) % shuffledPlaylist.size();
            Song nextSong = shuffledPlaylist.get(currentSongIndex);
            return "SHUFFLE - Next: " + nextSong.name + " - " + nextSong.artist + " is now playing";
        } 
        else {
            return super.nextSong();
        }
    }

    
    @Override
    public String previousSong() {
        if (shuffleMode && shuffledPlaylist != null) {
            currentSongIndex = (currentSongIndex - 1 + shuffledPlaylist.size()) % shuffledPlaylist.size();
            Song previousSong = shuffledPlaylist.get(currentSongIndex);
            return "SHUFFLE - Previous: " + previousSong.name + " - " + previousSong.artist + " is now playing";
        } 
        else {
            return super.previousSong();
        }
    }
    

    @Override
    public String getCurrentSong() {
        if (shuffleMode && shuffledPlaylist != null) {

            Song currentSong = shuffledPlaylist.get(currentSongIndex);
            return "SHUFFLE - Now Playing: " + currentSong.name + " - " + currentSong.artist + " (" + currentSong.duration + "s)";
        
        } 
        else {
            return super.getCurrentSong();
        }
    }
    
    public boolean isShuffleMode() {
        return shuffleMode;
    }
    
    public String shufflePlaylist(Playlist playlist) {
        if (playlist == null) {
            return "No playlist selected";
        }
        
        if (playlist.getSongCount() == 0) {
            return "Empty playlist cannot be shuffle.";
        }
        

        List<Song> playlistSongs = new java.util.ArrayList<>();
        String[] songNames = playlist.getSongs();
        
        for (String songName : songNames) {
            Song song = MusicDatabase.findSongByName(songName);
            if (song != null) {

                playlistSongs.add(song);
            }
        }
        
        if (playlistSongs.isEmpty()) {
            return "No valid songs found in the playlist";
        }
        
        Collections.shuffle(playlistSongs, random);
        shuffledPlaylist = playlistSongs;
        shuffleMode = true;
        setCurrentSongList(shuffledPlaylist);
        resetSongIndex(0);
        
        return "Playlist '" + playlist.getName() + "' shuffled ";
    }



 
    @Override
    public String deleteAccount() {
        if (!isAccountActive()) {
            return "This account has already been deleted";
        }

        if (connected) {
            connected = false;
        }
        
 
        List<Playlist> userPlaylists = getMyPlaylists();
        for (Playlist playlist : userPlaylists) {
            MusicDatabase.deletePlaylist(playlist.getName(), this.getId());
        }

        this.name = "Deleted Premium User";
        this.currentSongList = null;
        this.currentSongIndex = 0;
        this.volume = 50;
        this.accountActive = false;
        
        return "Your Premium account has been deleted";
    }
}
