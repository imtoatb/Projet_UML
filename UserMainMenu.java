import java.util.List;
import java.util.Scanner;

public class UserMainMenu {
    
    public static void displayUserMenu(User user, Scanner sc) {
        boolean inUserMenu = true;
        List<Song> allSongs = MusicDatabase.getAllSongs();
        Song currentSong = null;
        
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
            System.out.println("8. Log Out");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    currentSong = playSelectedSong(user, sc);
                    break;
                case 2:
                    if (currentSong != null) {
                        System.out.println(user.pauseSong(currentSong));
                    } else {
                        System.out.println("No song is currently playing. Please play a song first.");
                    }
                    break;
                case 3:
                    System.out.println(user.nextSong(allSongs));
                    break;
                case 4:
                    System.out.println(user.previousSong(allSongs));
                    break;
                case 5:
                    System.out.println(user.getCurrentSong(allSongs));
                    break;
                case 6:
                    browseAllSongs();
                    break;
                case 7:
                    searchSongs(sc);
                    break;
                case 8:
                    System.out.println(user.logOut());
                    inUserMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static Song playSelectedSong(User user, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            // Trouver l'index de la chanson sélectionnée
            List<Song> allSongs = MusicDatabase.getAllSongs();
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