package Presentation.View;

import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.ManagerController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddProductUI extends javax.swing.JFrame {

    public AddProductUI() throws SQLException {
        initComponents();
    }

    private void initComponents() throws SQLException {


        PharmacyController.managerController = new ManagerController();

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTree = new javax.swing.JTree();
        namrLbl = new javax.swing.JLabel();
        descLbl = new javax.swing.JLabel();
        qtyLbl = new javax.swing.JLabel();
        priceLbl = new javax.swing.JLabel();
        hierarLbl = new javax.swing.JLabel();
        hierField = new javax.swing.JTextField();
        qtyField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descArea = new javax.swing.JTextArea();
        saveBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Avenir", 1, 36)); // NOI18N
        jLabel1.setText("Manage Products:  Add Product");

        Map<String, DefaultMutableTreeNode> nodeMap = new HashMap<>();

        HashMap<String,String> childParentMap = PharmacyController.managerController.getParentChildCMap();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");


        for (Map.Entry<String, String> entry : childParentMap.entrySet())
        {
            String child = entry.getKey(); // Child
            String parent = entry.getValue(); // Parent

            if(!parent.equals("NULL")) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child); // Making a Child Node

                if (!nodeMap.containsKey(child)) {
                    nodeMap.put(child, childNode);

                    if (childParentMap.containsKey(parent) && !nodeMap.containsKey(parent)) // Parent itself is a Child of a Grand-Parent
                    {
                        while (childParentMap.containsKey(parent)) {

                            DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();

                            if (nodeMap.containsKey(parent)) {
                                parentNode = nodeMap.get(parent);
                            } else {
                                parentNode = new DefaultMutableTreeNode(parent);
                            }

                            parentNode.add(childNode);
                            nodeMap.put(parent, parentNode);

                            child = parent; // Parent becomes Child
                            parent = childParentMap.get(child); // Grand Parents of Child

                            if (!nodeMap.containsKey(child))
                                childNode = new DefaultMutableTreeNode(child);
                            else {
                                childNode = nodeMap.get(child);
                            }

                            if (!childParentMap.containsKey(parent)) {
                                if (nodeMap.containsKey(parent)) {
                                    parentNode = nodeMap.get(parent);
                                } else {
                                    parentNode = new DefaultMutableTreeNode(parent);
                                }
                                parentNode.add(childNode);
                                nodeMap.put(parent, parentNode);
                                root.add(parentNode);
                            }
                        }
                    } else if (nodeMap.containsKey(parent)) {
                        DefaultMutableTreeNode parentNode = nodeMap.get(parent);
                        parentNode.add(childNode);
                    } else {
                        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();

                        if (nodeMap.containsKey(parent)) {
                            parentNode = nodeMap.get(parent);
                        } else {
                            parentNode = new DefaultMutableTreeNode(parent);
                        }
                        parentNode.add(childNode);
                        nodeMap.put(parent, parentNode);
                        root.add(parentNode);
                    }
                }
            }
            else
            {
                if (nodeMap.containsKey(child)) {
                    DefaultMutableTreeNode rootParentNode = nodeMap.get(child);
                    nodeMap.put(child, rootParentNode);
                    root.add(rootParentNode);
                } else {
                    DefaultMutableTreeNode rootParentNode = new DefaultMutableTreeNode(child);
                    nodeMap.put(child, rootParentNode);
                    root.add(rootParentNode);
                }

            }

        }

        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        categoryTree.setModel(treeModel);
        categoryTree.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(categoryTree);

        namrLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        namrLbl.setText("Product Name:");

        descLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        descLbl.setText("Product Desrciption:");

        qtyLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        qtyLbl.setText("Quantity Per Pack:");

        priceLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        priceLbl.setText("Price:");

        hierarLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        hierarLbl.setText("Category Hierarchy:");

        hierField.setEditable(false);
        hierField.setFont(new java.awt.Font("Avenir", 0, 16)); // NOI18N


        qtyField.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N

        priceField.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N


        nameField.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N


        descArea.setColumns(20);
        descArea.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        descArea.setRows(5);
        jScrollPane2.setViewportView(descArea);

        saveBtn.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        saveBtn.setText("Save");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(145, 145, 145)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(descLbl)
                                        .addComponent(qtyLbl)
                                        .addComponent(priceLbl)
                                        .addComponent(namrLbl)
                                        .addComponent(hierarLbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hierField, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(priceField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                                .addComponent(qtyField, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(603, 603, 603))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(459, 459, 459)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(137, 137, 137)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(jLabel1)
                                                .addGap(58, 58, 58)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(hierarLbl)
                                                        .addComponent(hierField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(37, 37, 37)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(namrLbl)
                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(39, 39, 39)
                                                                .addComponent(descLbl)
                                                                .addGap(69, 69, 69))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(36, 36, 36)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(qtyLbl)
                                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(43, 43, 43)
                                                                .addComponent(priceLbl))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(35, 35, 35)
                                                                .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(53, 53, 53)
                                .addComponent(saveBtn)
                                .addContainerGap(223, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1502, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(25, Short.MAX_VALUE))
        );


        categoryTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();

                if (selectedNode != null) {

                    TreePath path = new TreePath(selectedNode.getPath());
                    System.out.println("Selected Node Path: " + path);

                    String hierarchy = String.valueOf(path).replaceAll(","," -> ");
                    hierarchy = hierarchy.replaceAll("\\[","");
                    hierarchy = hierarchy.replaceAll("]","");
                    hierField.setText(hierarchy);
                }
            }});

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();


                if (selectedNode != null) {

                    TreePath path = new TreePath(selectedNode.getPath());
                    String hierarchy = String.valueOf(path).replaceAll(", ","/");
                    hierarchy = hierarchy.replaceAll("\\[","");
                    hierarchy = hierarchy.replaceAll("]","");


                    String pName = nameField.getText().toUpperCase();
                    String desc = descArea.getText();
                    Integer qtyPerPack = null;
                    Float price = null;

                    if (!qtyField.getText().matches("\\d+") || !priceField.getText().matches("\\d+(\\.\\d+)?")) {
                        JOptionPane.showMessageDialog(AddProductUI.this, "Invalid Input for Quantity or Price");
                        return;
                    }
                    if(!qtyField.getText().isEmpty())
                        qtyPerPack = Integer.valueOf(qtyField.getText());
                    if(!priceField.getText().isEmpty())
                        price = Float.valueOf(priceField.getText());


                    if(pName.isEmpty() || desc.isEmpty() || qtyPerPack == null|| price == null)
                    {
                        JOptionPane.showMessageDialog(AddProductUI.this,"Field(s) are Empty");
                    }
                    else {


                    try {
                        if (PharmacyController.managerController.getSearchedProd(pName) == null) {
                            PharmacyController.managerController.addProduct(pName, desc, qtyPerPack, price, hierarchy,selectedNode.toString());
                            JOptionPane.showMessageDialog(AddProductUI.this,"Product " + pName + " Added Successfully");
                        }
                        else {
                            JOptionPane.showMessageDialog(AddProductUI.this,"Product " + pName + " Already Exists");
                        }

                        TreeSelectionModel treeSelectionModel = categoryTree.getSelectionModel();
                        treeSelectionModel.clearSelection();
                        hierField.setText("");
                        nameField.setText("");
                        descArea.setText("");
                        qtyField.setText("");
                        priceField.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }}
            }
        });
        pack();
    }


    public static void main(String[] args) {


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddProductUI().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTree categoryTree;
    private javax.swing.JTextArea descArea;
    private javax.swing.JLabel descLbl;
    private javax.swing.JTextField hierField;
    private javax.swing.JLabel hierarLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel namrLbl;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JTextField qtyField;
    private javax.swing.JLabel qtyLbl;
    private javax.swing.JButton saveBtn;
// End of variables declaration
}
