import java.util.List;

public class PremiumUser extends User {
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
        } else {
            return "Song not found in playlist '" + playlist.getName() + "'";
        }
    }

    public String playPlaylist(Playlist playlist){
        return playlist.play();
    }

    public String downloadSong(Song song){
        return "'" + song.name + "' has been downloaded";
    }

    public String deletePlaylist(String playlistName){
        boolean deleted = MusicDatabase.deletePlaylist(playlistName, this.getId());
        if (deleted) {
            return "Playlist '" + playlistName + "' has been deleted";
        } else {
            return "Playlist '" + playlistName + "' not found";
        }
    }
    
    public List<Playlist> getMyPlaylists() {
        return MusicDatabase.getUserPlaylists(this.getId());
    }
}

//Comment for commit