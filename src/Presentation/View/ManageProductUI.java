package Presentation.View;

 import Application.Model.Product;
 import Presentation.Controller.Main.PharmacyController;
 import Presentation.Controller.Supporting.ManagerController;

 import javax.swing.*;
 import javax.swing.event.ListSelectionEvent;
 import javax.swing.event.ListSelectionListener;
 import javax.swing.table.DefaultTableModel;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.sql.SQLException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Set;
 import java.util.Vector;


public class ManageProductUI extends javax.swing.JFrame {


    public ManageProductUI() throws SQLException {
        initComponents();
    }

    private void initComponents() throws SQLException {

        PharmacyController.managerController = new ManagerController();
        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        titleLbl = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        crumbLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        prodTable = new javax.swing.JTable();
        showBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        qtyField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descArea = new javax.swing.JTextArea();
        updateBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        titleLbl.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        titleLbl.setText("Manage Products");

        addBtn.setBackground(new java.awt.Color(102, 153, 255));
        addBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        addBtn.setText("Add Product");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                AddProductUI addUI = null;
                try {
                    addUI = new AddProductUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                addUI.setVisible(true);
                dispose();
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 102));
        jLabel6.setText("( Click to Add New Product )");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(567, 567, 567)
                                .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(1166, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(205, 205, 205))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(titleLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        crumbLbl.setFont(new java.awt.Font("Avenir", 3, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        jLabel1.setText("Search Product:");

        searchField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        jLabel2.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("( Enter Product Name or Code )");

        searchBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(0, 153, 255));
        searchBtn.setText("SEARCH");

        prodTable.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        prodTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String [] {
                        "Code", "Product Name", "Description", "Available Packs", "Qty Per Pack", "Available Stock", "Price"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(prodTable);
        prodModel = (DefaultTableModel) prodTable.getModel();

        showBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        showBtn.setText("SELECT ALL");

        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                prodModel.setRowCount(0);
                HashMap<String,Product> productMap = PharmacyController.managerController.getProductMap();

                String search = searchField.getText().trim();

                if(!search.isEmpty()) {
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

            }
        });

        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                HashMap<String,Product> productMap = PharmacyController.managerController.getProductMap();

                Set<String> keys = productMap.keySet();

                prodModel.setRowCount(0);
                for(String k : keys)
                {
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

        clearBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(255, 51, 51));
        clearBtn.setText("CLEAR");
        clearBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                prodModel.setRowCount(0);
                searchField.setText("");
            }
        });

        prodTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if(prodTable.getSelectedRow() != -1)
                {
                    nameField.setText((String) prodTable.getValueAt(prodTable.getSelectedRow(),1));
                    descArea.setText((String) prodTable.getValueAt(prodTable.getSelectedRow(),2));
                    qtyField.setText(String.valueOf(prodTable.getValueAt(prodTable.getSelectedRow(),4)));
                    priceField.setText((String.valueOf( prodTable.getValueAt(prodTable.getSelectedRow(),6))));

                }
                else
                {
                    nameField.setText("");
                    descArea.setText("");
                    qtyField.setText("");
                    priceField.setText("");
                }
            }
        });
        jLabel3.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel3.setText("Product Name:");

        jLabel4.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel4.setText("Description:");

        jLabel5.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel5.setText("Quantity Per Pack:");

        jLabel7.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jLabel7.setText("Price:");

        priceField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        qtyField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        nameField.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N


        descArea.setColumns(20);
        descArea.setRows(5);
        jScrollPane2.setViewportView(descArea);

        updateBtn.setBackground(new java.awt.Color(255, 204, 102));
        updateBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        updateBtn.setText("UPDATE");

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (prodTable.getSelectedRow() != -1) {
                    String pName = nameField.getText().toUpperCase();
                    String desc = descArea.getText();
                    Integer qtyPerPack = null;
                    Float price = null;

                    if (!qtyField.getText().matches("\\d+") || !priceField.getText().matches("\\d+(\\.\\d+)?")) {
                        JOptionPane.showMessageDialog(ManageProductUI.this, "Invalid input for quantity or price");
                        return;
                    }
                    if (!qtyField.getText().isEmpty())
                        qtyPerPack = Integer.valueOf(qtyField.getText());
                    if (!priceField.getText().isEmpty())
                        price = Float.valueOf(priceField.getText());


                    if (pName.isEmpty() || desc.isEmpty() || qtyPerPack == null || price == null) {
                        JOptionPane.showMessageDialog(ManageProductUI.this, "Field(s) are Empty");
                    } else {

                        try {
                            PharmacyController.managerController.updateProduct(pName, (String) prodTable.getValueAt(prodTable.getSelectedRow(),0),desc,qtyPerPack,price);

                            prodTable.setValueAt(pName,prodTable.getSelectedRow(),1);
                            prodTable.setValueAt(desc,prodTable.getSelectedRow(),2);
                            prodTable.setValueAt(qtyPerPack,prodTable.getSelectedRow(),4);
                            prodTable.setValueAt(price,prodTable.getSelectedRow(),6);
                            prodTable.getSelectionModel().clearSelection();


                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(ManageProductUI.this, "Field(s) are Empty");
                }
            }


        });

        delBtn.setBackground(new java.awt.Color(255, 102, 102));
        delBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        delBtn.setText("REMOVE");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(69, 69, 69)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(32, 32, 32)
                                                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(47, 47, 47)
                                                                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(452, 452, 452)
                                                                .addComponent(showBtn))
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGap(43, 43, 43)
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(75, 75, 75)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(24, 24, 24)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel4))
                                                                .addGap(50, 50, 50)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(crumbLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(424, 424, 424)
                                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(68, 68, 68)
                                                .addComponent(delBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(crumbLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clearBtn)
                                        .addComponent(showBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(82, 82, 82)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(43, 43, 43)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(updateBtn)
                                        .addComponent(delBtn))
                                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1517, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(420, 420, 420))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>


    public static void main(String[] args) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {


            public void run() {
                try {
                    new ManageProductUI().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton addBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel crumbLbl;
    private javax.swing.JButton delBtn;
    private javax.swing.JTextArea descArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField priceField;
    private javax.swing.JTable prodTable;
    private javax.swing.JTextField qtyField;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton showBtn;
    private javax.swing.JLabel titleLbl;
    private javax.swing.JButton updateBtn;

    private DefaultTableModel prodModel;
}
// End of variables declaration
