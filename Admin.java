// Admin.java (sans mot de passe)
public class Admin {
    private String name;
    private int admin_id;
    private boolean connected;

    public Admin() {}

    public Admin(String name, int admin_id){
        this.name = name;
        this.admin_id = admin_id;
        this.connected = false;
    }

    // ---- Création / Login/Logout / Delete ----
    public static Admin createAccount(String name, int admin_id){
        return new Admin(name, admin_id);
    }

    // Login par id (même logique que tes autres types — ici on compare l'id fourni)
    public boolean login(int providedId){
        boolean ok = (this.admin_id == providedId);
        this.connected = ok;
        return ok;
    }

    public void logout(){
        this.connected = false;
    }

    public void deleteAccount(){
        this.name = null;
        this.admin_id = 0;
        this.connected = false;
    }

    // ---- Gestion utilisateurs ----
    public PremiumUser upgradeToPremium(User u){
        if (!connected) throw new IllegalStateException("Admin must be logged in to manage users.");
        if (u == null) throw new IllegalArgumentException("User cannot be null.");
        return new PremiumUser(u.getName(), u.getId());
    }

    public User downgradeToFree(PremiumUser p){
        if (!connected) throw new IllegalStateException("Admin must be logged in to manage users.");
        if (p == null) throw new IllegalArgumentException("PremiumUser cannot be null.");
        return new User(p.getName(), p.getId());
    }

    // ---- Helpers pour tes menus existants ----
    public String addSongs(Song song){
        return song.name + " by " + song.artist + " has been added to MAHE Music";
    }

    public void addInformation(){ /* placeholder conservé */ }

    public String deleteSongs(Song song){
        return "The song " + song.name + " by " + song.artist + " has been deleted from MAHE Music";
    }

    // ---- Getters ----
    public String getName(){ return name; }
    public int getAdmin_id(){ return admin_id; }
    public boolean is_connected(){ return connected; }
}
