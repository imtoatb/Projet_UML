import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main {
    private static int id = 0;
    private static AllUser allUser = new AllUser();
    private static Scanner sc = new Scanner(System.in);
    private static List<Song> songCatalog = new ArrayList<>();
    private static final List<Credential> credentials = new ArrayList<>();



    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    displaySystemInfo();
                    break;
                case 4:
                    showAdminNotice();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using MusicHub System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("          WELCOME TO MUSICHUB");
        System.out.println("========================================");
        System.out.println("1. Register New Account");
        System.out.println("2. Login to Existing Account");
        System.out.println("3. System Information");
        System.out.println("4. Administrator Access");
        System.out.println("5. Exit System");
        System.out.println("========================================");
    }
    
    private static void showAdminNotice() {
        System.out.println("\n========================================");
        System.out.println("       ADMINISTRATOR ACCESS");
        System.out.println("========================================");
        System.out.println("   Administrator features are currently");
        System.out.println("    under construction and development.");
        System.out.println("");
        System.out.println("Expected availability: Next system update");
        System.out.println("Current status: Limited demo access only");
        System.out.println("========================================");

        System.out.print("\nWould you like to view demo options? (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase();

        if (response.equals("yes") || response.equals("y")) {
            // Create a temporary admin for the demo
            Admin tempAdmin = Admin.createAccount("System Administrator", 9999);
            tempAdmin.login(9999); // mark as connected


            AdminMainMenu.displayAdminMenu(tempAdmin, sc, allUser, songCatalog);
        } else {
            System.out.println("Returning to main menu...");
        }
    }
    
    private static void displaySystemInfo() {
        System.out.println("\nSYSTEM INFORMATION");
        System.out.println("Total Songs in Library: " + MusicDatabase.getTotalSongs());
        System.out.println("Total Artists: " + MusicDatabase.getTotalArtists());
        System.out.println("Registered Users: " + allUser.getUsers().size());
        System.out.println("Admin System: Under Construction");
    }
    
    private static void registerUser() {
        System.out.println("\nACCOUNT REGISTRATION");
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }
        
        System.out.println("\nSelect account type:");
        System.out.println("1. Regular User");
        System.out.println("2. Premium User");
        System.out.println("3. Administrator (Limited Access)");
        
        int typeChoice = getIntInput("Choose account type (1-3): ");

        switch (typeChoice) {
            case 1:
                String contact;
                while (true) {
                    System.out.print("Enter your email or your phone: ");
                    contact = sc.nextLine().trim();
                    if (isValidEmail(contact) || isValidPhone(contact)) break;
                    System.out.println("Please enter a valid email address or phone number.");
                }
                String password;
                do {
                    System.out.print("Password: ");
                    password = sc.nextLine().trim();
                    if (password.isEmpty()) System.out.println("Value cannot be empty.");
                } while (password.isEmpty());

                createUserAccount(name, "User");

                credentials.add(new Credential(name, id - 1, contact, password, "User"));
                System.out.println("User registered with credentials.");
                break;
            case 2:

                while (true) {
                    System.out.print("Enter your email or your phone: ");
                    contact = sc.nextLine().trim();
                    if (isValidEmail(contact) || isValidPhone(contact)) break;
                    System.out.println("Please enter a valid email address or phone number.");
                }

                do {
                    System.out.print("Password: ");
                    password = sc.nextLine().trim();
                    if (password.isEmpty()) System.out.println("Value cannot be empty.");
                } while (password.isEmpty());

                createUserAccount(name, "PremiumUser");
                credentials.add(new Credential(name, id - 1, contact, password, "PremiumUser"));
                System.out.println("Premium user registered with credentials.");
                break;
            case 3:
                createAdminAccount(name);
                break;
            default:
                System.out.println("Invalid account type selection.");
        }
    }

    private static void createAdminAccount(String name) {
        allUser.addUser(name, id, "Admin");
        System.out.println("Admin account created for " + name + " (ID: " + id + ")");
        id++;
    }


    private static void createUserAccount(String name, String accountType) {
        if (accountType.equals("User")) {
            User user = new User(name, id);
            allUser.addUser(name, id, "User");
            System.out.println("Welcome " + user.getName() + "! Your User ID: " + user.getId());
        } else if (accountType.equals("PremiumUser")) {
            PremiumUser pUser = new PremiumUser(name, id);
            allUser.addUser(name, id, "PremiumUser");
            System.out.println("Welcome Premium User " + pUser.getName() + "! Your User ID: " + pUser.getId());
        }
        id++;
    }

    private static void loginUser() {
        System.out.println("\nUSER LOGIN");

        System.out.print("Enter your mail or your phone : ");
        String contact = sc.nextLine().trim();

        if (!contact.isEmpty()) {
            System.out.print("Password: ");
            String password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
                return;
            }

            Credential cred = findCredentialByContactPassword(contact, password);
            if (cred == null) {
                System.out.println("Error: Invalid contact or password.");
                return;
            }

            AllUser.UserInfo match = findUserInfoByCredential(cred);
            if (match == null) {
                System.out.println("Error: Account not found in registry. Please register again.");
                return;
            }

            handleUserLogin(match);
            return;
        }

        System.out.print("Enter your name: ");
        String loginName = sc.nextLine().trim();
        int loginId = getIntInput("Enter your ID: ");

        boolean found = false;
        for (AllUser.UserInfo userInfo : allUser.getUsers()) {
            if (userInfo.getName().equalsIgnoreCase(loginName) && userInfo.getId() == loginId) {
                found = true;
                handleUserLogin(userInfo);
                break;
            }
        }

        if (!found) {
            System.out.println("Error: No account found with these credentials.");
        }
    }
    
    private static void handleUserLogin(AllUser.UserInfo userInfo) {
        switch (userInfo.getAccountType()) {
            case "User":
                User user = new User(userInfo.getName(), userInfo.getId());
                fillCredentialsIfAny(user);
                System.out.println(user.logIn());
                UserMainMenu.displayUserMenu(user, sc);
                break;
            case "PremiumUser":
                PremiumUser pUser = new PremiumUser(userInfo.getName(), userInfo.getId());
                fillCredentialsIfAny(pUser);
                System.out.println(pUser.logIn());
                PremiumUserMainMenu.displayPremiumUserMenu(pUser, sc);
                break;
            case "Admin":
                handleAdminLogin(userInfo);
                break;
            default:
                System.out.println("Error: Unknown account type.");
        }
    }
    
    private static void handleAdminLogin(AllUser.UserInfo userInfo) {
        Admin admin = Admin.createAccount(userInfo.getName(), userInfo.getId());
        if (admin.login(userInfo.getId())) {
            System.out.println("Welcome, Admin " + admin.getName() + "!");
            AdminMainMenu.displayAdminMenu(admin, sc, allUser, songCatalog); // pass registry + catalog
        } else {
            System.out.println("Invalid admin id.");
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = sc.nextInt();
                sc.nextLine();
                return input;
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number.");
                sc.nextLine();
            }
        }
    }




    // Attach contact/password to the freshly created object if we have them
    private static void fillCredentialsIfAny(Object obj) {
        if (obj instanceof PremiumUser) {
            PremiumUser p = (PremiumUser) obj;
            for (Credential c : credentials) {
                if (c.id == p.getId() && "PremiumUser".equals(c.type)) {
                    p.setCredentials(c.contact, c.password);
                    break;
                }
            }
        } else if (obj instanceof User) {
            User u = (User) obj;
            for (Credential c : credentials) {
                if (c.id == u.getId() && "User".equals(c.type)) {
                    u.setCredentials(c.contact, c.password);
                    break;
                }
            }
        }
    }

    private static Credential findCredentialByContactPassword(String contact, String password) {
        if (contact == null || password == null) return null;
        for (Credential c : credentials) {
            String cContact = c.getContact();
            String cPassword = c.getPassword();
            if (cContact != null && cPassword != null
                    && cContact.equalsIgnoreCase(contact)
                    && cPassword.equals(password)) {
                return c;
            }
        }
        return null;
    }

    private static AllUser.UserInfo findUserInfoByCredential(Credential cred) {
        if (cred == null) return null;
        for (AllUser.UserInfo u : allUser.getUsers()) {
            if (u.getId() == cred.getId()
                    && u.getName().equalsIgnoreCase(cred.getName())
                    && u.getAccountType().equals(cred.getType())) {
                return u;
            }
        }
        return null;
    }


    private static boolean isValidEmail(String s) {
        if (s == null) return false;
        String x = s.trim();
        int at = x.indexOf('@');
        int dot = x.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < x.length() - 1;
    }

    private static boolean isValidPhone(String s) {
        if (s == null) return false;
        String digits = s.replaceAll("[^0-9]", "");
        return digits.length() >= 7 && digits.length() <= 20;
    }

   




}