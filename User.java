public class User {
    private String name;
    private int userId;
    boolean connected;
    
    public User(String name, int userId){
        this.name = name;
        this.userId = userId;
        this.connected = false;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.userId;
    }

    public String register(String name, int userId){
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
            return "You're now on your User account";
        }
    }

    public String logOut(){
        if (connected == false){
            return "A problem has occur, please reach the admin for more informations";
        }
        else{
            connected = false;
            return "You've been disconnected";
        }
    }

    public String playSong(Song song){
        return song.name + " is playing";
    }

    public String pauseSong(Song song){
        return song.name + " has been paused at " + song.playingtime;
    }
}