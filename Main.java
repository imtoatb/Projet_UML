import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        int id = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("************** Welcome on MAHE Music **************");
        System.out.println("1. Register");
        System.out.println("2. Log In");
        System.out.println("***************************************************");

        int choice = sc.nextInt();
        sc.nextLine();                      // vider le buffer après nextInt()

        switch(choice){

            case 1:
                System.out.println("Create your account !");
                System.out.println("Enter your name:");
                String name = sc.nextLine();

                System.out.println("Who are you ? (Admin, User, PremiumUser)");
                String status = sc.nextLine();

                if (status.equalsIgnoreCase("Admin")){
                    Admin admin = new Admin(name, id);
                    System.out.println("Not available for the moment");
                }
                else if (status.equalsIgnoreCase("User")){
                    User user = new User(name, id);
                    System.out.println("Welcome " + user.getName() + ", here's your id : " + user.getId());
                }
                else if (status.equalsIgnoreCase("PremiumUser")){
                    PremiumUser pUser = new PremiumUser(name, id);
                    System.out.println("Welcome " + pUser.getName() + ", here's your id : " + pUser.getId());
                }
                else{
                    System.out.println("Something went wrong");
                }

                id++;
                break;

            case 2:
                System.out.println("Login system not implemented yet.");
                break;

            default:
                System.out.println("Invalid choice");
        }

        sc.close();
    }
}