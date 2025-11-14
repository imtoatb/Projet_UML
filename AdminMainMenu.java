import java.util.List;
import java.util.Scanner;

public final class AdminMainMenu {

    // ---- local helpers (work on provided catalog) ----
    private static Song findSong(List<Song> catalog, String name, String artist) {
        for (Song s : catalog) {
            if (s.name.equalsIgnoreCase(name) && s.artist.equalsIgnoreCase(artist)) return s;
        }
        return null;
    }

    private static boolean askYesNo(Scanner sc, String prompt) {
        System.out.print(prompt + " (y/n): ");
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

    // ---- main admin menu ----
    public static void displayAdminMenu(Admin admin, Scanner sc, AllUser allUser, List<Song> songCatalog) {
        if (!admin.is_connected()) {
            System.out.println("Please log in as Admin first.");
            return;
        }
        boolean in = true;
        while (in) {
            System.out.println("\n********** Admin Menu **********");
            System.out.println("0) Back");
            System.out.println("1) Add Song");
            System.out.println("2) Modify Song");
            System.out.println("3) Delete Song");
            System.out.println("4) Manage Users (Upgrade/Downgrade)");
            System.out.println("5) Logout");
            System.out.println("6) Delete Admin Account");
            System.out.println("********************************");

            int choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 0:
                    in = false;
                    break;

                case 1: { // Add Song
                    System.out.print("Song name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("Cancelled."); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("Cancelled."); break; }

                    System.out.print("Duration (seconds, 0 = cancel): ");
                    double duration;
                    try {
                        duration = sc.nextDouble(); sc.nextLine();
                        if (duration == 0) { System.out.println("Cancelled."); break; }
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("Invalid duration."); break;
                    }

                    Song song = new Song(name, artist, duration, 0);
                    if (findSong(songCatalog, name, artist) == null) songCatalog.add(song);
                    System.out.println(admin.addSongs(song));
                } break;

                case 2: { // Modify Song
                    System.out.print("Song to modify — name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("Cancelled."); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("Cancelled."); break; }

                    Song song = findSong(songCatalog, name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist in the catalog.");
                        if (!askYesNo(sc, "Do you want to create it now?")) break;

                        System.out.print("Duration in seconds (0 = cancel): ");
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

                    System.out.println("\nWhat do you want to change?");
                    System.out.println("0) Back");
                    System.out.println("1) Name");
                    System.out.println("2) Artist");
                    System.out.println("3) Duration");
                    System.out.println("4) Playing Time");
                    int sub = readInt(sc, "Choice: ");
                    if (sub == 0) break;

                    switch (sub) {
                        case 1: {
                            System.out.print("New name (0 = cancel): ");
                            String newName = sc.nextLine().trim();
                            if ("0".equals(newName)) { System.out.println("Cancelled."); break; }
                            if (findSong(songCatalog, newName, song.artist) != null) {
                                System.out.println("A song with that name and the same artist already exists.");
                                break;
                            }
                            song.name = newName;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                        } break;
                        case 2: {
                            System.out.print("New artist (0 = cancel): ");
                            String newArtist = sc.nextLine().trim();
                            if ("0".equals(newArtist)) { System.out.println("Cancelled."); break; }
                            if (findSong(songCatalog, song.name, newArtist) != null) {
                                System.out.println("A song with that artist and the same name already exists.");
                                break;
                            }
                            song.artist = newArtist;
                            System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                        } break;
                        case 3: {
                            System.out.print("New duration in seconds (0 = cancel): ");
                            try {
                                double nd = sc.nextDouble(); sc.nextLine();
                                if (nd == 0) { System.out.println("Cancelled."); break; }
                                song.duration = nd;
                                System.out.println("Updated: " + song.name + " - " + song.artist + " (" + song.duration + "s)");
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("Invalid duration. Cancelled.");
                            }
                        } break;
                        case 4: {
                            System.out.print("New playing time in seconds (0 = cancel): ");
                            try {
                                double np = sc.nextDouble(); sc.nextLine();
                                if (np == 0) { System.out.println("Cancelled."); break; }
                                song.playingtime = np;
                                System.out.println("Updated playing time: " + song.name + " at " + song.playingtime + "s");
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("Invalid playing time. Cancelled.");
                            }
                        } break;
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                } break;

                case 3: { // Delete Song
                    System.out.print("Song to delete — name (0 = cancel): ");
                    String name = sc.nextLine().trim();
                    if ("0".equals(name)) { System.out.println("Cancelled."); break; }

                    System.out.print("Artist (0 = cancel): ");
                    String artist = sc.nextLine().trim();
                    if ("0".equals(artist)) { System.out.println("Cancelled."); break; }

                    Song song = findSong(songCatalog, name, artist);
                    if (song == null) {
                        System.out.println("This song does not exist in the catalog.");
                        if (askYesNo(sc, "Do you want to create it instead?")) {
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

                    if (!askYesNo(sc, "Are you sure you want to delete \"" + song.name + "\" by " + song.artist + "\"?")) {
                        System.out.println("Cancelled."); break;
                    }
                    songCatalog.remove(song);
                    System.out.println(admin.deleteSongs(song));
                } break;

                case 4: { // Manage users
                    System.out.println("1) Upgrade user to Premium");
                    System.out.println("2) Downgrade premium to Free");
                    int action = readInt(sc, "Choice: ");

                    System.out.print("User name: ");
                    String uname = sc.nextLine().trim();
                    int uid = readInt(sc, "User id: ");

                    // check existence in AllUser
                    AllUser.UserInfo match = null;
                    for (AllUser.UserInfo u : allUser.getUsers()) {
                        if (u.getId() == uid && u.getName().equalsIgnoreCase(uname)) { match = u; break; }
                    }
                    if (match == null) {
                        System.out.println("No account found with this name and id.");
                        break;
                    }
                    if ("Admin".equals(match.getAccountType())) {
                        System.out.println("Operation denied: cannot upgrade/downgrade an Admin.");
                        break;
                    }

                    if (action == 1) { // upgrade
                        if (!"User".equals(match.getAccountType())) {
                            System.out.println("Upgrade denied: must be a 'User'.");
                            break;
                        }
                        PremiumUser neo = admin.upgradeToPremium(new User(uname, uid));
                        System.out.println("Upgraded " + neo.getName() + " to Premium.");
                    } else if (action == 2) { // downgrade
                        if (!"PremiumUser".equals(match.getAccountType())) {
                            System.out.println("Downgrade denied: must be a 'PremiumUser'.");
                            break;
                        }
                        User neo = admin.downgradeToFree(new PremiumUser(uname, uid));
                        System.out.println("Downgraded " + neo.getName() + " to Free.");
                    } else {
                        System.out.println("Invalid choice.");
                    }
                } break;

                case 5: {
                    admin.logout();
                    System.out.println("Admin logged out.");
                    in = false;
                } break;

                case 6: {
                    System.out.print("Type YES to confirm deletion: ");
                    String conf = sc.nextLine().trim();
                    if ("YES".equals(conf)) {
                        admin.deleteAccount();
                        System.out.println("Admin account deleted.");
                        in = false;
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                } break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
