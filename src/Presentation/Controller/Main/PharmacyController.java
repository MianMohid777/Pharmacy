package Presentation.Controller.Main;

import Presentation.Controller.Supporting.AssistantController;
import Presentation.Controller.Supporting.ManagerController;
import Presentation.Controller.Supporting.UserController;

import java.sql.SQLException;

public class PharmacyController {

    public static ManagerController managerController;
    public static AssistantController assistantController;
    public static UserController userController;

    public PharmacyController() throws SQLException {
       userController = new UserController();
    }
}
