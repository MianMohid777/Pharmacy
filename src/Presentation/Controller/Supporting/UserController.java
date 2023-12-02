package Presentation.Controller.Supporting;

import Application.Model.Manager;
import Application.Model.Sales_Assistant;
import Application.Model.User;
import Application.Service.Implementation.UserS_I;
import Application.Service.UserService;
import Presentation.Controller.Main.PharmacyController;

import java.sql.SQLException;
import java.util.List;

public class UserController {


    private UserService userService;
    private static User u;

    public UserController() throws SQLException {
       userService = new UserS_I();
    }

    public Boolean logIn(String userName, String password) throws SQLException {
         u = userService.findByUserName(userName);

        if(u == null)
            return false;
        else
        {
            if(u.getUserName().equalsIgnoreCase(userName) && u.getPassWord().equals(password))
            {
                u.getRole().permissions();
                return true;
            }
        }

        return false;
    }

    public String roleOfLoggedUser()
    {
        return( u.getRole() instanceof Manager ? "M" : "A");
    }

    public void save(String name,String userName,String passWord, String role) throws SQLException {

        User u = new User();
        u.setName(name);
        u.setUserName(userName);
        u.setPassWord(passWord);

        if(role.equals("Manager"))
            u.setRole(new Manager());
        else
            u.setRole(new Sales_Assistant());

        userService.add(u);
    }

    public void delete(Integer id) throws SQLException {
        try {
            userService.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void logOut()
    {
       // u.getRole().permissions();
        PharmacyController.assistantController = null;
        PharmacyController.managerController = null;
    }

    public List<User> findAll() throws SQLException {
        return userService.getAll();
    }
    public static User getU() {
        return u;
    }

    public static void setU(User u) {
        UserController.u = u;
    }

    public static void main(String[] args) throws SQLException {
        UserController uc = new UserController();

        System.out.println(uc.logIn("Test","Test1234"));

        //uc.save("Mohid","Test","Test1234","Manager");

        System.out.println(uc.logIn("Test","Test1234"));

        System.out.println(uc.roleOfLoggedUser());


    }
}
