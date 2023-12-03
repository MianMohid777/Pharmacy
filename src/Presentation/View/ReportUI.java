package Presentation.View;

import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.ManagerController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Objects;

public class ReportUI extends javax.swing.JFrame {

    public ReportUI() throws SQLException {
        initComponents();
    }

    private void initComponents() throws SQLException {

        PharmacyController controller = new PharmacyController();
        PharmacyController.managerController = new ManagerController();

        sqlDateModel1 = new org.jdatepicker.SqlDateModel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        reportTypeBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        weekRadio = new javax.swing.JRadioButton();
        todayRadio = new javax.swing.JRadioButton();
        monthRadio = new javax.swing.JRadioButton();
        customRadio = new javax.swing.JRadioButton();
        fromDate = new org.jdatepicker.JDatePicker();
        toDate = new org.jdatepicker.JDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        genBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("Reports");

        reportTypeBox.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        reportTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select-", "Sales Report", "Inventory Report" }));




        jLabel2.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel2.setText("Choose Type of Report:");

        weekRadio.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        weekRadio.setText("This Week");

        todayRadio.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        todayRadio.setText("Today's");

        monthRadio.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        monthRadio.setText("This Month");

        customRadio.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        customRadio.setText("Custom ");

        ButtonGroup group = new ButtonGroup();

        group.add(todayRadio);
        group.add(weekRadio);
        group.add(monthRadio);
        group.add(customRadio);



        customRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(customRadio.isSelected()) {
                    fromDate.setEnabled(true);
                    toDate.setEnabled(true);
                }
                else
                {
                    fromDate.setEnabled(false);
                    toDate.setEnabled(false);
                }
            }
        });


        reportTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED)
                {
                    if( reportTypeBox.getSelectedItem().equals("-Select-"))
                    {
                        JOptionPane.showMessageDialog(ReportUI.this,"Select a Valid Type");
                    }
                    else if(reportTypeBox.getSelectedItem().equals("Inventory Report"))
                    {
                        todayRadio.setEnabled(false);
                        weekRadio.setEnabled(false);
                        monthRadio.setEnabled(false);
                        customRadio.setSelected(true);
                    }
                    else if(reportTypeBox.getSelectedItem().equals("Sales Report"))
                    {
                        todayRadio.setEnabled(true);
                        weekRadio.setEnabled(true);
                        monthRadio.setEnabled(true);
                    }
                }
            }
        });

        fromDate.setEnabled(false);
        toDate.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Avenir", 3, 18)); // NOI18N
        jLabel3.setText("From Date: ");

        jLabel5.setFont(new java.awt.Font("Avenir", 3, 18)); // NOI18N
        jLabel5.setText("To Date: ");

        backBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        backBtn.setText("Back");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ManagerDashUI managerDashUI = new ManagerDashUI();
                managerDashUI.setVisible(true);
                dispose();
            }
        });
        genBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        genBtn.setText("Generate");

        genBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!Objects.equals(reportTypeBox.getSelectedItem(), "-Select"))
                {
                    if(group.getSelection() != null)
                    {

                        if(customRadio.isSelected()) {
                            if (fromDate.getModel().getValue() == null || toDate.getModel().getValue() == null) {
                                JOptionPane.showMessageDialog(ReportUI.this, "Please Select a Valid Date Range");
                                return;
                            }

                            Object selectedDate1 = fromDate.getModel().getValue();
                            Object selectedDate2 = toDate.getModel().getValue();

                            LocalDate startDate = null;
                            LocalDate endDate = null;

                            if (selectedDate1 instanceof GregorianCalendar) {
                                startDate = ((GregorianCalendar) selectedDate1).toZonedDateTime().toLocalDate();
                            }

                            if (selectedDate2 instanceof GregorianCalendar) {
                                endDate = ((GregorianCalendar) selectedDate2).toZonedDateTime().toLocalDate();
                            }


                            if ((startDate != null && startDate.isAfter(LocalDate.now())) || (endDate != null && endDate.isBefore(LocalDate.now()) || Objects.requireNonNull(endDate).isAfter(LocalDate.now().plusDays(1)))) {
                                JOptionPane.showMessageDialog(ReportUI.this, "Please Select a Valid Date Range");

                            }
                            else {

                                try {
                                    PharmacyController.managerController.SalesReport("CUSTOM",startDate,endDate);
                                } catch (SQLException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                JOptionPane.showMessageDialog(ReportUI.this, "Sales Report Generated");
                            }
                        }
                        else {

                            if(Objects.requireNonNull(reportTypeBox.getSelectedItem()).equals("Sales Report")) {
                                String type = "";

                                if (todayRadio.isSelected())
                                    type = "DAILY";
                                else if (weekRadio.isSelected())
                                    type = "WEEKLY";
                                else if (monthRadio.isSelected())
                                    type = "MONTHLY";

                                try {
                                    PharmacyController.managerController.SalesReport(type,null,null);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                JOptionPane.showMessageDialog(ReportUI.this, "Sales Report Generated");
                            }
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(ReportUI.this,"Select a Date Range to proceed !");
                    }
                }
                else
                    JOptionPane.showMessageDialog(ReportUI.this,"Select a Valid Type !");
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(257, 257, 257)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(92, 92, 92)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(todayRadio)
                                                                .addGap(89, 89, 89)
                                                                .addComponent(weekRadio)
                                                                .addGap(84, 84, 84)
                                                                .addComponent(monthRadio))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(59, 59, 59)
                                                                .addComponent(reportTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(71, 71, 71)
                                                .addComponent(customRadio)))
                                .addGap(0, 201, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(genBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(33, 33, 33)
                                                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(82, 82, 82)
                                                .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(backBtn))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(reportTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(89, 89, 89)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(weekRadio)
                                        .addComponent(monthRadio)
                                        .addComponent(todayRadio)
                                        .addComponent(customRadio))
                                .addGap(111, 111, 111)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel3)
                                                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5))
                                        .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(101, 101, 101)
                                .addComponent(genBtn)
                                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );


        pack();

        setResizable(false);
    }// </editor-fold>


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ReportUI().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton backBtn;
    private javax.swing.JRadioButton customRadio;
    private org.jdatepicker.JDatePicker fromDate;
    private org.jdatepicker.JDatePicker toDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton monthRadio;
    private javax.swing.JComboBox<String> reportTypeBox;
    private org.jdatepicker.SqlDateModel sqlDateModel1;
    private javax.swing.JRadioButton todayRadio;
    private javax.swing.JRadioButton weekRadio;
    private javax.swing.JButton genBtn;
    // End of variables declaration
}
