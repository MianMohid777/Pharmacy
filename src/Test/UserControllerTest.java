package Test;

import Application.Model.User;
import Presentation.Controller.Supporting.UserController;


import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.*;

public class UserControllerTest {

    @Test
    public void testlogIn() throws SQLException {
        UserController userController = new UserController();

        assertFalse(userController.logIn("NonExistentUser", "InvalidPassword"));

        // Assuming there is a user with username "Test" and password "Test1234" in the database
        assertTrue(userController.logIn("Test", "1234"));
    }

    @Test
    public void testroleOfLoggedUser() throws SQLException {
        UserController userController = new UserController();

        // Assuming there is a user with username "Test" and password "Test1234" in the database
        assertDoesNotThrow(() -> userController.logIn("Test", "123"));
        assertEquals("M", userController.roleOfLoggedUser()); // Manager role

        // Assuming there is a user with username "SalesAssistant" and password "Assistant1234" in the database
        assertDoesNotThrow(() -> userController.logIn("root", "root123"));
        assertEquals("A", userController.roleOfLoggedUser()); // Sales_Assistant role
    }



    @Test
    public void testlogOut() throws SQLException {
        UserController userController = new UserController();

        // Assuming there is a user with username "Test" and password "Test1234" in the database
        assertDoesNotThrow(() -> userController.logIn("Test", "1234"));

        assertNotNull(UserController.getU());

        userController.logOut();

        assertNull(UserController.getU());
    }

    @Test
    public void testgetU()  throws SQLException {
        UserController userController = new UserController();
        assertNull(UserController.getU());
    }

    @Test
    public void testsetU()  throws SQLException{
        UserController userController = new UserController();

        // Assuming there is a user with username "Test" and password "Test1234" in the database
        User user = userController.logIn("Test", "1234") ? UserController.getU() : null;

        assertNotNull(user);

        UserController.setU(user);

        assertEquals(user, UserController.getU());
    }

}