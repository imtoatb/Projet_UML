import java.util.ArrayList;
import java.util.List;

public class AllUser {
    public static class UserInfo {
        private String name;
        private int id;
        private String accountType;

        public UserInfo(String name, int id, String accountType) {
            this.name = name;
            this.id = id;
            this.accountType = accountType;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getAccountType() {
            return accountType;
        }

        @Override
        public String toString() {
            return "UserInfo{name='" + name + "', id=" + id + ", accountType='" + accountType + "'}";
        }
    }

    private List<UserInfo> users;

    public AllUser() {
        users = new ArrayList<>();
    }

    public void addUser(String name, int id, String accountType) {
        users.add(new UserInfo(name, id, accountType));
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}