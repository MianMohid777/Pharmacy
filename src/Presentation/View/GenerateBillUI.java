package Presentation.View;


import Application.Model.*;
import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.AssistantController;
import Presentation.Controller.Supporting.UserController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jScrollPane2 = new JScrollPane();
        billTable = new JTable();
        jLabel3 = new JLabel();
        nameField = new JTextField();
        cancelBtn = new JButton();
        billBtn = new JButton();
        panel = new JPanel();
        totalLbl = new JLabel();

        panel.setBackground(new Color(255, 255, 255));

        jLabel1.setFont(new Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("PillPal Pharmacy: Order Bill");

        jLabel2.setFont(new Font("Avenir", 0, 18)); // NOI18N
        jLabel2.setText("Customer Order");

        billTable.setFont(new Font("Avenir", 0, 18)); // NOI18N
        billTable.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Item N.o", "Product Name", "Qty", "Price", "Total"
                }
        ) {
            Class[] types = new Class[]{
                    Integer.class, String.class, Integer.class, Float.class, Float.class
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


        jLabel3.setFont(new Font("Avenir", 0, 18)); // NOI18N
        jLabel3.setText("Customer Name:");

        nameField.setFont(new Font("Avenir", 0, 18)); // NOI18N

        billBtn.setFont(new Font("Avenir", 1, 18)); // NOI18N
        billBtn.setForeground(new Color(0, 153, 255));
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
                        o.setName(UserController.getU().getName());

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
        cancelBtn.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

        cancelBtn.setFont(new Font("Avenir", 1, 14)); // NOI18N
        cancelBtn.setForeground(new Color(255, 51, 51));
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

        totalLbl.setFont(new Font("Avenir", 1, 24)); // NOI18N
        totalLbl.setForeground(new Color(204, 0, 51));
        totalLbl.setText("Grand Total:");

        GroupLayout jPanel1Layout = new GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(155, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(157, 157, 157))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel3)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(317, 317, 317)
                                                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 456, GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(425, 425, 425)))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 1088, GroupLayout.PREFERRED_SIZE)
                                                .addGap(132, 132, 132))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(billBtn, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                                .addGap(590, 590, 590))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(totalLbl, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
                                                .addGap(489, 489, 489))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel1)
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(63, 63, 63)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(totalLbl)
                                .addGap(37, 37, 37)
                                .addComponent(billBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(213, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 1375, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 781, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

                    totalLbl.setText(totalLbl.getText() + "  " + AssistantController.getContainer().total());
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
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton billBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;

    private DefaultTableModel billModel;

    private javax.swing.JLabel totalLbl;
    // End of variables declaration
}





