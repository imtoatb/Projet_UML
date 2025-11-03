import java.util.List;
import java.util.Scanner;

public class UserMainMenu {
    
    public static void displayUserMenu(User user, Scanner sc) {
        boolean inUserMenu = true;
        
        while (inUserMenu) {
            System.out.println("\n========================================");
            System.out.println("             USER MENU");
            System.out.println("========================================");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Browse All Songs");
            System.out.println("4. Search Songs");
            System.out.println("5. Log Out");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    playSelectedSong(user, sc);
                    break;
                case 2:
                    pauseSelectedSong(user, sc);
                    break;
                case 3:
                    browseAllSongs();
                    break;
                case 4:
                    searchSongs(sc);
                    break;
                case 5:
                    System.out.println(user.logOut());
                    inUserMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void playSelectedSong(User user, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            System.out.println(user.playSong(selectedSong));
        }
    }
    
    private static void pauseSelectedSong(User user, Scanner sc) {
        Song selectedSong = MusicDatabase.selectSongFromLibrary(sc);
        if (selectedSong != null) {
            // Simuler un temps de lecture aléatoire
            selectedSong.playingtime = (int)(Math.random() * selectedSong.duration / 2);
            System.out.println(user.pauseSong(selectedSong));
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