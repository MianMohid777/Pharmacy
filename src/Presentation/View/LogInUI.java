package Presentation.View;

import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.AssistantController;
import Presentation.Controller.Supporting.ManagerController;
import Presentation.Controller.Supporting.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogInUI extends JFrame {


    private JTextField usernameField;
    private JPasswordField passwordField;

    public LogInUI() throws SQLException {

        PharmacyController controller = new PharmacyController();

        setTitle("(POS) Point of Sale - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel logoLabel = new JLabel("PHARMACY POS LOGIN");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 30));
        panel.add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 30));
        panel.add(passwordField, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");

        loginButton.setPreferredSize(new Dimension(100, 30));

        loginButton.setBackground(new Color(60, 179, 113)); // Green color


        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (password.length() < 4) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    try {
                        if(PharmacyController.userController.logIn(username,password))
                        {
                            if(PharmacyController.userController.roleOfLoggedUser().equals("M"))
                            {
                                JOptionPane.showMessageDialog(null,"Manager: " + UserController.getU().getName()+ " Logged In");
                                PharmacyController.managerController = new ManagerController();
                                PharmacyController.managerController.clearExpireStock();
                                ManagerDashUI dashUI = new ManagerDashUI();
                                dashUI.setVisible(true);
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Sales Assistant: " + UserController.getU().getName()+ " Logged In");
                                PharmacyController.assistantController = new AssistantController();
                                PharmacyController.assistantController.clearExpireStock();
                                POS_UI posUi = new POS_UI();
                                posUi.setVisible(true);
                                dispose();

                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"Wrong Username or Password");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        panel.add(loginButton, gbc);
        setResizable(false);
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {

        LogInUI ui = new LogInUI();
    }


}
