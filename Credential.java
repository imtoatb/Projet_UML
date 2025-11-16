public class Credential {
    String name;      // pour info
    int id;           // même id que dans AllUser
    String contact;   // email ou téléphone
    String password;  // (plain text pour la démo)
    String type;      // "User" | "PremiumUser"
    Credential(String name, int id, String contact, String password, String type) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }
}
