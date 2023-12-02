package Presentation.View;

import Application.Model.Item;
import Application.Model.Order;
import Application.Model.Product;
import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.AssistantController;
import Presentation.Controller.Supporting.ManagerController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class POS_UI extends javax.swing.JFrame {


    public POS_UI() throws SQLException {
        initComponents();
    }


    private void initComponents() throws SQLException {

        PharmacyController controller = new PharmacyController();
        PharmacyController.assistantController = new AssistantController();
        PharmacyController.managerController = new ManagerController();


        mainPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        titleLbl1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        showBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        crumbLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prodTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        qtyField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartTable = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        removeCart = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        availQtyField = new javax.swing.JTextField();
        checkBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel1.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        titleLbl1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        titleLbl1.setText("PillPal Pharmacy: Point of Sales");

        jLabel1.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel1.setText("Search Product:");

        searchField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        jLabel2.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("( Enter Product Name or Code )");

        searchBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(0, 153, 255));
        searchBtn.setText("SEARCH");
        searchBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        searchBtn.addActionListener(evt -> {

            prodModel.setRowCount(0);
            HashMap<String, Product> productMap = PharmacyController.assistantController.getProductMap();

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
                        Product p = PharmacyController.assistantController.getSearchedProd(search);

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
                            List<String> searchedList = PharmacyController.assistantController.giveSearchResult(search);

                            for (String k : searchedList) {
                                Vector<Object> rowData = new Vector<>();

                                p = PharmacyController.assistantController.getSearchedProd(k);

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

        clearBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(255, 51, 51));
        clearBtn.setText("CLEAR");
        clearBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                prodModel.setRowCount(0);
                searchField.setText("");
                nameField.setText("");
                qtyField.setText("");
                availQtyField.setText("");
                priceField.setText("");
            }
        });

        showBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        showBtn.setText("SELECT ALL");

        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Product> productMap = PharmacyController.assistantController.getProductMap();

                Set<String> keys = productMap.keySet();

                prodModel.setRowCount(0);
                for (String k : keys) {
                    Vector<Object> rowData = new Vector<>();

                    Product p = productMap.get(k);

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

        });

        backBtn.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        backBtn.setText("Log Out");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    PharmacyController.userController.logOut();
                    dispose();
                    LogInUI ui = new LogInUI();
                    ui.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addGap(210, 210, 210)
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(386, 386, 386)
                                                                .addComponent(titleLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(407, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44)
                                                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(showBtn)
                                                .addGap(143, 143, 143))))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleLbl1)
                                        .addComponent(backBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(clearBtn)
                                        .addComponent(searchBtn)
                                        .addComponent(showBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        crumbLbl.setFont(new java.awt.Font("Avenir", 3, 14)); // NOI18N

        prodTable.setFont(new java.awt.Font("Avenir", 0, 16)); // NOI18N
        prodTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null, null},
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

        jLabel3.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel3.setText("Product Name:");

        jLabel5.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel5.setText("Item Quantity:");

        jLabel7.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel7.setText("Price:");

        priceField.setEditable(false);
        priceField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        qtyField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        nameField.setEditable(false);
        nameField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        cartTable.setFont(new java.awt.Font("Avenir", 0, 16)); // NOI18N
        cartTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Item N.o", "Code", "Product Name", "Qty", "Price", "Total"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(cartTable);
        cartModel = (DefaultTableModel) cartTable.getModel();

        addBtn.setFont(new java.awt.Font("Avenir", 3, 14)); // NOI18N
        addBtn.setForeground(new java.awt.Color(0, 153, 255));
        addBtn.setText("Add To Cart");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (prodTable.getSelectedRow() != -1) {
                    if (!qtyField.getText().isEmpty()) {
                        if (!qtyField.getText().matches("\\d+") || (Integer.parseInt(qtyField.getText())==0)) {
                            JOptionPane.showMessageDialog(POS_UI.this, "Invalid Input for Quantity");
                        } else {
                            int qty = Integer.parseInt(qtyField.getText());
                            int availQty = Integer.parseInt(availQtyField.getText());

                            if (availQty < qty)
                                JOptionPane.showMessageDialog(POS_UI.this, "Asked Quantity of Item is NOT available");

                            else {
                                try {
                                    if(PharmacyController.assistantController.addToCart(((String) prodTable.getValueAt(prodTable.getSelectedRow(), 0)), qty)) {


                                        System.out.println(AssistantController.getContainer().getItemMap().size());

                                        Item i = AssistantController.getContainer().getItemMap().get((String) prodTable.getValueAt(prodTable.getSelectedRow(), 1));


                                        if (AssistantController.getContainer().getItemMap().isEmpty())
                                            cartModel.setRowCount(0);

                                        Vector<Object> rowData = new Vector<>();

                                        if (i.getQtyOrdered() == qty) {
                                            int index = cartModel.getRowCount();

                                            rowData.add(++index);
                                            rowData.add(prodTable.getValueAt(prodTable.getSelectedRow(), 0));
                                            rowData.add(prodTable.getValueAt(prodTable.getSelectedRow(), 1));
                                            rowData.add(qtyField.getText());
                                            rowData.add(prodTable.getValueAt(prodTable.getSelectedRow(), 6));

                                            Float total = Integer.parseInt(qtyField.getText()) * Float.parseFloat(priceField.getText());

                                            rowData.add(total);

                                            cartModel.addRow(rowData);

                                            JOptionPane.showMessageDialog(POS_UI.this, "Item Added to Cart");




                                        } else {

                                            int cartRow = 0;

                                            for (int x = 0; x < cartModel.getRowCount() && !cartModel.getValueAt(x, 2).equals(i.getP().getName()); x++) {
                                                cartRow++;
                                            }

                                            //int newQty = Integer.parseInt(qtyField.getText() + i.getQtyOrdered());
                                            cartTable.setValueAt(i.getQtyOrdered(),cartRow,3);
                                            cartTable.setValueAt(i.totalPrice(),cartRow,5);

                                            JOptionPane.showMessageDialog(POS_UI.this, "Item Updated to Cart");

                                        }

                                        int redStock = Integer.parseInt(availQtyField.getText()) - qty;
                                        prodTable.setValueAt(redStock,prodTable.getSelectedRow(),5);
                                    }
                                    else
                                        JOptionPane.showMessageDialog(POS_UI.this, "Item has Insufficient Stock");

                                    prodTable.getSelectionModel().clearSelection();
                                } catch (RuntimeException run) {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                }
            }
        });


        removeCart.setFont(new java.awt.Font("Avenir", 3, 14)); // NOI18N
        removeCart.setForeground(new java.awt.Color(255, 153, 51));
        removeCart.setText("Remove From Cart");

        removeCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int cartRow = cartTable.getSelectedRow();

                if(cartRow != -1)
                {
                    String code = (String) cartTable.getValueAt(cartRow,1);

                    PharmacyController.assistantController.removeCart(code);

                    cartModel.removeRow(cartRow);

                    clearBtn.doClick();
                    cartTable.getSelectionModel().clearSelection();

                    JOptionPane.showMessageDialog(POS_UI.this,"Item Removed From Cart");

                }
            }
        });
        jButton3.setFont(new java.awt.Font("Avenir", 3, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 102, 102));
        jButton3.setText("Clear Cart");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                cartModel.setRowCount(0);
                clearBtn.doClick();

                PharmacyController.assistantController.clearCart();
            }
        });

        jLabel4.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel4.setText("Available Qty:");

        availQtyField.setEditable(false);
        availQtyField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N

        checkBtn.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        checkBtn.setText("PROCEED TO CHECKOUT");

        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(cartTable.getRowCount() > 0) {
                    AssistantController.setOrder(PharmacyController.assistantController.makeOrder());
                    GenerateBillUI billUI = null;
                    try {
                        billUI = new GenerateBillUI();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    billUI.setVisible(true);
                    dispose();
                }

                else {
                    JOptionPane.showMessageDialog(POS_UI.this,"Cart is Empty !");
                }



            }
        });

        jLabel6.setFont(new java.awt.Font("Avenir", 1, 24)); // NOI18N
        jLabel6.setText("Cart");


        prodTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if(prodTable.getSelectedRow() != -1)
                {
                    String pName = (String) prodTable.getValueAt(prodTable.getSelectedRow(),1);
                    Float price = (Float) prodTable.getValueAt(prodTable.getSelectedRow(),6);
                    Integer avail = (Integer) prodTable.getValueAt(prodTable.getSelectedRow(),5);

                    nameField.setText(pName);
                    priceField.setText(String.valueOf(price));
                    availQtyField.setText(String.valueOf(avail));
                }
                else {
                    searchField.setText("");
                    nameField.setText("");
                    qtyField.setText("");
                    availQtyField.setText("");
                    priceField.setText("");
                }
            }
        });
        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
                mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(checkBtn)
                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1232, 1232, 1232))
                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(removeCart)
                                                                .addGap(38, 38, 38)
                                                                .addComponent(jButton3))
                                                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                        .addGap(43, 43, 43)
                                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                        .addGap(101, 101, 101)
                                                                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(jLabel5))
                                                                        .addGap(38, 38, 38)
                                                                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(88, 88, 88)
                                                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(101, 101, 101)
                                                                                        .addComponent(jLabel4)
                                                                                        .addGap(29, 29, 29)
                                                                                        .addComponent(availQtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(36, 36, 36)
                                                                                        .addComponent(addBtn))))))
                                                .addGap(24, 24, 24))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1329, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(crumbLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(35, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
                mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(crumbLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50)
                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel7)
                                                .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4)
                                                .addComponent(availQtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel3))
                                .addGap(61, 61, 61)
                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addBtn)
                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(33, 33, 33)
                                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(checkBtn))
                                        .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton3)
                                                .addComponent(removeCart)))
                                .addContainerGap(385, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }


    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new POS_UI().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField availQtyField;
    private javax.swing.JButton backBtn;
    private javax.swing.JTable cartTable;
    private javax.swing.JButton checkBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel crumbLbl;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField priceField;
    private javax.swing.JTable prodTable;
    private javax.swing.JTextField qtyField;
    private javax.swing.JButton removeCart;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton showBtn;
    private javax.swing.JLabel titleLbl;
    private javax.swing.JLabel titleLbl1;

    private DefaultTableModel prodModel;
    private DefaultTableModel cartModel;
    // End of variables declaration
}
