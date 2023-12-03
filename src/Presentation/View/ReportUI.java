package Presentation.View;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ReportUI extends javax.swing.JFrame {

    public ReportUI() {
        initComponents();
    }

    private void initComponents() {

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("Reports");

        reportTypeBox.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        reportTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select-", "Sales Report", "Inventory Report" }));

        reportTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED)
                {
                    if( reportTypeBox.getSelectedItem().equals("-Select-"))
                    {
                        JOptionPane.showMessageDialog(ReportUI.this,"Select a Valid Type");
                    }
                }
            }
        });
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



        jLabel3.setFont(new java.awt.Font("Avenir", 3, 18)); // NOI18N
        jLabel3.setText("From Date: ");

        jLabel5.setFont(new java.awt.Font("Avenir", 3, 18)); // NOI18N
        jLabel5.setText("To Date: ");

        backBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        backBtn.setText("Back");

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
                                                                .addComponent(jLabel2)
                                                                .addGap(59, 59, 59)
                                                                .addComponent(reportTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(todayRadio)
                                                                .addGap(89, 89, 89)
                                                                .addComponent(weekRadio)
                                                                .addGap(84, 84, 84)
                                                                .addComponent(monthRadio)))
                                                .addGap(71, 71, 71)
                                                .addComponent(customRadio)))
                                .addGap(0, 201, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLabel3)
                                .addGap(33, 33, 33)
                                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(jLabel5)
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
                                .addContainerGap(229, Short.MAX_VALUE))
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
                new ReportUI().setVisible(true);
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
    // End of variables declaration
}
