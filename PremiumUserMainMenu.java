import java.util.List;
import java.util.Scanner;


public class PremiumUserMainMenu {
    
    public static void displayPremiumUserMenu(PremiumUser pUser, Scanner sc) {
        boolean inPremiumMenu = true;
        Playlist currentPlaylist = null;
        
        while (inPremiumMenu) {
            System.out.println("\n========================================");
            System.out.println("          PREMIUM USER MENU");
            System.out.println("========================================");
            System.out.println("Music Controls:");
            System.out.println("  1. Play Song");
            System.out.println("  2. Pause Song");
            System.out.println("  3. Browse All Songs");
            System.out.println("  4. Search Songs");
            System.out.println("Playlist Management:");
            System.out.println("  5. Create Playlist");
            System.out.println("  6. Add Song to Playlist");
            System.out.println("  7. Remove Song from Playlist");
            System.out.println("  8. Play Playlist");
            System.out.println("  9. View My Playlists");
            System.out.println("Premium Features:");
            System.out.println("  10. Download Song");
            System.out.println("  11. Log Out");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    playSelectedSong(pUser, sc);
                    break;
                case 2:
                    pauseSelectedSong(pUser, sc);
                    break;
                case 3:
                    browseAllSongs();
                    break;
                case 4:
                    searchSongs(sc);
                    break;
                case 5:
                    currentPlaylist = createPlaylist(pUser, sc);
                    break;
                case 6:
                    addSongToPlaylist(pUser, currentPlaylist, sc);
                    break;
                case 7:
                    removeSongFromPlaylist(pUser, currentPlaylist, sc);
                    break;
                case 8:
                    playPlaylist(pUser, currentPlaylist);
                    break;
                case 9:
                    viewUserPlaylists(pUser);
                    break;
                case 10:
                    downloadSelectedSong(pUser, sc);
                    break;
                case 11:
                    System.out.println(pUser.logOut());
                    inPremiumMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void playSelectedSong(PremiumUser pUser, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            System.out.println(pUser.playSong(selectedSong));
        }
    }
    
    private static void pauseSelectedSong(PremiumUser pUser, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            selectedSong.playingtime = (int)(Math.random() * selectedSong.duration / 2);
            System.out.println(pUser.pauseSong(selectedSong));
        }
    }
    
    private static void downloadSelectedSong(PremiumUser pUser, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            System.out.println(pUser.downloadSong(selectedSong));
        }
    }
    
    private static Playlist createPlaylist(PremiumUser pUser, Scanner sc) {
        System.out.print("Enter playlist name: ");
        String playlistName = sc.nextLine();
        
        Playlist playlist = new Playlist(playlistName, pUser.getId());
        MusicDatabase.addPlaylist(playlist);
        System.out.println("Playlist '" + playlistName + "' created successfully!");
        
        // Demander si l'utilisateur veut ajouter des chansons maintenant
        System.out.print("Would you like to add songs to this playlist now? (yes/no): ");
        String response = sc.nextLine().toLowerCase();
        
        if (response.equals("yes") || response.equals("y")) {
            addSongToPlaylist(pUser, playlist, sc);
        }
        
        return playlist;
    }
    
    private static void addSongToPlaylist(PremiumUser pUser, Playlist playlist, Scanner sc) {
        if (playlist == null) {
            System.out.println("Please create a playlist first (option 5).");
            return;
        }
        
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            System.out.println(pUser.addToPlaylist(selectedSong, playlist));
            System.out.println("Playlist now contains " + playlist.getSongCount() + " songs.");
        }
    }
    
    private static void removeSongFromPlaylist(PremiumUser pUser, Playlist playlist, Scanner sc) {
        if (playlist == null) {
            System.out.println("No playlist available. Please create one first.");
            return;
        }
        
        if (playlist.getSongCount() == 0) {
            System.out.println("Playlist is empty. No songs to remove.");
            return;
        }
        
        System.out.println("\nSongs in playlist '" + playlist.getName() + "':");
        String[] songs = playlist.getSongs();
        for (int i = 0; i < songs.length; i++) {
            System.out.println((i + 1) + ". " + songs[i]);
        }
        
        System.out.print("Select song number to remove (1-" + songs.length + "): ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            
            if (choice >= 1 && choice <= songs.length) {
                String songName = songs[choice - 1];
                Song songToRemove = new Song(songName, "", 0, 0); // Créer un objet Song temporaire
                System.out.println(pUser.removeFromPlaylist(songToRemove, playlist));
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }
    
    private static void playPlaylist(PremiumUser pUser, Playlist playlist) {
        if (playlist == null) {
            System.out.println("No playlist available. Please create one first.");
            return;
        }
        
        System.out.println(pUser.playPlaylist(playlist));
    }
    
    private static void viewUserPlaylists(PremiumUser pUser) {
        List<Playlist> userPlaylists = pUser.getMyPlaylists();
        
        if (userPlaylists.isEmpty()) {
            System.out.println("You don't have any playlists yet.");
            System.out.println("Use 'Create Playlist' to make your first playlist!");
            return;
        }
        
        System.out.println("\nYOUR PLAYLISTS:");
        System.out.println("================");
        for (int i = 0; i < userPlaylists.size(); i++) {
            Playlist playlist = userPlaylists.get(i);
            System.out.println((i + 1) + ". " + playlist.getName() + " (" + playlist.getSongCount() + " songs)");
        }
    }
    
    private static void browseAllSongs() {
        System.out.println("\nMUSIC LIBRARY - ALL SONGS");
        System.out.println("=========================");
        List<Song> allSongs = MusicDatabase.getAllSongs();
        
        for (int i = 0; i < allSongs.size(); i++) {
            Song song = allSongs.get(i);
            System.out.printf("%2d. %-25s - %-20s (%.0fs)\n", 
                i + 1, song.name, song.artist, song.duration);
        }
        
        System.out.println("\nTotal songs: " + allSongs.size());
    }
    
    private static void searchSongs(Scanner sc) {
        System.out.print("Enter song name or artist to search: ");
        String query = sc.nextLine();
        
        List<Song> results = MusicDatabase.searchSongs(query);
        
        if (results.isEmpty()) {
            System.out.println("No songs found matching: " + query);
        } else {
            System.out.println("\nSEARCH RESULTS for '" + query + "':");
            System.out.println("=========================");
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.printf("%2d. %-25s - %-20s (%.0fs)\n", 
                    i + 1, song.name, song.artist, song.duration);
            }
        }
    }
}