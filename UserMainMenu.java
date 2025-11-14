import java.util.List;
import java.util.Scanner;

public class UserMainMenu {
    
    public static void displayUserMenu(User user, Scanner sc) {
        boolean inUserMenu = true;
        List<Song> allSongs = MusicDatabase.getAllSongs();
        Song currentSong = null;
        
        user.setCurrentSongList(allSongs);
        
        while (inUserMenu) {
            System.out.println("\n========================================");
            System.out.println("             USER MENU");
            System.out.println("========================================");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Next Song");
            System.out.println("4. Previous Song");
            System.out.println("5. Now Playing");
            System.out.println("6. Browse All Songs");
            System.out.println("7. Search Songs");
            System.out.println("8. Volume Controls");
            System.out.println("9. Log Out");
            System.out.println("  10. Delete account");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            String result = "";
            
            switch (choice) {

                case 1:
                    currentSong = playSelectedSong(user, sc, allSongs);
                    result = "Song selected";
                    break;

                case 2:

                    if (currentSong != null) {
                        result = user.pauseSong(currentSong);
                    } 
                    else {
                        result = "No song is currently playing. Please play a song first.";
                    }
                    break;

                case 3:
                    result = user.nextSong();
                    break;

                case 4:

                    result = user.previousSong();
                    break;

                case 5:
                    result = user.getCurrentSong();
                    break;

                case 6:
                    browseAllSongs();
                    result = "Songs displayed";
                    break;

                case 7:
                    searchSongs(sc);
                    result = "Search completed";
                    break;

                case 8:
                    handleVolumeControls(user, sc);
                    result = "Volume adjusted";
                    break;

                case 9:
                    result = user.logOut();
                    inUserMenu = false;
                    break;

                
                case 10: 
                    System.out.print("Are you sure you want to delete your account? This action cannot be undone. (YES): ");
                    String confirmation = sc.nextLine().toLowerCase();
                    if (confirmation.equals("YES") || confirmation.equals("y")) {
                        result = user.deleteAccount();
                        inUserMenu = false;
                    } 
                    else {
                        result = "Account deletion cancelled.";
                    }
                    break;

                default:
                    result = "Invalid choice. Please try again.";
            }
            
        
            if (!result.isEmpty()) {
                System.out.println("\n" + result);
                System.out.println(user.getVolumeDisplay());
            }
        }
    }
    
    private static void handleVolumeControls(User user, Scanner sc) {
        boolean inVolumeMenu = true;
        
        while (inVolumeMenu) {
            System.out.println("\n--- VOLUME CONTROLS ---");
            System.out.println(user.getVolumeDisplay());
            System.out.println("1. Increase Volume (+10%)");
            System.out.println("2. Decrease Volume (-10%)");
            System.out.println("3. Set Specific Volume");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int volumeChoice = sc.nextInt();
            sc.nextLine();
            
            switch (volumeChoice) {
                case 1:
                    System.out.println(user.increaseVolume());
                    break;
                case 2:
                    System.out.println(user.decreaseVolume());
                    break;
                case 3:
                    System.out.print("Enter volume level (0-100): ");
                    int newVolume = sc.nextInt();
                    sc.nextLine();
                    System.out.println(user.setVolume(newVolume));
                    break;
                case 4:
                    inVolumeMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
 
    private static Song playSelectedSong(User user, Scanner sc, List<Song> allSongs) {
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
                user.resetSongIndex(songIndex);
            }
            System.out.println(user.playSong(selectedSong));
            return selectedSong;
        }
        return null;
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
        } 
        else {
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