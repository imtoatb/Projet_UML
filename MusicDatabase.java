import java.util.ArrayList;
import java.util.List;

public class MusicDatabase {
    private static List<Song> allSongs = new ArrayList<>();
    private static List<Playlist> allPlaylists = new ArrayList<>();
    
    static {
        initializeSongs();
    }
    
    private static void initializeSongs() {
        allSongs.add(new Song("Bohemian Rhapsody", "Queen", 354, 0));
        allSongs.add(new Song("Blinding Lights", "The Weeknd", 200, 0));
        allSongs.add(new Song("Shape of You", "Ed Sheeran", 234, 0));
        allSongs.add(new Song("Bad Guy", "Billie Eilish", 194, 0));
        allSongs.add(new Song("Dance Monkey", "Tones and I", 210, 0));
        allSongs.add(new Song("Levitating", "Dua Lipa", 203, 0));
        allSongs.add(new Song("Save Your Tears", "The Weeknd", 215, 0));
        allSongs.add(new Song("Stay", "The Kid LAROI & Justin Bieber", 141, 0));
        allSongs.add(new Song("Good 4 U", "Olivia Rodrigo", 178, 0));
        allSongs.add(new Song("Watermelon Sugar", "Harry Styles", 174, 0));
        allSongs.add(new Song("Flowers", "Miley Cyrus", 200, 0));
        allSongs.add(new Song("Kill Bill", "SZA", 153, 0));
    }
    
    public static List<Song> getAllSongs() {
        return new ArrayList<>(allSongs);
    }
    
    public static Song findSongByName(String name) {
        for (Song song : allSongs) {
            if (song.name.equalsIgnoreCase(name)) {
                return song;
            }
        }
        return null;
    }
    
    public static List<Song> findSongsByArtist(String artist) {
        List<Song> result = new ArrayList<>();
        for (Song song : allSongs) {
            if (song.artist.equalsIgnoreCase(artist)) {
                result.add(song);
            }
        }
        return result;
    }
    
    public static void addPlaylist(Playlist playlist) {
        allPlaylists.add(playlist);
    }
    
    public static int getTotalSongs() {
        return allSongs.size();
    }
    
    public static int getTotalArtists() {
        List<String> artists = new ArrayList<>();
        for (Song song : allSongs) {
            if (!artists.contains(song.artist)) {
                artists.add(song.artist);
            }
        }
        return artists.size();
    }
    
    public static int getAllPlaylistsCount() {
        return allPlaylists.size();
    }
    
    public static List<Song> searchSongs(String query) {
        List<Song> results = new ArrayList<>();
        for (Song song : allSongs) {
            if (song.name.toLowerCase().contains(query.toLowerCase()) || 
                song.artist.toLowerCase().contains(query.toLowerCase())) {
                results.add(song);
            }
        }
        return results;
    }
    
    public static List<Playlist> getUserPlaylists(int userId) {
        List<Playlist> userPlaylists = new ArrayList<>();
        for (Playlist playlist : allPlaylists) {
            if (playlist.getUserId() == userId) {
                userPlaylists.add(playlist);
            }
        }
        return userPlaylists;
    }
    
    public static Playlist getPlaylistByName(String name, int userId) {
        for (Playlist playlist : allPlaylists) {
            if (playlist.getName().equalsIgnoreCase(name) && playlist.getUserId() == userId) {
                return playlist;
            }
        }
        return null;
    }
    
    public static boolean deletePlaylist(String name, int userId) {
        for (int i = 0; i < allPlaylists.size(); i++) {
            Playlist playlist = allPlaylists.get(i);
            if (playlist.getName().equalsIgnoreCase(name) && playlist.getUserId() == userId) {
                allPlaylists.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static int getUserPlaylistCount(int userId) {
        return getUserPlaylists(userId).size();
    }
}