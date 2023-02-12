package al.webapp.objects;

public class UserLogIn {

    public UserLogIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

}
