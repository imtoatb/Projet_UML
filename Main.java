import java.util.Scanner;

class Main {
    private static int id = 0;
    private static AllUser allUser = new AllUser();
    private static Scanner sc = new Scanner(System.in);
    
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
        System.out.println("⚠️  Administrator features are currently");
        System.out.println("    under construction and development.");
        System.out.println("");
        System.out.println("Expected availability: Next system update");
        System.out.println("Current status: Limited demo access only");
        System.out.println("========================================");
        
        System.out.print("\nWould you like to view demo options? (yes/no): ");
        String response = sc.nextLine().toLowerCase();
        
        if (response.equals("yes") || response.equals("y")) {
            // Créer un admin temporaire pour la démo
            Admin tempAdmin = new Admin("System Administrator", 9999);
            AdminMainMenu.displayAdminMenu(tempAdmin, sc);
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
                createUserAccount(name, "User");
                break;
            case 2:
                createUserAccount(name, "PremiumUser");
                break;
            case 3:
                createLimitedAdminAccount(name);
                break;
            default:
                System.out.println("Invalid account type selection.");
        }
    }
    
    private static void createLimitedAdminAccount(String name) {
        System.out.println("\nADMINISTRATOR REGISTRATION NOTICE");
        System.out.println("Full administrator registration is currently disabled.");
        System.out.println("Your account will be created as a regular user.");
        System.out.println("Admin features will be available in the next update.");
        
        // Créer un compte utilisateur normal à la place
        createUserAccount(name, "User");
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
                System.out.println(user.logIn());
                UserMainMenu.displayUserMenu(user, sc);
                break;
            case "PremiumUser":
                PremiumUser pUser = new PremiumUser(userInfo.getName(), userInfo.getId());
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
        System.out.println("\nADMINISTRATOR LOGIN NOTICE");
        System.out.println("Full administrator login is currently disabled.");
        System.out.println("Please use the demo access from the main menu.");
        System.out.println("Your admin account: " + userInfo.getName() + " (ID: " + userInfo.getId() + ")");
        System.out.println("Full access will be available in the next update.");
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
}