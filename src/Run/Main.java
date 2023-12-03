package Run;

import Presentation.View.LogInUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        LogInUI posStart = new LogInUI();
        posStart.setVisible(true);
    }
}