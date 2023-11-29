package Presentation.View;

import Application.Model.Product;
import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.ManagerController;
import org.jdatepicker.SqlDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class StockUI extends javax.swing.JFrame {


    public StockUI() throws SQLException {
        initComponents();
    }

    private void initComponents() throws SQLException {

        PharmacyController controller = new PharmacyController();
        PharmacyController.managerController = new ManagerController();
        sqlDateModel1 = new SqlDateModel();
        sqlDateModel2 = new SqlDateModel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        prodTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 800), new java.awt.Dimension(0, 800), new java.awt.Dimension(32767, 800));
        stockUpBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        qtyField = new javax.swing.JTextField();
        expiryDatePick = new org.jdatepicker.JDatePicker();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("Inventory Management");

        stockTable.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        stockTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Stock Id", "Product Name", "Pack Quantity", "Expiry Date"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(stockTable);
        stockModel = (DefaultTableModel) stockTable.getModel();

        prodTable.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        prodTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String[]{
                        "Code", "Product Name", "Description", "Available Packs", "Qty Per Pack", "Available Stock", "Price"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(prodTable);
        prodModel = (DefaultTableModel) prodTable.getModel();

        jLabel2.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel2.setText("Search Product:");

        searchField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        jLabel3.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("( Enter Product Name or Code )");

        searchBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(0, 153, 255));
        searchBtn.setText("SEARCH");
        searchBtn.addActionListener(evt -> {

            prodModel.setRowCount(0);
            HashMap<String, Product> productMap = PharmacyController.managerController.getProductMap();

            String search = searchField.getText().trim();

            if (!search.isEmpty()) {
                if (productMap.containsKey(search)) {
                    Product p = productMap.get(search);
                    Vector<Object> rowData = new Vector<>();

                    rowData.add(p.getCode());
                    rowData.add(p.getName());
                    rowData.add(p.getDesc());
                    rowData.add(p.getPackQty());
                    rowData.add(p.getQtyPerPack());
                    rowData.add(p.getStockQty());
                    rowData.add(p.getPrice());

                    prodModel.addRow(rowData);

                } else {
                    try {
                        Product p = PharmacyController.managerController.getSearchedProd(search);

                        if (p != null) {
                            Vector<Object> rowData = new Vector<>();

                            rowData.add(p.getCode());
                            rowData.add(p.getName());
                            rowData.add(p.getDesc());
                            rowData.add(p.getPackQty());
                            rowData.add(p.getQtyPerPack());
                            rowData.add(p.getStockQty());
                            rowData.add(p.getPrice());

                            prodModel.addRow(rowData);

                        } else {
                            List<String> searchedList = PharmacyController.managerController.giveSearchResult(search);

                            for (String k : searchedList) {
                                Vector<Object> rowData = new Vector<>();

                                p = PharmacyController.managerController.getSearchedProd(k);

                                rowData.add(p.getCode());
                                rowData.add(p.getName());
                                rowData.add(p.getDesc());
                                rowData.add(p.getPackQty());
                                rowData.add(p.getQtyPerPack());
                                rowData.add(p.getStockQty());
                                rowData.add(p.getPrice());

                                prodModel.addRow(rowData);

                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });

        clearBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(255, 51, 51));
        clearBtn.setText("CLEAR");
        clearBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                prodModel.setRowCount(0);
                stockModel.setRowCount(0);
                searchField.setText("");
            }
        });

        jLabel4.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel4.setText("Product Name:");

        nameField.setEditable(false);
        nameField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel5.setText("Pack Quantity:");

        jLabel6.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel6.setText("Expiry Date:");

        stockUpBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        stockUpBtn.setText("Stock Up");

        stockUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (prodTable.getSelectedRow() != -1) {
                    if (!qtyField.getText().matches("\\d+")) {
                        JOptionPane.showMessageDialog(StockUI.this, "Invalid input for quantity");
                        return;
                    }
                    if (expiryDatePick.getModel().getValue() == null) {
                        JOptionPane.showMessageDialog(StockUI.this, "Please Select an Expiry Date");
                        return;
                    }

                    Object selectedDate = expiryDatePick.getModel().getValue();

                    LocalDate expiryDate = null;
                    if (selectedDate instanceof GregorianCalendar) {
                        expiryDate = ((GregorianCalendar) selectedDate).toZonedDateTime().toLocalDate();
                    }


                    if (expiryDate != null && expiryDate.isBefore(LocalDate.now().plusDays(1))) {
                        JOptionPane.showMessageDialog(StockUI.this, "Please Select a Valid Expiry Date");
                        return;
                    }

                    String code = (String) prodTable.getValueAt(prodTable.getSelectedRow(), 0);
                    Integer qty = Integer.valueOf(qtyField.getText());

                    try {
                        PharmacyController.managerController.addStock(code, qty, expiryDate);
                        JOptionPane.showMessageDialog(StockUI.this, "Stocks Added to Inventory!");

                        int selectedRow = prodTable.getSelectedRow();
                        prodTable.getSelectionModel().clearSelection();
                        searchBtn.doClick();
                        prodTable.setRowSelectionInterval(selectedRow,selectedRow);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        removeBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        removeBtn.setText("Remove");

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(stockTable.getSelectedRow() != -1)
                {
                    Integer id = (Integer) stockTable.getValueAt(stockTable.getSelectedRow(),0);
                    String code = (String) stockTable.getValueAt(stockTable.getSelectedRow(),1);
                    Integer qty = (Integer) stockTable.getValueAt(stockTable.getSelectedRow(),2);

                    try {
                        PharmacyController.managerController.removeStock(id,code,qty);
                        JOptionPane.showMessageDialog(StockUI.this,"Stocks Removed From Inventory");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    int selectedRow = prodTable.getSelectedRow();
                    prodTable.getSelectionModel().clearSelection();
                    searchBtn.doClick();
                    prodTable.setRowSelectionInterval(selectedRow,selectedRow);

                }
            }
        });
        qtyField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N

        expiryDatePick.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N

        backBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        backBtn.setText("Back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        prodTable.getSelectionModel().addListSelectionListener(e -> {

            if (prodTable.getSelectedRow() != -1) {
                stockModel.setRowCount(0);
                nameField.setText((String) prodTable.getValueAt(prodTable.getSelectedRow(), 1));

                String code = (String) prodTable.getValueAt(prodTable.getSelectedRow(), 0);
                try {
                    List<Vector<Object>> stockList = PharmacyController.managerController.pileStocks(code);

                    for (Vector<Object> row : stockList) {
                        stockModel.addRow(row);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                nameField.setText("");
                qtyField.setText("");
                expiryDatePick.getModel().setValue(null);
                stockModel.setRowCount(0);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel6))
                                                                .addGap(74, 74, 74)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(expiryDatePick, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(152, 152, 152))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addGap(29, 29, 29)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                .addGap(43, 43, 43)
                                                                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(32, 32, 32)
                                                                                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(20, 20, 20))))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addGap(579, 579, 579)
                                                                .addComponent(stockUpBtn)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(511, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(546, 546, 546)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(backBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(53, 53, 53)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(clearBtn)
                                                        .addComponent(removeBtn))
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(61, 61, 61)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel4))
                                                .addGap(74, 74, 74)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(54, 54, 54)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(expiryDatePick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(39, 39, 39)
                                                .addComponent(stockUpBtn)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>


    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new StockUI().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton clearBtn;
    private org.jdatepicker.JDatePicker expiryDatePick;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;
    private javax.swing.JTable prodTable;
    private javax.swing.JTextField qtyField;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchField;
    private org.jdatepicker.SqlDateModel sqlDateModel1;
    private org.jdatepicker.SqlDateModel sqlDateModel2;
    private javax.swing.JTable stockTable;
    private javax.swing.JButton stockUpBtn;

    private DefaultTableModel prodModel;
    private DefaultTableModel stockModel;

    private javax.swing.JButton backBtn;
}

