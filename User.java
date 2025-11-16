
import java.util.List;

public class User {
    private String name;
    private int userId;
    boolean connected;
    private int currentSongIndex = 0; // Nouveau champ pour suivre la chanson actuelle
    private String contact;
    private String password;

    public User(String name, int userId){
        this.name = name;
        this.userId = userId;
        this.connected = false;
        this.currentSongIndex = 0;
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

    // NOUVELLE MÉTHODE : Next Song
    public String nextSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No songs available in the library.";
        }

        currentSongIndex = (currentSongIndex + 1) % songList.size();
        Song nextSong = songList.get(currentSongIndex);
        return "▶ Next: " + nextSong.name + " - " + nextSong.artist + " is now playing";
    }

    // NOUVELLE MÉTHODE : Previous Song
    public String previousSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No songs available in the library.";
        }

        currentSongIndex = (currentSongIndex - 1 + songList.size()) % songList.size();
        Song previousSong = songList.get(currentSongIndex);
        return "◀ Previous: " + previousSong.name + " - " + previousSong.artist + " is now playing";
    }

    // NOUVELLE MÉTHODE : Get Current Song
    public String getCurrentSong(List<Song> songList) {
        if (songList == null || songList.isEmpty()) {
            return "No song is currently playing.";
        }

        Song currentSong = songList.get(currentSongIndex);
        return "Now Playing: " + currentSong.name + " - " + currentSong.artist + " (" + currentSong.duration + "s)";
    }

    // NOUVELLE MÉTHODE : Reset song index (quand on choisit une nouvelle chanson)
    public void resetSongIndex(int newIndex) {
        this.currentSongIndex = newIndex;
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