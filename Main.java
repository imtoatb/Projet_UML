import java.util.Scanner;

class Main{
    public static void main(String[] args) {

        int id = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("************** Welcome on MAHE Music **************");
        System.out.println("1. Register");
        System.out.println("2. Log In");
        System.out.println("***************************************************");

        int choice = sc.nextInt();

        switch(choice){

            case 1:
                System.out.println("Create your account !");
                System.out.println("Enter your name:");
                String name = sc.nextLine();

                System.out.println("Who are you ? (Admin, User, Premium User)");
                String status = sc.nextLine();

                

                if (status == "Admin"){
                    Admin name = new Admin(name, id);
                }
                else if (status == "User"){
                    User name = new User(name, id);
                }
                else if (status == "Premium User"){
                    PremiumUser name = new PremiumUser(name, id);
                }
                else{
                    System.out.println("Something went wrong");
                }

                System.out.println("Welcome " + name + ", here's your id : " + id);
                id++;
        }


    }
}