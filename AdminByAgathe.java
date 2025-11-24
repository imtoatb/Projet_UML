import java.util.List;

public class AdminByAgathe {
    private String name;
    private int admin_id;
    private boolean connected;


    public AdminByAgathe(String name, int admin_id){
        this.name = name;
        this.admin_id = admin_id;
        this.connected = false;
    }


    
    public static Admin createAccount(String name, int admin_id){

        return new Admin(name, admin_id);
    }



    public boolean login(int providedId){

        boolean ok = (this.admin_id == providedId);
        this.connected = ok;
        if (ok) {
            System.out.println("Admin " + name + " logged in successfully");
        } else {
            System.out.println("Invalid admin ID");
        }
        return ok;
    }

    public void logout(){
        if (this.connected) {
            System.out.println("Admin " + name + " logged out");
        }
        this.connected = false;
    }

    public String deleteAccount(){

        String deletedName = this.name;
        this.name = null;
        this.admin_id = 0;
        this.connected = false;
        return "Admin account '" + deletedName + "' has been deleted";
    }



    
    public PremiumUser upgradeToPremium(User u){
        if (!connected) throw new IllegalStateException("Admin must be logged in to manage users.");
        if (u == null) throw new IllegalArgumentException("User cannot be null.");
        if (!u.isAccountActive()) throw new IllegalArgumentException("Cannot upgrade deleted user account.");
        
        System.out.println("Upgrading user " + u.getName() + " to Premium User");
        return new PremiumUser(u.getName(), u.getId());

    }


    public User downgradeToFree(PremiumUser p){
        if (!connected) throw new IllegalStateException("Admin must be logged in to manage users.");
        if (p == null) throw new IllegalArgumentException("PremiumUser cannot be null.");
        if (!p.isAccountActive()) throw new IllegalArgumentException("Cannot downgrade deleted user account.");
        
        System.out.println("Downgrading premium user " + p.getName() + " to User");
        return new User(p.getName(), p.getId());
    }





    public String deleteUserAccount(AllUser allUser, String userName, int userId) {
        if (!connected) return "Admin must be logged in to manage users.";
        
        for (int i = 0; i < allUser.getUsers().size(); i++) {
            AllUser.UserInfo user = allUser.getUsers().get(i);
            if (user.getName().equals(userName) && user.getId() == userId) {
                if ("Admin".equals(user.getAccountType())) {
                    return "Cannot delete another admin account";
                }
                
                user.deleteAccount();
                return "User account " + userName + " (ID: " + userId + ") has been deleted";
            
            }
        }
        return "User account not found";
    }







    public String addSong(Song song, List<Song> songCatalog){
     
        for (int i = 0; i < songCatalog.size(); i++) {
            Song s = songCatalog.get(i);
            if (s.name.equalsIgnoreCase(song.name) && s.artist.equalsIgnoreCase(song.artist)) {
                return "Song '" + song.name + "' by " + song.artist + " already exists in catalog.";
            }
        }
        
        songCatalog.add(song);
        return "Song '" + song.name + "' by " + song.artist + " has been added to music library.";
    }


    public String deleteSong(Song song, List<Song> songCatalog){
        boolean removed = songCatalog.remove(song);
        if (removed) {
            return "Song '" + song.name + "' by " + song.artist + " has been deleted from music library.";
        } else {
            return "Song '" + song.name + "' not found in catalog.";
        }
    }


    
    public String updateSongInformation(Song song, String newName, String newArtist, double newDuration) { 
        boolean changed = false;
       
        if (newName != null && !newName.trim().isEmpty() && !newName.equals(song.name)) {                   //get rid of the space before and after a string
            song.name = newName;
            changed = true;
    
        }
        
        
        if (newArtist != null && !newArtist.trim().isEmpty() && !newArtist.equals(song.artist)) {
            song.artist = newArtist;
            changed = true;
        }
        
        if (newDuration > 0 && newDuration != song.duration) {
            song.duration = newDuration;
            changed = true;
        }
        

        if (changed) {
            return "Song updated";
        } else {
            return "No changes made to the song.";
        }
    }



    public String getName(){ return name; }
    public int getAdmin_id(){ return admin_id; }
    public boolean is_connected(){ return connected; }

}