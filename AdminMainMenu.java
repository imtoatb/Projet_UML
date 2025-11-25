import java.util.List;
import java.util.Scanner;

public final class AdminMainMenu {

    private static Song findSong(List<Song> catalog, String name, String artist) {
        for (int i = 0; i < catalog.size(); i++) {
            Song s = catalog.get(i);
            if (s.name.equalsIgnoreCase(name) && s.artist.equalsIgnoreCase(artist)) return s;
        }
        return null;
    }

    private static boolean askYesNo(Scanner sc, String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String a = sc.nextLine().trim();
        return a.equalsIgnoreCase("y") || a.equalsIgnoreCase("yes");
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int v = sc.nextInt(); sc.nextLine();
                return v;
            } catch (Exception e) {
                System.out.println("Invalid number.");
                sc.nextLine();
            }
        }
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double v = sc.nextDouble(); sc.nextLine();
                return v;
            } catch (Exception e) {
                System.out.println("Invalid number");
                sc.nextLine();
            }
        }
    }

  
    public static void displayAdminMenu(Admin admin, Scanner sc, AllUser allUser, List<Song> songCatalog) {
        if (!admin.is_connected()) {
            System.out.println("Please log in as Admin");
            return;
        }

        boolean in = true;
        while (in) {
            System.out.println("\n********** Admin Menu **********");
            System.out.println("1) Add Song");
            System.out.println("2) Modify Song");
            System.out.println("3) Delete Song");
            System.out.println("4) Manage Users");
            System.out.println("5) Admin Settings");
            System.out.println("6) Logout");
            System.out.println("7) Delete Admin Account");
            System.out.println("********************************");

            int choice = readInt(sc, "Enter your choice : ");

            switch (choice) {
                case 1: { 
                    System.out.print("Song name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("End"); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("End"); break; }

                    double duration = readDouble(sc, "Duration (seconds, 0 = cancel): ");
                    if (duration == 0) { System.out.println("End"); break; }

                    Song song = new Song(name, artist, duration, 0);
                    if (findSong(songCatalog, name, artist) == null) {
                        songCatalog.add(song);
                        System.out.println(admin.addSongs(song));
                    } else {
                        System.out.println("Song already exists");
                    }
                    break;
                }

                case 2: { 
                    System.out.print("Song to modify — name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("End"); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("End"); break; }

                    Song song = findSong(songCatalog, name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist");
                        if (!askYesNo(sc, "Create it :")) break;

                        double duration = readDouble(sc, "Duration in seconds (0 = cancel)  : ");
                        if (duration == 0) { System.out.println("Cancelled"); break; }
                        
                        song = new Song(name, artist, duration, 0);
                        songCatalog.add(song);
                        System.out.println(admin.addSongs(song));
                    }

                    System.out.println("\nWhat do you want to change ?");
                    System.out.println("0) Back");
                    System.out.println("1) Name");
                    System.out.println("2) Artist");
                    System.out.println("3) Duration");
                    int sub = readInt(sc, "Choice: ");
                    if (sub == 0) break;

                    switch (sub) {
                        case 1: {
                            System.out.print("New name (0 = cancel): ");
                            String newName = sc.nextLine().trim();
                            if ("0".equals(newName)) { System.out.println("End"); break; }
                            if (findSong(songCatalog, newName, song.artist) != null) {
                                System.out.println("This song already exist");
                                break;
                            }
                            song.name = newName;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            break;
                        }
                        case 2: {
                            System.out.print("New artist (0 = cancel) : ");
                            String newArtist = sc.nextLine().trim();
                            if ("0".equals(newArtist)) { System.out.println("end"); break; }
                            if (findSong(songCatalog, song.name, newArtist) != null) {
                                System.out.println("The song already exists");
                                break;
                            }
                            song.artist = newArtist;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            break;
                        }
                        case 3: {
                            double nd = readDouble(sc, "New duration in seconds (0 = cancel): ");
                            if (nd == 0) { System.out.println("End"); break; }
                            song.duration = nd;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            break;
                        }

                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    break;
                }

                case 3: { 
                    System.out.print("Song to delete — name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("cancelled"); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("cancelled"); break; }

                    Song song = findSong(songCatalog, name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist");
                        if (askYesNo(sc, "Add it ?")) {
                            double duration = readDouble(sc, "Duration in seconds (0 = cancel): ");
                            if (duration == 0) { System.out.println("cancelled"); break; }
                            Song created = new Song(name, artist, duration, 0);
                            songCatalog.add(created);
                            System.out.println(admin.addSongs(created));
                        }
                        break;
                    }

                    if (!askYesNo(sc, "Delete ? \"" + song.name + "?")) {
                        System.out.println("Cancelled"); break;
                    }
                    songCatalog.remove(song);
                    System.out.println(admin.deleteSongs(song));
                    break;
                }

                case 4: { 
                    System.out.println("1) Upgrade user to Premium");
                    System.out.println("2) Downgrade premium to Free");
                    System.out.println("3) Delete user account");
                    int action = readInt(sc, "Choice : ");

                    System.out.print("User name : ");
                    String uname = sc.nextLine().trim();
                    int uid = readInt(sc, "User id : ");

                    
                    AllUser.UserInfo match = null;
                    for (int i = 0; i < allUser.getUsers().size(); i++) {
                        AllUser.UserInfo u = allUser.getUsers().get(i);
                        if (u.getId() == uid && u.getName().equalsIgnoreCase(uname)) { 
                            match = u; 
                            break; 
                        }
                    }
                    
                    if (match == null) {
                        System.out.println("No account found");
                        break;
                    }
                    
                    if ("Admin".equals(match.getAccountType())) {
                        System.out.println("Cannot manage another Admin.");
                        break;
                    }





                    switch (action) {
                        case 1:
                            if (!"User".equals(match.getAccountType())) {
                                System.out.println("Must be a User.");
                                break;
                            }
                            PremiumUser neo = admin.upgradeToPremium(new User(uname, uid));
                            System.out.println("Upgraded " + neo.getName() + " to Premium.");
                            break;
                            
                        case 2: 
                            if (!"PremiumUser".equals(match.getAccountType())) {
                                System.out.println("Must be a PremiumUser.");
                                break;
                            }
                            User neo2 = admin.downgradeToFree(new PremiumUser(uname, uid));
                            System.out.println("Downgraded " + neo2.getName() + " to Free.");
                            break;
                            
                        case 3: 
                            if (askYesNo(sc, "Delete user " + uname + "?")) {
                                String result = admin.deleteUserAccount(allUser, uname, uid);
                                System.out.println(result);
                            } else {
                                System.out.println("cancelled.");
                            }
                            break;
                            
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    break;
                }

                case 5: { 
                    boolean inSettings = true;
                    while (inSettings) {
                        System.out.println("\n--- Admin Settings ---");
                        System.out.println("Admin: " + admin.getName() + " (ID: " + admin.getAdmin_id() + ")");
                        System.out.println("Credentials: " + (admin.hasCredentials() ? "Set" : "Not set"));
                        if (admin.hasCredentials()) {
                            System.out.println("Contact: " + admin.getCredentials().getContact());
                        }
                        System.out.println("1) Set Credentials");
                        System.out.println("2) Update Password");
                        System.out.println("3) Update Contact");
                        System.out.println("4) Back to Main Menu");
                        
                        int settingChoice = readInt(sc, "Choice: ");
                        
                        switch (settingChoice) {
                            case 1: 
                                if (admin.hasCredentials()) {
                                    System.out.println("Credentials already set. Use update options instead.");
                                    break;
                                }
                                System.out.print("Enter contact (email/phone): ");
                                String contact = sc.nextLine().trim();
                                System.out.print("Enter password (min 6 characters): ");
                                String password = sc.nextLine();
                            
                                admin.setCredentials(contact, password);
                                System.out.println("Credentials changed");
                               
                                break;
                                
                            case 2: 
                                if (!admin.hasCredentials()) {
                                    System.out.println("No credentials set");
                                    break;
                                }
                                System.out.print("Enter old password: ");
                                String oldPass = sc.nextLine();
                                System.out.print("Enter new password (min 6 characters): ");
                                String newPass = sc.nextLine();
                                boolean updated = admin.updatePassword(oldPass, newPass);
                                if (updated) {
                                    System.out.println("Password updated");
                                }
                                break;
                                
                            case 3: 
                                if (!admin.hasCredentials()) {
                                    System.out.println("No credentials set.");
                                    break;
                                }
                                System.out.print("Enter new contact (email/phone) : ");
                                String newContact = sc.nextLine().trim();
                                boolean contactUpdated = admin.updateContact(newContact);
                                if (contactUpdated) {
                                    System.out.println("Contact updated");
                                }
                                break;
                                
                            case 4: 
                                inSettings = false;
                                break;
                                
                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    break;
                }

                case 6: { 
                    admin.logout();
                    System.out.println("Admin log out");
                    in = false;
                    break;
                }

                case 7: { 
                    System.out.print("Type YES to confirm deletion : ");
                    String conf = sc.nextLine().trim();
                    if ("YES".equals(conf)) {
                        String result = admin.deleteAccount();
                        System.out.println(result);
                        in = false;
                    } else {
                        System.out.println("Cancelled");
                    }
                    break;
                }

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}