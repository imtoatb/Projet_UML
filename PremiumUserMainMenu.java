import java.util.Scanner;
import java.util.List;

public class PremiumUserMainMenu {
    
    public static void displayPremiumUserMenu(PremiumUser pUser, Scanner sc) {
        boolean inPremiumMenu = true;
        Playlist currentPlaylist = null;
        List<Song> allSongs = MusicDatabase.getAllSongs();
        Song currentSong = null;
        
        pUser.setCurrentSongList(allSongs);
        
        while (inPremiumMenu) {
            System.out.println("\n========================================");
            System.out.println("          PREMIUM USER MENU");
            System.out.println("========================================");

            System.out.println("Music Controls:");
            System.out.println("  1. Play Song");
            System.out.println("  2. Pause Song");
            System.out.println("  3. Next Song");
            System.out.println("  4. Previous Song");
            System.out.println("  5. Now Playing");
            System.out.println("  6. Toggle Shuffle Mode");
            System.out.println("  7. Browse All Songs");
            System.out.println("  8. Search Songs");

            System.out.println("Playlist Management:");
            System.out.println("  9. Create Playlist");
            System.out.println("  10. Add Song to Playlist");
            System.out.println("  11. Remove Song from Playlist");
            System.out.println("  12. Play Playlist");
            System.out.println("  13. Shuffle Playlist");
            System.out.println("  14. View My Playlists");

            System.out.println("Premium Features:");
            System.out.println("  15. Download Song");
            System.out.println("  16. Volume Controls");
            System.out.println("  17. Log Out");
            System.out.println("  18. Delete account");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            String result = "";
            
            switch (choice) {


                case 1:
                    currentSong = playSelectedSong(pUser, sc, allSongs);
                    
                    if (currentSong != null) {
                        result = pUser.playSong(currentSong);
                    } 
                    else {
                        result = "No song selected.";
                    }
                    break;

                case 2:
                    if (currentSong != null) {
                        result = pUser.pauseSong(currentSong);
                    } 
                    else {
                        result = "No song is currently playing. Please play a song first.";
                    }
                    break;

                case 3:
                    result = pUser.nextSong();
                    break;

                case 4:
                    result = pUser.previousSong();
                    break;

                case 5:
                    result = pUser.getCurrentSong();
                    break;

                case 6:
                    result = pUser.toggleShuffle(allSongs);
                    break;

                case 7:
                    result = browseAllSongs();
                    break;

                case 8:
                    result = searchSongs(sc);
                    break;

                case 9:
                    currentPlaylist = createPlaylist(pUser, sc);
                    result = "Playlist '" + currentPlaylist.getName() + "' created successfully!";
                    break;

                case 10:
                    result = addSongToPlaylist(pUser, currentPlaylist, sc);
                    break;

                case 11:
                    result = removeSongFromPlaylist(pUser, currentPlaylist, sc);
                    break;

                case 12:
                    if (currentPlaylist != null) {
                        result = pUser.playPlaylist(currentPlaylist);
                    } 
                    else {
                        result = "No playlist available. Please create one first.";
                    }
                    break;

                case 13:

                    if (currentPlaylist != null) {
                        result = pUser.shufflePlaylist(currentPlaylist);
                    } 
                    else {
                        result = "No playlist available. Please create one first.";
                    }
                    break;

                case 14:
                    result = viewUserPlaylists(pUser);
                    break;

                case 15:
                    result = downloadSelectedSong(pUser, sc);
                    break;

                case 16:
                    handleVolumeControls(pUser, sc);
                    result = "Volume controls closed";
                    break;

                case 17:
                    result = pUser.logOut();
                    inPremiumMenu = false;
                    break;

                case 18: 
                    System.out.print("Are you sure you want to delete your account? This action cannot be undone. (YES): ");
                    String confirmation = sc.nextLine().toLowerCase();
                    if (confirmation.equals("YES") || confirmation.equals("y")) {
                        result = pUser.deleteAccount();
                        inPremiumMenu = false;
                    } 
                    else {
                        result = "Account deletion cancelled.";
                    }

                    break;

                default:
                    result = "Invalid choice. Please try again.";
            }
            
            // Afficher le résultat + volume après chaque action
            if (!result.isEmpty() && choice != 16) { 
                System.out.println("\n" + result);
                System.out.println(pUser.getVolumeDisplay());
            }
        }
    }
    
    private static void handleVolumeControls(PremiumUser pUser, Scanner sc) {
        boolean inVolumeMenu = true;
        
        while (inVolumeMenu) {
            System.out.println("\n--- VOLUME CONTROLS ---");
            System.out.println(pUser.getVolumeDisplay());
            System.out.println("1. Increase Volume (+10%)");
            System.out.println("2. Decrease Volume (-10%)");
            System.out.println("3. Set Specific Volume");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int volumeChoice = sc.nextInt();
            sc.nextLine();
            
            String volumeResult = "";
            
            switch (volumeChoice) {
                case 1:
                    volumeResult = pUser.increaseVolume();
                    break;
                case 2:
                    volumeResult = pUser.decreaseVolume();
                    break;
                case 3:
                    System.out.print("Enter volume level (0-100): ");
                    int newVolume = sc.nextInt();
                    sc.nextLine();
                    volumeResult = pUser.setVolume(newVolume);
                    break;
                case 4:
                    inVolumeMenu = false;
                    volumeResult = "Returning to main menu...";
                    break;
                default:
                    volumeResult = "Invalid choice. Please try again.";
            }
            
                       if (!volumeResult.isEmpty()) {
                System.out.println(volumeResult);
            }
        }
    }
    
    private static Song playSelectedSong(PremiumUser pUser, Scanner sc, List<Song> allSongs) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            int songIndex = -1;
            for (int i = 0; i < allSongs.size(); i++) {
                if (allSongs.get(i).name.equals(selectedSong.name)) {
                    songIndex = i;
                    break;
                }
            }
            if (songIndex != -1) {
                pUser.resetSongIndex(songIndex);
            }
            return selectedSong;
        }
        return null;
    }
    
    private static String downloadSelectedSong(PremiumUser pUser, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            return pUser.downloadSong(selectedSong);
        }
        return "No song selected for download.";
    }
    
    private static Playlist createPlaylist(PremiumUser pUser, Scanner sc) {
        System.out.print("Enter playlist name: ");
        String playlistName = sc.nextLine();
        
        Playlist playlist = new Playlist(playlistName, pUser.getId());
        MusicDatabase.addPlaylist(playlist);
        
        System.out.print("Would you like to add songs to this playlist now? (yes/no): ");
        String response = sc.nextLine().toLowerCase();
        
        if (response.equals("yes") || response.equals("y")) {
            addSongToPlaylist(pUser, playlist, sc);
        }
        
        return playlist;
    }
    
    private static String addSongToPlaylist(PremiumUser pUser, Playlist playlist, Scanner sc) {
        if (playlist == null) {
            return "Please create a playlist first (option 9).";
        }
        
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            String result = pUser.addToPlaylist(selectedSong, playlist);
            return result + " Playlist now contains " + playlist.getSongCount() + " songs.";
        }
        return "No song selected to add to playlist.";
    }
    
    private static String removeSongFromPlaylist(PremiumUser pUser, Playlist playlist, Scanner sc) {
        if (playlist == null) {
            return "No playlist available. Please create one first.";
        }
        
        if (playlist.getSongCount() == 0) {
            return "Playlist is empty. No songs to remove.";
        }
        
        System.out.println("\nSongs in playlist '" + playlist.getName() + "':");
        String[] songs = playlist.getSongs();
        for (int i = 0; i < songs.length; i++) {
            System.out.println((i + 1) + ". " + songs[i]);
        }
        
        System.out.print("Select song number to remove (1-" + songs.length + "): ");
        String line = sc.nextLine();
        try {
            int choice = Integer.parseInt(line.trim());
            
            if (choice >= 1 && choice <= songs.length) {
                String songName = songs[choice - 1];
                Song songToRemove = new Song(songName, "", 0, 0);
                return pUser.removeFromPlaylist(songToRemove, playlist);
            } else {
                return "Invalid selection.";
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a number.";
        }
    }
    
    private static String browseAllSongs() {
        StringBuilder result = new StringBuilder();
        result.append("\nMUSIC LIBRARY - ALL SONGS\n");
        result.append("=========================\n");
        List<Song> allSongs = MusicDatabase.getAllSongs();
        
        for (int i = 0; i < allSongs.size(); i++) {
            Song song = allSongs.get(i);
            result.append(String.format("%2d. %-25s - %-20s (%.0fs)\n", 
                i + 1, song.name, song.artist, song.duration));
        }
        
        result.append("\nTotal songs: ").append(allSongs.size());
        return result.toString();
    }
    
    private static String searchSongs(Scanner sc) {
        System.out.print("Enter song name or artist to search: ");
        String query = sc.nextLine();
        
        List<Song> results = MusicDatabase.searchSongs(query);
        
        if (results.isEmpty()) {
            return "No songs found matching: " + query;
        } else {
            StringBuilder result = new StringBuilder();
            result.append("\nSEARCH RESULTS for '").append(query).append("':\n");
            result.append("=========================\n");
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                result.append(String.format("%2d. %-25s - %-20s (%.0fs)\n", 
                    i + 1, song.name, song.artist, song.duration));
            }
            return result.toString();
        }
    }
    
    private static String viewUserPlaylists(PremiumUser pUser) {
        List<Playlist> userPlaylists = pUser.getMyPlaylists();
        
        if (userPlaylists.isEmpty()) {
            return "You don't have any playlists yet.\nUse 'Create Playlist' to make your first playlist!";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("\nYOUR PLAYLISTS:\n");
        result.append("================\n");
        for (int i = 0; i < userPlaylists.size(); i++) {
            Playlist playlist = userPlaylists.get(i);
            result.append((i + 1)).append(". ").append(playlist.getName())
                  .append(" (").append(playlist.getSongCount()).append(" songs)\n");
        }
        return result.toString();
    }
}