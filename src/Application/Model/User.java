package Application.Model;

public class User {

    private String name;
    private String userName;
    private String passWord;
    private Role role;

    public User(String name, String userName, String passWord, Role role) {
        this.name = name;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
