package Presentation.View;

import Application.Model.Manager;
import Application.Model.Product;
import Application.Model.Sales_Assistant;
import Application.Model.User;
import Presentation.Controller.Main.PharmacyController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;


public class ManageUserUI extends javax.swing.JFrame {


    public ManageUserUI() {
        initComponents();
    }


    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLbl = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        userF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pass2F = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        passF = new javax.swing.JTextField();
        nameF = new javax.swing.JTextField();
        updateBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        titleLbl.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        titleLbl.setText("Manage Users");

        backBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        backBtn.setText("Back");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerDashUI managerDashUI = new ManagerDashUI();

                managerDashUI.setVisible(true);
                dispose();
            }
        });

        userTable.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        userTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Name", "UserName", "Password", "Role"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(userTable);
        userModel = (DefaultTableModel) userTable.getModel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                userModel.setRowCount(0);
                for(User u: PharmacyController.userController.getUsers())
                {
                    Vector<Object> rowData = new Vector<>();

                    rowData.add(u.getName());
                    rowData.add(u.getUserName());
                    rowData.add(u.getPassWord());
                    rowData.add((u.getRole() instanceof Manager ? "Manager" : "Assistant" ));

                    userModel.addRow(rowData);

                }
            }
        });

        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if(userTable.getSelectedRow() != -1)
                {
                    int row = userTable.getSelectedRow();
                    String name = (String) userTable.getValueAt(row,0);
                    String userName = (String) userTable.getValueAt(row,1);
                    String passWord = (String) userTable.getValueAt(row,2);

                    nameF.setText(name);
                    userF.setText(userName);
                    passF.setText(passWord);

                    pass2F.setText("");

                    jRadioButton1.setEnabled(false);
                    jRadioButton2.setEnabled(false);
                    userF.setEditable(false);
                }
                else {
                    nameF.setText("");
                    userF.setText("");
                    passF.setText("");

                    pass2F.setText("");

                    jRadioButton1.setEnabled(true);
                    jRadioButton2.setEnabled(true);
                }
            }
        });
        jLabel3.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel3.setText("Name:");

        userF.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        jLabel4.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel4.setText("Enter Username:");

        jLabel5.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel5.setText("Enter Password:");

        pass2F.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        jLabel7.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel7.setText("Confirm Password:");

        passF.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        nameF.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N



        updateBtn.setBackground(new java.awt.Color(255, 204, 102));
        updateBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        updateBtn.setText("UPDATE");

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(userTable.getSelectedRow() != -1) {
                    String name = nameF.getText().trim();
                    String userName = userF.getText();
                    String pass1 = passF.getText();
                    String pass2 = pass2F.getText();
                    String role = (String) userTable.getValueAt(userTable.getSelectedRow(),3);


                    if(pass1.length() < 4)
                    {
                        JOptionPane.showMessageDialog(ManageUserUI.this,"Password Must Be At Least 4 Characters Long");

                    }
                    else if(name.isEmpty())
                        JOptionPane.showMessageDialog(ManageUserUI.this,"Name Field is Empty");
                    else if(pass2.equals(pass1))
                    {
                        try {
                            PharmacyController.userController.update(name,userName,pass1,role);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        int row = userTable.getSelectedRow();
                        userTable.getSelectionModel().clearSelection();


                        userTable.setValueAt(name,row,0);
                        userTable.setValueAt(pass1,row,2);


                    }
                    else {
                        userTable.getSelectionModel().clearSelection();
                        JOptionPane.showMessageDialog(ManageUserUI.this,"Password doesn't match");
                    }
                }
            }
        });

        delBtn.setBackground(new java.awt.Color(255, 102, 102));
        delBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        delBtn.setText("REMOVE");

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = userTable.getSelectedRow();

                if(row != -1)
                {
                    String username = (String) userTable.getValueAt(row,1);
                    try {
                        PharmacyController.userController.delete(username);
                        userModel.removeRow(row);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        saveBtn.setBackground(new java.awt.Color(0, 153, 255));
        saveBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        saveBtn.setText("SAVE");


        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nameF.getText().trim();
                String userName = userF.getText().trim();
                String passWord = passF.getText();
                String passWord2 = pass2F.getText();
                String role = "";

                if(jRadioButton1.isSelected())
                    role = "Manager";
                else if(jRadioButton2.isSelected())
                    role = "Assistant";


                if(name.isEmpty() || userName.isEmpty() || passWord.isEmpty()|| passWord2.isEmpty() || (!jRadioButton1.isSelected() &&!jRadioButton2.isSelected()))
                {
                    JOptionPane.showMessageDialog(ManageUserUI.this,"Field(s) are empty !" );
                }
                else if (!passWord.equals(passWord2))
                {
                    JOptionPane.showMessageDialog(ManageUserUI.this,"Password(s) didn't Matched !" );
                }
                else {

                    for(User u : PharmacyController.userController.getUsers())
                    {
                        if(u.getUserName().equalsIgnoreCase(userName.trim()))
                        {
                            JOptionPane.showMessageDialog(ManageUserUI.this,"Username Already Taken, Select a Unique Username !" );
                            return;
                        }
                    }
                    Vector<Object> rowData = new Vector<>();

                    rowData.add(name);
                    rowData.add(userName);
                    rowData.add(passWord);
                    rowData.add((role.equals("Manager")? "Manager" : "Assistant"));

                    //User u = new User(name, userName, passWord,role.equals("Manager") ? new Manager() : new Sales_Assistant());

                    userModel.addRow(rowData);
                    try {
                        PharmacyController.userController.save(name, userName, passWord,role);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    nameF.setText("");
                    userF.setText("");
                    passF.setText("");

                    pass2F.setText("");

                    jRadioButton1.setSelected(false);
                    jRadioButton2.setSelected(false);
                }

            }
        });

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel1.setText("Select Role:");
        jLabel1.setToolTipText("");

        jRadioButton1.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jRadioButton1.setText("Manager");


        jRadioButton2.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jRadioButton2.setText("Assistant");

        ButtonGroup group = new ButtonGroup();

        group.add(jRadioButton1);
        group.add(jRadioButton2);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(466, 466, 466)
                                                .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(486, 486, 486)
                                                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(118, 118, 118)
                                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(1079, 1079, 1079)
                                                .addComponent(delBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(111, 111, 111)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel5))
                                                .addGap(51, 51, 51)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(passF, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(userF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
                                                        .addComponent(nameF, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(211, 211, 211)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel1))
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jRadioButton1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(pass2F, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(195, 195, 195)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1023, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(554, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(backBtn)
                                        .addComponent(titleLbl))
                                .addGap(34, 34, 34)
                                .addComponent(delBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(jRadioButton1)
                                        .addComponent(jRadioButton2))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(userF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel7)
                                        .addComponent(pass2F, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveBtn)
                                        .addComponent(updateBtn))
                                .addContainerGap(159, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageUserUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton backBtn;
    private javax.swing.JButton delBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameF;
    private javax.swing.JTextField pass2F;
    private javax.swing.JTextField passF;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel titleLbl;
    private javax.swing.JButton updateBtn;
    private javax.swing.JTextField userF;
    private javax.swing.JTable userTable;

    private DefaultTableModel userModel;
    // End of variables declaration
}

