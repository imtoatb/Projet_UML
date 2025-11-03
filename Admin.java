import java.util.Scanner;

public class Admin {
    private String name;
    private int admin_id;
    private boolean connected;
    private int password = 12345;
    Scanner sc = new Scanner(System.in);


    public Admin(String name, int admin_id){
        this.name = name;
        this.admin_id = admin_id;
    }
    public Admin(){

    }

    public int getAdmin_id() {
        return admin_id;
    }

    public int getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


    public String register(String name, int admin_id) {
        this.name = name;
        this.admin_id = admin_id;
        return "Your account has been created";
    }

    public void createAccount(String name, int admin_id, int password){


        if (is_admin(password)){
            System.out.println("Please enter your name : ");
            this.name = sc.nextLine();
            System.out.println("Please enter your id : ");
            this.admin_id = sc.nextInt();
        }
        else{
            System.out.println("You are not a admin");
        }



        //Faire une méthode qui permet de s'inscrire en insriant une id et son nom(faire attention que l'id n'est pas déja pris)
    }

    public boolean is_admin(int password){
        System.out.println("If you are a new admin, please enter the password : ");
        int attempt = sc.nextInt();
        return (attempt == this.password);
    }


    public void login(int admin_id, boolean connected){
        System.out.println("Please enter your ID : ");
        int admin_id_try = sc.nextInt();
        if (admin_id_try == this.admin_id){
            this.connected = true;
        }
        else{
            System.out.println("It's incorrect");
        }

//Faire une méthode qui permet de se connecter en entrant d'id

    }

    public String is_connected(boolean connected){
        if (this.connected){
            return "Hello " + name + ", you are already connected"; // Mettre le nom de l'utilsateur au début
        }
        else{
            return "Hello listener, you are not connected";
            //Proposer à l'utilsateur de se connecter si il veut appeler la méthode login
        }

    }

    public void logout(int admin_id, boolean connected){
        System.out.println("Please enter your ID : ");
        int admin_id_try = sc.nextInt();
        if (admin_id_try == this.admin_id){
            this.connected = false;
        }
        else{
            System.out.println("It's incorrect");
        }



//Faire une méthode qui permet de se deconecter

    }

    public void deleteAccount(int admin_id, boolean connected, String name){
        System.out.println("Are you sure that you want delete ypur account ?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int choice = sc.nextInt();

        do{
            System.out.println("Please choose between 1 and 2");
        }while((choice != 1) || (choice != 2));

        switch(choice){
            case 1:
                this.admin_id = 0;
                this.name = null;
                this.connected = false;
                System.out.println("You account is deleted");
            case 2 :
                System.out.println("Good choice !");
        }
        //Faire une méthode qui permet de se desincrire en mettant tout les attributs à nulles

    }

    public String addSongs(Song song){
        return "Le son " + song.name + " de " + song.artist +  "a été ajouté a l'application";
        //Ajouter un objet de type Song
    }

    public void addInformation(){
        //Modifier l'un des attributs de l'objet
    }

    public String deleteSongs(Song song){
        return "Le son " + song.name + " de " + song.artist +  "a été supprimé de l'application";
    }





}
