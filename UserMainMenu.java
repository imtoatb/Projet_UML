import java.util.Scanner;

public class UserMainMenu {
    
    public static void displayUserMenu(User user, Scanner sc) {
        boolean inUserMenu = true;
        
        while (inUserMenu) {
            System.out.println("\n********** User Menu **********");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Browse Songs");
            System.out.println("4. Log Out");
            System.out.println("********************************");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    Song song = new Song("Bohemian Rhapsody", "Queen", 354, 0);
                    System.out.println(user.playSong(song));
                    break;
                case 2:
                    Song song2 = new Song("Bohemian Rhapsody", "Queen", 354, 120);
                    System.out.println(user.pauseSong(song2));
                    break;
                case 3:
                    browseAllSongs();
                    break;
                case 4:
                    System.out.println(user.logOut());
                    inUserMenu = false;
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
    }
}