import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;



class Main {
    private static int id = 0;
    private static AllUser allUser = new AllUser();
    private static Scanner sc = new Scanner(System.in);
    // Liste des comptes "actifs" de la session (sans toucher AllUser)
    private static List<Object> activeAccounts = new ArrayList<>();
    // Petit catalogue en mémoire des morceaux gérés par l'admin
    private static List<Song> songCatalog = new ArrayList<>();



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
                createAdminAccount(name);   // <-- on crée vraiment l’admin
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

    // Cherche un morceau exact par (name, artist)
    private static Song findSong(String name, String artist) {
        for (Song s : songCatalog) {
            if (s.name.equals(name) && s.artist.equals(artist)) {
                return s;
            }
        }
        return null;
    }

    // Ajoute le morceau au catalogue si absent
    private static void addSongIfMissing(Song song) {
        if (findSong(song.name, song.artist) == null) {
            songCatalog.add(song);
        }
    }

    // Demande oui/non (y/n), retourne true si 'y'/'Y'
    private static boolean askYesNo(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String ans = sc.nextLine().trim();
        return ans.equalsIgnoreCase("y");
    }

    // Crée un compte utilisateur
    private static void createUserAccount(String name, String accountType) {
        if (accountType.equals("User")) {
            User user = new User(name, id);
            user.register(name, id);
            allUser.addUser(name, id, "User");
            System.out.println("Welcome " + user.getName() + ", here's your id : " + user.getId());
        } else if (accountType.equals("PremiumUser")){
            PremiumUser pUser = new PremiumUser(name, id);
            pUser.register(name, id);
            allUser.addUser(name, id, "PremiumUser");
            System.out.println("Welcome " + pUser.getName() + ", here's your id : " + pUser.getId());
        }
        id++;
    }

    // Crée un compte administrateur
    private static void createAdminAccount(String name) {
        allUser.addUser(name, id, "Admin");
        System.out.println("Admin account created for " + name + " with id " + id);
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
            case "Admin":{
                Admin admin = Admin.createAccount(userInfo.getName(), userInfo.getId());
                if (admin.login(userInfo.getId())) {
                    System.out.println("Welcome, Admin " + admin.getName() + "!");
                    adminMenu(admin);
                } else {
                    System.out.println("Invalid admin id.");
                }
                break;
            }
            default:
                System.out.println("Something went wrong");
        }
    }

    // Verifier si l'utilisateur existe
  /*  private static boolean userExists(String name, int id) {
        List<AllUser.UserInfo> list = allUser.getUsers();
        for (AllUser.UserInfo u : list) {
            if (u.getId() == id && u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Retourne true si (name,id) existe ET a le type demandé (User / PremiumUser / Admin)
    private static boolean userExistsAsType(String name, int id, String type) {
        List<AllUser.UserInfo> list = allUser.getUsers();
        for (AllUser.UserInfo u : list) {
            if (u.getId() == id && u.getName().equals(name) && u.getAccountType().equals(type)) {
                return true;
            }
        }
        return false;
    }
*/
    // Trouve l'index dans activeAccounts par (name,id). Retourne -1 si absent.
    private static int indexOfActive(String name, int id) {
        for (int i = 0; i < activeAccounts.size(); i++) {
            Object o = activeAccounts.get(i);
            if (o instanceof User u && !(o instanceof PremiumUser)) {
                if (u.getId() == id && u.getName().equals(name)) return i;
            } else if (o instanceof PremiumUser p) {
                if (p.getId() == id && p.getName().equals(name)) return i;
            }
        }
        return -1;
    }

    // Récupère l'objet actif s'il existe (User ou PremiumUser), sinon null
    private static Object getActive(String name, int id) {
        int idx = indexOfActive(name, id);
        return (idx >= 0) ? activeAccounts.get(idx) : null;
    }

    // Remplace (ou ajoute) un objet actif (suppression + ajout)
    private static void replaceActive(String name, int id, Object neo) {
        int idx = indexOfActive(name, id);
        if (idx >= 0) activeAccounts.remove(idx);
        activeAccounts.add(neo);
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
    private static void adminMenu(Admin admin) {
        if (!admin.is_connected()) {
            System.out.println("Please log in as Admin first.");
            return;
        }

        boolean inAdminMenu = true;

        while (inAdminMenu) {
            System.out.println("\n********** Admin Menu **********");
            System.out.println("1) Add Song");
            System.out.println("2) Modify Song");
            System.out.println("3) Delete Song");
            System.out.println("4) Manage Users (Upgrade/Downgrade)");
            System.out.println("5) Logout");
            System.out.println("6) Delete Admin Account");
            System.out.println("********************************");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // flush
            } catch (Exception e) {
                sc.nextLine(); // flush invalid
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case 1: { // Add Song (avec enregistrement dans le catalogue)
                    System.out.print("Song name: ");
                    String name = sc.nextLine();
                    System.out.print("Artist: ");
                    String artist = sc.nextLine();
                    System.out.print("Duration (seconds): ");
                    double duration;
                    try {
                        duration = sc.nextDouble();
                        sc.nextLine();
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("Invalid duration.");
                        break;
                    }
                    // playingtime initial 0
                    Song song = new Song(name, artist, duration, 0);
                    addSongIfMissing(song); // <-- on garde une trace
                    System.out.println(admin.addSongs(song));
                    break;
                }

                case 2: { // Modify Song — avec "0 = cancel" partout
                    System.out.print("Song to modify — name (0 = exit): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("Cancelled."); break; }

                    System.out.print("Artist (0 = exit): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("Cancelled."); break; }

                    Song song = findSong(name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist in the catalog.");
                        if (!askYesNo("Do you want to create it now?")) { break; }

                        System.out.print("Duration in seconds (0 = exit): ");
                        try {
                            double duration = sc.nextDouble(); sc.nextLine();
                            if (duration == 0) { System.out.println("Cancelled."); break; }
                            song = new Song(name, artist, duration, 0);
                            songCatalog.add(song);
                            System.out.println(admin.addSongs(song));
                        } catch (Exception e) {
                            sc.nextLine();
                            System.out.println("Invalid duration. Cancelled.");
                            break;
                        }
                    }

                    // Sous-menu modification avec option 0 = Back
                    System.out.println("\nWhat do you want to change?");
                    System.out.println("0) Back");
                    System.out.println("1) Name");
                    System.out.println("2) Artist");
                    System.out.println("3) Duration");
                    System.out.println("4) Playing Time");
                    System.out.print("Choice: ");

                    int sub;
                    try {
                        sub = sc.nextInt(); sc.nextLine();
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("Invalid input.");
                        break;
                    }

                    if (sub == 0) { System.out.println("Back."); break; }

                    switch (sub) {
                        case 1: {
                            System.out.print("New name (0 = exit): ");
                            String newName = sc.nextLine().trim();
                            if ("0".equals(newName)) { System.out.println("Cancelled."); break; }
                            // collision?
                            if (findSong(newName, song.artist) != null) {
                                System.out.println("A song with that name and the same artist already exists.");
                                break;
                            }
                            song.name = newName;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            break;
                        }
                        case 2: {
                            System.out.print("New artist (0 = exit): ");
                            String newArtist = sc.nextLine().trim();
                            if ("0".equals(newArtist)) { System.out.println("Cancelled."); break; }
                            if (findSong(song.name, newArtist) != null) {
                                System.out.println("A song with that artist and the same name already exists.");
                                break;
                            }
                            song.artist = newArtist;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            break;
                        }
                        case 3: {
                            System.out.print("New duration in seconds (0 = exit): ");
                            try {
                                double nd = sc.nextDouble(); sc.nextLine();
                                if (nd == 0) { System.out.println("Cancelled."); break; }
                                song.duration = nd;
                                System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("Invalid duration. Cancelled.");
                            }
                            break;
                        }
                        case 4: {
                            System.out.print("New playing time in seconds (0 = exit): ");
                            try {
                                double np = sc.nextDouble(); sc.nextLine();
                                if (np == 0) { System.out.println("Cancelled."); break; }
                                song.playingtime = np;
                                System.out.println("Updated playing time: " + song.name + " at " + song.playingtime + "s");
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("Invalid playing time. Cancelled.");
                            }
                            break;
                        }
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                }

                case 3: { // Delete Song — avec "0 = cancel" partout
                    System.out.print("Song to delete — name (0 = exit): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("Cancelled."); break; }

                    System.out.print("Artist (0 = exit): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("Cancelled."); break; }

                    Song song = findSong(name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist in the catalog.");
                        if (askYesNo("Do you want to create it instead?")) {
                            System.out.print("Duration in seconds (0 = cancel): ");
                            try {
                                double duration = sc.nextDouble(); sc.nextLine();
                                if (duration == 0) { System.out.println("Cancelled."); break; }
                                Song created = new Song(name, artist, duration, 0);
                                songCatalog.add(created);
                                System.out.println(admin.addSongs(created));
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("Invalid duration. Cancelled.");
                            }
                        }
                        break;
                    }

                    // Confirmation avant suppression, avec possibilité d'annuler
                    boolean ok = askYesNo("Are you sure you want to delete \"" + song.name + "\" by " + song.artist + "?");
                    if (!ok) { System.out.println("Cancelled."); break; }

                    songCatalog.remove(song);
                    System.out.println(admin.deleteSongs(song));
                    break;
                }

                case 4: { // Manage Users (Upgrade/Downgrade) avec persistance session (sans toucher AllUser)
                    System.out.println("1) Upgrade user to Premium");
                    System.out.println("2) Downgrade premium to Free");
                    System.out.print("Choice: ");

                    int um;
                    try {
                        um = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("Invalid input.");
                        break;
                    }

                    System.out.print("User name: ");
                    String uname = sc.nextLine();
                    System.out.print("User id: ");
                    int uid;
                    try {
                        uid = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("Invalid id.");
                        break;
                    }

                    // 1) Vérifier que (name,id) existe dans AllUser
                    boolean exists = false;
                    String declaredType = null;
                    for (AllUser.UserInfo u : allUser.getUsers()) {
                        if (u.getId() == uid && u.getName().equals(uname)) {
                            exists = true;
                            declaredType = u.getAccountType(); // "User" | "PremiumUser" | "Admin"
                            break;
                        }
                    }
                    if (!exists) {
                        System.out.println("No account found with this name and id.");
                        break;
                    }
                    if ("Admin".equals(declaredType)) {
                        System.out.println("Operation denied: cannot upgrade/downgrade an Admin.");
                        break;
                    }

                    // 2) Déterminer l'objet actif courant (session) ou en instancier un conforme au type déclaré
                    Object current = getActive(uname, uid);
                    if (current == null) {
                        if ("User".equals(declaredType)) current = new User(uname, uid);
                        else if ("PremiumUser".equals(declaredType)) current = new PremiumUser(uname, uid);
                    }

                    // 3) Appliquer la demande
                    if (um == 1) { // Upgrade -> Premium : il faut que ce soit un User
                        if (!(current instanceof User) || (current instanceof PremiumUser)) {
                            System.out.println("Upgrade denied: this account is not a 'User'.");
                            break;
                        }
                        try {
                            // supprimer l'ancien objet et créer le nouveau (mêmes name/id)
                            User oldUser = (User) current;
                            PremiumUser neo = admin.upgradeToPremium(oldUser);
                            replaceActive(uname, uid, neo);
                            System.out.println("Upgraded " + neo.getName() + " to Premium.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                    } else if (um == 2) { // Downgrade  Free : il faut que ce soit un PremiumUser
                        if (!(current instanceof PremiumUser)) {
                            System.out.println("Downgrade denied: this account is not a 'PremiumUser'.");
                            break;
                        }
                        try {
                            PremiumUser oldP = (PremiumUser) current;
                            User neo = admin.downgradeToFree(oldP);
                            replaceActive(uname, uid, neo);
                            System.out.println("Downgraded " + neo.getName() + " to Free.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                }
                case 5: { // Logout
                    admin.logout();
                    System.out.println("Admin logged out.");
                    inAdminMenu = false;
                    break;
                }

                case 6: { // Delete account
                    System.out.print("Type YES to confirm deletion: ");
                    String conf = sc.nextLine();
                    if ("YES".equals(conf)) {
                        admin.deleteAccount();
                        System.out.println("Admin account deleted.");
                        inAdminMenu = false;
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;
                }

                default:
                    System.out.println("Invalid choice.");
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