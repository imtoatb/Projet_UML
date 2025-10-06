import java.util.Scanner;

class Main {
    private static int id = 0;
    private static AllUser allUser = new AllUser();
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // vider le buffer après nextInt()

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using MAHE Music!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
    
    // Affiche le menu principal
    private static void displayMainMenu() {
        System.out.println("************** Welcome on MAHE Music **************");
        System.out.println("1. Register");
        System.out.println("2. Log In");
        System.out.println("3. Exit");
        System.out.println("***************************************************");
        System.out.print("Enter your choice: ");
    }
    
    // Gère l'inscription d'un nouvel utilisateur
    private static void registerUser() {
        System.out.println("Create your account !");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Who are you ? (Admin, User, PremiumUser): ");
        String status = sc.nextLine();

        switch (status.toLowerCase()) {
            case "admin":
                System.out.println("Admin");
                break;
            case "user":
                createUserAccount(name, "User");
                break;
            case "premiumuser":
                createUserAccount(name, "PremiumUser");
                break;
            default:
                System.out.println("Invalid account type");
        }
    }
    
    // Crée un compte utilisateur
    private static void createUserAccount(String name, String accountType) {
        if (accountType.equals("User")) {
            User user = new User(name, id);
            user.register(name, id);
            allUser.addUser(name, id, "User");
            System.out.println("Welcome " + user.getName() + ", here's your id : " + user.getId());
        } else {
            PremiumUser pUser = new PremiumUser(name, id);
            pUser.register(name, id);
            allUser.addUser(name, id, "PremiumUser");
            System.out.println("Welcome " + pUser.getName() + ", here's your id : " + pUser.getId());
        }
        id++;
    }
    
    // Gère la connexion d'un utilisateur
    private static void loginUser() {
        System.out.print("What's your name ? ");
        String loginName = sc.nextLine();
        System.out.print("What's your id ? ");
        int loginId = sc.nextInt();
        sc.nextLine(); // vider le buffer après nextInt()
        
        boolean found = false;
        for (AllUser.UserInfo userInfo : allUser.getUsers()) {
            if (userInfo.getName().equals(loginName) && userInfo.getId() == loginId) {
                found = true;
                handleUserLogin(userInfo);
                break;
            }
        }
        
        if (!found) {
            System.out.println("No account found with this name and id.");
        }
    }
    
    // Gère la connexion selon le type de compte
    private static void handleUserLogin(AllUser.UserInfo userInfo) {
        switch (userInfo.getAccountType()) {
            case "User":
                User user = new User(userInfo.getName(), userInfo.getId());
                System.out.println(user.logIn());
                userMenu(user);
                break;
            case "PremiumUser":
                PremiumUser pUser = new PremiumUser(userInfo.getName(), userInfo.getId());
                System.out.println(pUser.logIn());
                premiumUserMenu(pUser);
                break;
            case "Admin":
                System.out.println("Admin login not available yet.");
                break;
            default:
                System.out.println("Something went wrong");
        }
    }
    
    // Menu pour les utilisateurs normaux
    private static void userMenu(User user) {
        boolean inUserMenu = true;
        
        while (inUserMenu) {
            System.out.println("\n********** User Menu **********");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Log Out");
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
                    System.out.println(user.logOut());
                    inUserMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    
    // Menu pour les utilisateurs premium
    private static void premiumUserMenu(PremiumUser pUser) {
        boolean inPremiumMenu = true;
        Playlist playlist = null;
        
        while (inPremiumMenu) {
            System.out.println("\n********** Premium User Menu **********");
            System.out.println("1. Play Song");
            System.out.println("2. Pause Song");
            System.out.println("3. Create Playlist");
            System.out.println("4. Add Song to Playlist");
            System.out.println("5. Remove Song from Playlist");
            System.out.println("6. Play Playlist");
            System.out.println("7. Download Song");
            System.out.println("8. Log Out");
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
                    String[] initialList = {"Dance Monkey", "Bad Guy", "Levitating"};
                    System.out.println(pUser.createPlaylist(initialList));
                    playlist = new Playlist();
                    playlist.list = initialList;
                    System.out.println("Playlist created with " + initialList.length + " songs");
                    break;
                case 4:
                    if (playlist != null) {
                        Song newSong = new Song("Shape of You", "Ed Sheeran", 234, 0);
                        System.out.println(pUser.addToPlaylist(newSong, playlist));
                        System.out.println("Playlist now has " + playlist.list.length + " songs");
                    } else {
                        System.out.println("Please create a playlist first (option 3)");
                    }
                    break;
                case 5:
                    if (playlist != null && playlist.list.length > 0) {
                        Song songToRemove = new Song("Bad Guy", "Billie Eilish", 194, 0);
                        System.out.println(pUser.removeFromPlaylist(songToRemove, playlist));
                        System.out.println("Playlist now has " + playlist.list.length + " songs");
                    } else {
                        System.out.println("No playlist or playlist is empty");
                    }
                    break;
                case 6:
                    if (playlist != null && playlist.list.length > 0) {
                        System.out.println(pUser.playPlaylist(playlist));
                    } else {
                        System.out.println("No playlist available or playlist is empty");
                    }
                    break;
                case 7:
                    Song downloadSong = new Song("Save Your Tears", "The Weeknd", 215, 0);
                    System.out.println(pUser.downloadSong(downloadSong));
                    break;
                case 8:
                    System.out.println(pUser.logOut());
                    inPremiumMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    
    // Méthode utilitaire pour afficher les détails d'une chanson
    private static void displaySongInfo(Song song) {
        System.out.println("Now playing: " + song.name + " by " + song.artist);
        System.out.println("Duration: " + song.duration + " seconds");
    }
}