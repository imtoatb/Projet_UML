
public class Admin {
    private String name;
    private int admin_id;
    private boolean connected;
    private Credential credentials; 

    public Admin(String name, int admin_id){
        this.name = name;
        this.admin_id = admin_id;
        this.connected = false;
        this.credentials = null; 
    }

    public Admin(String name, int admin_id, String contact, String password){
        this.name = name;
        this.admin_id = admin_id;
        this.connected = false;
        this.credentials = new Credential(name, admin_id, contact, password, "Admin");
    }

   
    public static Admin createAccount(String name, int admin_id, String contact, String password){
        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact information required");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        return new Admin(name, admin_id, contact, password);
    }

   
    public boolean login(int providedId, String password){
        if (this.credentials == null) {
            System.out.println("Cannot login");
            return false;
        }
        
        boolean idOk = (this.admin_id == providedId);
        boolean passwordOk = this.credentials.getPassword().equals(password);
        
        this.connected = idOk && passwordOk;
        
        if (this.connected) {
            System.out.println("Admin " + name + " login");
        } 
        
        else {
            if (!idOk) {
                System.out.println("Invalid admin ID");
            } 
            else if (!passwordOk) {
                System.out.println("Invalid password");
            }
        }
        return this.connected;
    }

   
    public boolean login(int providedId){
        if (this.credentials != null) {
            System.out.println("This admin requires password authentication. Use login(id, password) instead.");
            return false;
        }
        boolean ok = (this.admin_id == providedId);
        this.connected = ok;
        if (ok) {
            System.out.println("Admin " + name + " logged in");
        } else {
            System.out.println("Invalid admin ID");
        }
        return ok;
    }

    public void logout(){
        if (this.connected) {
            System.out.println("Admin " + name + " logout");
        }
        this.connected = false;
    }

    public String deleteAccount(){
        if (this.name == null) {
            return "Admin account already deleted";
        }
        String deletedName = this.name;
        this.name = null;
        this.admin_id = 0;
        this.connected = false;
        this.credentials = null;
        return "Admin account '" + deletedName + "' has been deleted";
    }

    
    public void setCredentials(String contact, String password) {
        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact cannot be empty");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        this.credentials = new Credential(this.name, this.admin_id, contact, password, "Admin");
        System.out.println("Credentials set for admin " + this.name);
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        if (this.credentials == null) {
            System.out.println("No credentials yet");
            return false;
        }
        if (!this.credentials.getPassword().equals(oldPassword)) {
            System.out.println("Old password is incorrect");
            return false;
        }
        if (newPassword.length() < 6) {
            System.out.println("New password must be at least 6 characters");
            return false;
        }
        
        this.credentials = new Credential(this.name, this.admin_id, this.credentials.getContact(), newPassword, "Admin");
        System.out.println("Password updated " + this.name);
        return true;
    }

    public boolean updateContact(String newContact) {
        if (this.credentials == null) {
            System.out.println("No credentials");
            return false;
        }
        if (newContact == null || newContact.trim().isEmpty()) {
            System.out.println("Contact can't be empty.");
            return false;
        }
        
        this.credentials = new Credential(this.name, this.admin_id, newContact, this.credentials.getPassword(), "Admin");
        System.out.println("Contact information updated " + this.name);
        return true;
    }



    public boolean hasCredentials() {
        return this.credentials != null;
    }

    

    
    public PremiumUser upgradeToPremium(User u){
        if (!connected) throw new IllegalStateException("Admin must be logged in");
        if (u == null) throw new IllegalArgumentException("User cannot be null.");
        if (!u.isAccountActive()) throw new IllegalArgumentException("Cannot upgrade deleted user account");
        
        System.out.println("Upgrading user " + u.getName() + " to premium");
        return new PremiumUser(u.getName(), u.getId());
    }

    public User downgradeToFree(PremiumUser p){
        if (!connected) throw new IllegalStateException("Admin must be logged");
        if (p == null) throw new IllegalArgumentException("PremiumUser cannot be null");
        if (!p.isAccountActive()) throw new IllegalArgumentException("Cannot downgrade deleted user account");
        
        System.out.println("Downgrading premium user " + p.getName() + " to user");
        return new User(p.getName(), p.getId());
    }

    public String deleteUserAccount(AllUser allUser, String userName, int userId) {
        if (!connected) return "Admin must be logged in";
        
        for (AllUser.UserInfo user : allUser.getUsers()) {
            if (user.getName().equals(userName) && user.getId() == userId) {
                if ("Admin".equals(user.getAccountType())) {
                    return "Cannot delete another admin account";
                }
                user.deleteAccount();
                return "User account " + userName + " (ID: " + userId + ") has been deleted.";
            }
        }
        return "User account not found.";
    }




  
    public String addSongs(Song song){
        if (!connected) return "Admin must be logged in";
        return song.name + " by " + song.artist + " added";
    }

    public String deleteSongs(Song song){
        if (!connected) return "Admin must be logged in";
        return "The song " + song.name + " by " + song.artist + " deleted";
    }

    public void addInformation(){ 
  
        System.out.println("Additional information");
    }

  
    public String getName(){ 
        return name != null ? name : "[Deleted Account]"; 
    }
    
    public int getAdmin_id(){ 
        return admin_id; 
    }
    
    public boolean is_connected(){ 
        return connected && name != null;
    }

    public Credential getCredentials() {
        return credentials;
    }

 

    @Override
    public String toString() {
        if (name == null) {
            return "Admin[DELETED]";
        }
        return String.format("Admin{name='%s', id=%d, connected=%s, hasCredentials=%s}", 
            name, admin_id, connected, credentials != null);
    }
}