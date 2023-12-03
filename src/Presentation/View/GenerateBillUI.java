package Presentation.View;


import Application.Model.*;
import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.AssistantController;
import Presentation.Controller.Supporting.UserController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class GenerateBillUI extends javax.swing.JFrame {


    public GenerateBillUI() throws SQLException {
        initComponents();
    }


    private void initComponents() throws SQLException {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        billBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        panel = new JPanel();

        panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("PillPal Pharmacy: Order Bill");

        jLabel2.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel2.setText("Customer Order");

        billTable.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        billTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Item N.o", "Product Name", "Qty", "Price", "Total"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(billTable);
        billModel = (DefaultTableModel) billTable.getModel();


        jLabel3.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel3.setText("Customer Name:");

        nameField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N

        billBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        billBtn.setForeground(new java.awt.Color(0, 153, 255));
        billBtn.setText("GENERATE BILL");

        billBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(nameField.getText().trim().isEmpty())
                    {
                        JOptionPane.showMessageDialog(GenerateBillUI.this,"Please enter the Customer Name to Proceed !");
                    }
                    else {
                        Order o = AssistantController.getOrder();
                        o.setCustomerName(nameField.getText().trim());
                        //o.setName(UserController.getU().getName());

                        List<Product> lowStockP = PharmacyController.assistantController.generateBill(AssistantController.getOrder());

                        PharmacyController.assistantController.setLowStock(lowStockP);

                        POS_UI ui = new POS_UI();
                        ui.setVisible(true);

                        dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        billBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        cancelBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        cancelBtn.setForeground(new java.awt.Color(255, 51, 51));
        cancelBtn.setText("CANCEL");

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op = JOptionPane.showConfirmDialog(GenerateBillUI.this, "Do you want to Cancel this Order");

                if (op == 0) {
                    PharmacyController.assistantController.cancelOrder(AssistantController.getOrder());
                    POS_UI ui = null;
                    try {
                        ui = new POS_UI();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    ui.setVisible(true);

                }

            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(155, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(157, 157, 157))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel3)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(317, 317, 317)
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(425, 425, 425)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1088, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(132, 132, 132))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(billBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(590, 590, 590))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel1)
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(63, 63, 63)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(80, 80, 80)
                                .addComponent(billBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(213, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1375, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 773, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

         addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                Set<String> keys = AssistantController.getContainer().getItemMap().keySet();

                int index = 0;
                for (String k : keys) {

                    Item i = AssistantController.getContainer().getItemMap().get(k);
                    Product p = i.getP();
                    int qty = i.getQtyOrdered();
                    float total = i.totalPrice();

                    Vector<Object> rowData = new Vector<>();

                    rowData.add(++index);
                    rowData.add(p.getName());
                    rowData.add(qty);
                    rowData.add(p.getPrice());
                    rowData.add(total);
                    billModel.addRow(rowData);
                }
            }
        });

         setResizable(false);
         setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }// </editor-fold>



    public static void main(String[] args) throws SQLException {
        GenerateBillUI ui = new GenerateBillUI();
        ui.setVisible(true);
    }
    // Variables declaration - do not modify
    private javax.swing.JTable billTable;
    private javax.swing.JButton billBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;

    private DefaultTableModel billModel;
    // End of variables declaration
}





