import java.util.List;

public class User {
    String name;
    int userId;
    boolean connected;

    int currentSongIndex = 0;
    List<Song> currentSongList; 
    int volume = 50;

    boolean accountActive = true;
    private String contact;
    private String password;
    
    public User(String name, int userId){
        this.name = name;
        this.userId = userId;
        this.connected = false;
        this.currentSongIndex = 0;
        this.currentSongList = null;
        this.volume = 50;
        this.accountActive = true;
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
        if (!accountActive) {
            return "Cannot login: This account has been deleted.";
        }
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


    public String deleteAccount() {
        if (!accountActive) {
            return "This account has already been deleted.";
        }
        
   
        if (connected) {
            connected = false;
        }
        
    
        this.name = "Deleted User";
        this.currentSongList = null;
        this.currentSongIndex = 0;
        this.volume = 50;
        this.accountActive = false;
        
        return "Your account has been successfully deleted";
    }
    

    public boolean isAccountActive() {
        return accountActive;
    }
    
    
 
    
    public String playSong(Song song){
        if (!accountActive) {
            return "Cannot play song: This account has been deleted";
        }

        return song.name + " is playing";
    }

    public String pauseSong(Song song){
        if (!accountActive) {
            return "Cannot pause song: This account has been deleted";
        }

        return song.name + " has been paused at " + song.playingtime;
    }

    
    public String nextSong() {
        if (!accountActive) {
            return "Cannot play next song: This account has been deleted";
        }
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No songs available in the library\nPlease play a song first";
        }
        
        currentSongIndex = (currentSongIndex + 1) % currentSongList.size();
        Song nextSong = currentSongList.get(currentSongIndex);

        return "Next: " + nextSong.name + " - " + nextSong.artist + " is now playing";
    }
    

    public String previousSong() {
        if (!accountActive) {
            return "Cannot play previous song: This account has been deleted";
        }
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No songs available in the library\n Please play a song first";
        }
        
        currentSongIndex = (currentSongIndex - 1 + currentSongList.size()) % currentSongList.size();
        Song previousSong = currentSongList.get(currentSongIndex);
        return "Previous: " + previousSong.name + " - " + previousSong.artist + " is now playing";
    }


    
    public String getCurrentSong() {
        if (!accountActive) {
            return "Cannot get current song: This account has been deleted";
        }
        
        if (currentSongList == null || currentSongList.isEmpty()) {
            return "No song is currently playing";
        }
        
        Song currentSong = currentSongList.get(currentSongIndex);
        return "Now Playing: " + currentSong.name + " - " + currentSong.artist + " (" + currentSong.duration + "s)";
    }

    //For Volume
    public String increaseVolume() {
        if (!accountActive) {
            return "Cannot adjust volume: This account has been deleted.";
        }
        if (volume < 100) {
            
            volume += 10;
            if (volume > 100) volume = 100;
            return displayVolume();
        } 
        
        else {
            return displayVolume() + " (Volume maximum atteint!)";
        }
    }
    
    public String decreaseVolume() {
        if (!accountActive) {
            return "Cannot adjust volume: This account has been deleted.";
        }
        if (volume > 0) {
            volume -= 10;
            if (volume < 0) volume = 0;
            return displayVolume();
        } 
        else {
            return displayVolume() + " (Volume minimum atteint!)";
        }
    }
    
    public String setVolume(int newVolume) {
        if (!accountActive) {
            return "Cannot adjust volume: This account has been deleted.";
        }
        if (newVolume >= 0 && newVolume <= 100) {
            volume = newVolume;
            return displayVolume();
        } else {
            return "Volume must be between 0 and 100";
        }
    }
    

    public String displayVolume() {
        if (!accountActive) {
            return "Volume: Account deleted";
        }
        
        System.out.println('\n');
    
        int bars = volume / 10;
        StringBuilder volumeBar = new StringBuilder("[ ");
        
        for (int i = 0; i < 10; i++) {
            
            if (i < bars) {
                volumeBar.append("H");
            } 
            else {
                volumeBar.append("-");
            }
        }
        volumeBar.append(" ] ");
        
        return "Volume: " + volumeBar.toString() + volume + "%";
    }
    
    public int getVolume() {
        return volume;
    }
    
    public String getVolumeDisplay() {
        if (!accountActive) {
            return "Volume: Account deleted";
        }
        return displayVolume();
    }
    
    public void setCurrentSongList(List<Song> songList) {
        if (accountActive) {
            this.currentSongList = songList;
        }
    }
    
    public void resetSongIndex(int newIndex) {
        if (accountActive) {
            this.currentSongIndex = newIndex;
        }
    }
    
    public int getCurrentSongIndex() {
        return currentSongIndex;
    }




    // ====== CREDENTIALS (ADD-ONLY) ======

    // Set both contact & password at once
    public void setCredentials(String contact, String password) {
        this.contact = contact;
        this.password = password;
    }


    //Update the mail or the phone
    public boolean updateContact(String newContact) {
        if (newContact == null) return false;
        String c = newContact.trim();
        if (c.isEmpty()) return false;
        if (!isValidEmail(c) && !isValidPhone(c)) return false;
        this.contact = c;
        return true;
    }


    // Update password
    public boolean updatePassword(String oldPass, String newPass) {
        if (oldPass == null || newPass == null) return false;
        if (this.password == null) return false;                 // not set yet
        if (!this.password.equals(oldPass)) return false;        // wrong old
        if (!isStrongPassword(newPass)) return false;
        this.password = newPass;
        return true;
    }



    // Check password on login
    public boolean checkPassword(String attempted) {
        if (this.password == null) return attempted == null;
        return this.password.equals(attempted);
    }

    // For display (mask email/phone)
    public String maskedContact() {
        if (this.contact == null || this.contact.isEmpty()) return "(none)";
        return maskContact(this.contact);
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
        String x = s.trim();
        if (x.length() < 7 || x.length() > 20) return false;
        for (int i = 0; i < x.length(); i++) {
            char ch = x.charAt(i);
            if (!Character.isDigit(ch) && ch != '+' && ch != ' ' && ch != '-') return false;
        }
        return true;
    }


    private static boolean isStrongPassword(String s) {
        return s != null && s.length() >= 6;
    }


    private static String maskContact(String contact) {
        String c = contact.trim();
        if (isValidEmail(c)) {
            int at = c.indexOf('@');
            if (at <= 2) return "***" + c.substring(at);
            return c.substring(0, 2) + "***" + c.substring(at);
        }


        String digits = c.replaceAll("[^0-9]", "");
        String last2 = digits.length() >= 2 ? digits.substring(digits.length() - 2) : "**";
        return c.charAt(0) + "******" + last2;


    }
}