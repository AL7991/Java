package al.webapp.Objects;


public class UserTest {

    public UserTest(String userName, String password) {
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
