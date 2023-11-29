package Presentation.View;

import Application.Model.Category;
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

public class CatalogUI extends javax.swing.JFrame {

    private Category c;
    public CatalogUI( ) throws SQLException {
        initComponents();
    }


    private void initComponents() throws SQLException {


        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        treePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTree = new javax.swing.JTree();
        categoryPanel = new javax.swing.JPanel();
        categoryNameLbl = new javax.swing.JLabel();
        catDescLbl = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        catUpdateBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        addCatPanel = new javax.swing.JPanel();
        addCatLbl = new javax.swing.JLabel();
        subCatNameLbl = new javax.swing.JLabel();
        subCatDescLbl = new javax.swing.JLabel();
        catHierarchyLbl = new javax.swing.JLabel();
        subCatDescLbl2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        saveBtn = new javax.swing.JButton();
        hierLbl = new javax.swing.JLabel();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pharmacy-POS");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        setName("categoryFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1620, 1000));

        setExtendedState(JFrame.MAXIMIZED_BOTH);




        titlePanel.setFont(new java.awt.Font("Avenir", 3, 13)); // NOI18N

        titleLabel.setFont(new java.awt.Font("Heiti TC", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Product Catalog");
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
                titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addGap(453, 453, 453)
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
                titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(28, Short.MAX_VALUE))
        );


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
        categoryTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();

                if (selectedNode != null) {

                    TreePath path = new TreePath(selectedNode.getPath());
                    System.out.println("Selected Node Path: " + path);

                    hierLbl.setText(String.valueOf(path));

                    c = PharmacyController.managerController.getCategoryMap().get(selectedNode.toString());

                    if(c != null)
                    {
                        nameField.setText(c.getName());
                        jTextArea1.setText(c.getDesc());


                    }
                    else
                    {
                        nameField.setText("");
                        jTextArea1.setText("");
                    }
                }
            }});

        categoryTree.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(categoryTree);

        javax.swing.GroupLayout treePanelLayout = new javax.swing.GroupLayout(treePanel);
        treePanel.setLayout(treePanelLayout);
        treePanelLayout.setHorizontalGroup(
                treePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, treePanelLayout.createSequentialGroup()
                                .addContainerGap(23, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
        );
        treePanelLayout.setVerticalGroup(
                treePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        categoryPanel.setBackground(new java.awt.Color(255, 255, 255));
        categoryPanel.setFont(new java.awt.Font("Avenir", 0, 13)); // NOI18N

        categoryNameLbl.setFont(new java.awt.Font("Avenir", 0, 18)); // NOI18N
        categoryNameLbl.setText("Category Name:");

        catDescLbl.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        catDescLbl.setText("Category Description:");

        nameField.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        nameField.setToolTipText("");


        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        catUpdateBtn.setBackground(new java.awt.Color(0, 204, 255));
        catUpdateBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        catUpdateBtn.setText("UPDATE");

        catUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(c != null && !nameField.getText().isEmpty() && !jTextArea1.getText().isEmpty())
                {
                    if(!c.getName().equalsIgnoreCase(nameField.getText()) || !c.getDesc().equals(jTextArea1.getText()))
                    {
                        String exName = c.getName();
                        try {
                            if(!exName.equals(nameField.getText()))
                            {
                                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();

                                PharmacyController.managerController.getCategoryMap().remove(exName);
                                PharmacyController.managerController.getCategoryMap().put(nameField.getText().trim(),c);
                                PharmacyController.managerController.updateHierarchy(exName,nameField.getText());

                                if(PharmacyController.managerController.getCategoryMap().containsKey(nameField.getText()))
                                {
                                    if(!nameField.getText().equals(selectedNode.toString()))
                                    {
                                        selectedNode.setUserObject(nameField.getText());
                                        DefaultTreeModel treeModel1 = (DefaultTreeModel) categoryTree.getModel();
                                        treeModel1.nodeChanged(selectedNode);

                                    }
                                }
                            }
                            PharmacyController.managerController.updateCategory(nameField.getText(),jTextArea1.getText(),c.getCode());

                            hierLbl.setText("");
                            nameField.setText("");
                            jTextArea1.setText("");

                            TreeSelectionModel selectionModel = categoryTree.getSelectionModel();
                            selectionModel.clearSelection();

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
                else {
                    JOptionPane.showMessageDialog(CatalogUI.this,"Field(s) are Empty");
                }
            }
        });

        delBtn.setBackground(new java.awt.Color(255, 102, 102));
        delBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        delBtn.setText("DELETE");




        jLabel1.setFont(new java.awt.Font("Avenir", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Selected Category");

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
                categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                                .addGap(64, 64, 64)
                                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(catDescLbl)
                                                        .addComponent(categoryNameLbl))
                                                .addGap(18, 18, 18)
                                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                                                .addComponent(catUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(48, 48, 48)
                                                                .addComponent(delBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                                .addGap(422, 422, 422)
                                                .addComponent(jLabel1)))
                                .addContainerGap(623, Short.MAX_VALUE))
        );
        categoryPanelLayout.setVerticalGroup(
                categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(categoryNameLbl)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(catDescLbl)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(catUpdateBtn)
                                        .addComponent(delBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(80, Short.MAX_VALUE))
        );

        addCatPanel.setBackground(new java.awt.Color(255, 255, 255));

        addCatLbl.setFont(new java.awt.Font("Avenir", 1, 24)); // NOI18N
        addCatLbl.setText("Add Category");

        subCatNameLbl.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        subCatNameLbl.setText("Category Name:");

        catHierarchyLbl.setFont(new java.awt.Font("Avenir Next", 3, 14)); // NOI18N

        subCatDescLbl2.setFont(new java.awt.Font("Avenir", 1, 18)); // NOI18N
        subCatDescLbl2.setText("Category Description:");

        jTextField1.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N


        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Avenir", 0, 14)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        saveBtn.setBackground(new java.awt.Color(0, 204, 255));
        saveBtn.setFont(new java.awt.Font("Avenir", 1, 14)); // NOI18N
        saveBtn.setText("SAVE");

        saveBtn.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {

                                          DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();
                                          TreePath selectedPath = categoryTree.getSelectionPath();
                                          if (selectedNode != null) {
                                              if (jTextField1.getText().isEmpty() || jTextArea2.getText().isEmpty()) {
                                                  JOptionPane.showMessageDialog(CatalogUI.this, "Field(s) arte Empty !");
                                              } else if (PharmacyController.managerController.getCategoryMap().containsKey(jTextField1.getText())) {
                                                  JOptionPane.showMessageDialog(CatalogUI.this, "Category Already Exists !");
                                              } else {
                                                  try {
                                                      PharmacyController.managerController.addCategory(jTextField1.getText(), jTextArea2.getText());

                                                      if (!selectedNode.toString().equals("Categories")) {
                                                          PharmacyController.managerController.addSubCategory(selectedNode.toString(), jTextField1.getText());

                                                      }

                                                      DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(jTextField1.getText());
                                                      selectedNode.add(newNode);
                                                      DefaultTreeModel treeModel2 = (DefaultTreeModel) categoryTree.getModel();
                                                      treeModel2.nodeChanged(selectedNode);
                                                      TreeSelectionModel selectionModel1 = categoryTree.getSelectionModel();
                                                      selectionModel1.clearSelection();

                                                      jTextField1.setText("");
                                                      jTextArea2.setText("");
                                                      hierLbl.setText("");

                                                  } catch (SQLException ex) {
                                                      throw new RuntimeException(ex);
                                                  }


                                              }


                                          }
                                      }
                                  });


        hierLbl.setFont(new java.awt.Font("Avenir", 3, 15)); // NOI18N
        hierLbl.setText("");

        javax.swing.GroupLayout addCatPanelLayout = new javax.swing.GroupLayout(addCatPanel);
        addCatPanel.setLayout(addCatPanelLayout);
        addCatPanelLayout.setHorizontalGroup(
                addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(addCatLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hierLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(catHierarchyLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58))
                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(subCatNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(subCatDescLbl2))
                                .addGap(32, 32, 32)
                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(157, 157, 157)
                                                .addComponent(subCatDescLbl)))
                                .addContainerGap(461, Short.MAX_VALUE))
        );
        addCatPanelLayout.setVerticalGroup(
                addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addCatLbl)
                                        .addComponent(catHierarchyLbl)
                                        .addComponent(hierLbl))
                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(subCatDescLbl)
                                                .addGap(106, 106, 106))
                                        .addGroup(addCatPanelLayout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(subCatNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(22, 22, 22)
                                                .addGroup(addCatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(subCatDescLbl2)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(saveBtn)
                                                .addContainerGap(21, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(addCatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(treePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(treePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addCatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }


    public static void main(String[] args) throws SQLException {
       /* PharmacyController control = new PharmacyController();
        PharmacyController.managerController = new ManagerController();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CatalogUI(control).setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });*/
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel addCatLbl;
    private javax.swing.JPanel addCatPanel;
    private javax.swing.JLabel catDescLbl;
    private javax.swing.JLabel catHierarchyLbl;
    private javax.swing.JButton catUpdateBtn;
    private javax.swing.JLabel categoryNameLbl;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JTree categoryTree;
    private javax.swing.JButton delBtn;

    private javax.swing.JLabel hierLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel subCatDescLbl;
    private javax.swing.JLabel subCatDescLbl2;
    private javax.swing.JLabel subCatNameLbl;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JPanel treePanel;
    // End of variables declaration
}
