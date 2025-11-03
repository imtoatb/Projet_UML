import java.util.Scanner;

public class PremiumUserMainMenu {
    
    public static void displayPremiumUserMenu(PremiumUser pUser, Scanner sc) {
        boolean inPremiumMenu = true;
        Playlist playlist = null;
        
        while (inPremiumMenu) {
            System.out.println("\n********** Premium User Menu **********");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Browse Songs");
            System.out.println("4. Create Playlist");
            System.out.println("5. Add Song to Playlist");
            System.out.println("6. Remove Song from Playlist");
            System.out.println("7. Play Playlist");
            System.out.println("8. Download Song");
            System.out.println("9. Log Out");
            System.out.println("***************************************");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    Song song = new Song("Blinding Lights", "The Weeknd", 200, 0);
                    System.out.println(pUser.playSong(song));
                    break;
                case 2:
                    Song song2 = new Song("Blinding Lights", "The Weeknd", 200, 75);
                    System.out.println(pUser.pauseSong(song2));
                    break;
                case 3:
                    browseAllSongs();
                    break;
                case 4:
                    System.out.print("Enter playlist name: ");
                    String playlistName = sc.nextLine();
                    playlist = new Playlist(playlistName, pUser.getId());
                    // Ajouter quelques chansons initiales
                    playlist.addSong("Dance Monkey");
                    playlist.addSong("Bad Guy");
                    playlist.addSong("Levitating");
                    MusicDatabase.addPlaylist(playlist);
                    System.out.println("Playlist '" + playlistName + "' created with " + playlist.getSongCount() + " songs");
                    break;
                case 5:
                    if (playlist != null) {
                        System.out.print("Enter song name to add: ");
                        String songName = sc.nextLine();
                        playlist.addSong(songName);
                        System.out.println("Playlist now has " + playlist.getSongCount() + " songs");
                    } else {
                        System.out.println("Please create a playlist first (option 4)");
                    }
                    break;
                case 6:
                    if (playlist != null && playlist.getSongCount() > 0) {
                        System.out.print("Enter song name to remove: ");
                        String songToRemove = sc.nextLine();
                        boolean removed = playlist.removeSong(songToRemove);
                        if (removed) {
                            System.out.println("Song removed. Playlist now has " + playlist.getSongCount() + " songs");
                        } else {
                            System.out.println("Song not found in playlist");
                        }
                    } else {
                        System.out.println("No playlist or playlist is empty");
                    }
                    break;
                case 7:
                    if (playlist != null && playlist.getSongCount() > 0) {
                        System.out.println(playlist.play());
                    } else {
                        System.out.println("No playlist available or playlist is empty");
                    }
                    break;
                case 8:
                    Song downloadSong = new Song("Save Your Tears", "The Weeknd", 215, 0);
                    System.out.println(pUser.downloadSong(downloadSong));
                    break;
                case 9:
                    System.out.println(pUser.logOut());
                    inPremiumMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    
    private static void browseAllSongs() {
        System.out.println("\nAvailable Songs:");
        System.out.println("1. Bohemian Rhapsody - Queen (354s)");
        System.out.println("2. Blinding Lights - The Weeknd (200s)");
        System.out.println("3. Shape of You - Ed Sheeran (234s)");
        System.out.println("4. Bad Guy - Billie Eilish (194s)");
        System.out.println("5. Dance Monkey - Tones and I (210s)");
        System.out.println("6. Levitating - Dua Lipa (203s)");
        System.out.println("7. Save Your Tears - The Weeknd (215s)");
    }
}