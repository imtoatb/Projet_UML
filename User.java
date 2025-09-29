
public class User {
    String name;
    String userId;
    boolean connected;
    
    public User(String name, String useId){
        this.name = name;
        this.userId = useId;
        this.connected = false;
    }

    public String register(String name, String userId){
        this.name = name;
        this.userId = userId;
        return "Your account has been created";
    }

    public String logIn(){
        if (connected == true){
            return "You're already connected";
        }    
        else{
            connected = true;
            return "You're now on your account";
        }
    }

    public String logOut(){
        if (connected === false){
            return "A problem has occur, please reach the admin for more informations";
        }
        else{
        connected = false;
        return "You've been disconnected";
        }

    }


    
}
