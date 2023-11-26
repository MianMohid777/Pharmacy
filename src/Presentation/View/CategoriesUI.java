package Presentation.View;

import Presentation.Controller.Main.PharmacyController;
import Presentation.Controller.Supporting.UserController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class CategoriesUI extends JFrame {

    public CategoriesUI(PharmacyController controller) {

        JOptionPane.showMessageDialog(null, UserController.getU().getName());
        setTitle("Categories");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        Map<String, DefaultMutableTreeNode> nodeMap = new HashMap<>();

        HashMap<String,String> childParentMap = PharmacyController.managerController.getParentChildCMap();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");


        for (Map.Entry<String, String> entry : childParentMap.entrySet())
        {
            String child = entry.getKey(); // Child
            String parent = entry.getValue(); // Parent

            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child); // Making a Child Node

            if(!nodeMap.containsKey(child))
            {
                nodeMap.put(child,childNode);

                if(childParentMap.containsKey(parent) && !nodeMap.containsKey(parent) ) // Parent itself is a Child of a Grand-Parent
                {
                    while(childParentMap.containsKey(parent))
                    {

                        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();

                        if(nodeMap.containsKey(parent)) {
                            parentNode = nodeMap.get(parent);
                        }
                        else
                        {
                             parentNode = new DefaultMutableTreeNode(parent);
                        }

                        parentNode.add(childNode);
                        nodeMap.put(parent,parentNode);

                        child = parent; // Parent becomes Child
                        parent = childParentMap.get(child); // Grand Parents of Child

                        if(!nodeMap.containsKey(child))
                           childNode = new DefaultMutableTreeNode(child);
                        else
                        {
                            childNode = nodeMap.get(child);
                        }

                        if(!childParentMap.containsKey(parent) )
                        {
                            if(nodeMap.containsKey(parent)) {
                                parentNode = nodeMap.get(parent);
                            }
                            else
                            {
                                parentNode = new DefaultMutableTreeNode(parent);
                            }
                            parentNode.add(childNode);
                            nodeMap.put(parent,parentNode);
                            root.add(parentNode);
                        }
                    }
                }
                else if(nodeMap.containsKey(parent))
                {
                    DefaultMutableTreeNode parentNode = nodeMap.get(parent);
                    parentNode.add(childNode);
                }
                else {
                    DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode();

                    if(nodeMap.containsKey(parent)) {
                        parentNode = nodeMap.get(parent);
                    }
                    else {
                        parentNode = new DefaultMutableTreeNode(parent);
                    }
                    parentNode.add(childNode);
                    nodeMap.put(parent,parentNode);
                    root.add(parentNode);
                }
            }


        }


        DefaultTreeModel treeModel = new DefaultTreeModel(root);


        JTree tree = new JTree(treeModel);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null) {

                    TreePath path = new TreePath(selectedNode.getPath());
                    System.out.println("Selected Node Path: " + path);
            }
        }});


        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }




    public static void main(String[] args) {
        // Example child-parent map
        Map<String, String> childParentMap = new HashMap<>();
        childParentMap.put("Accessories", "Mobile");
        childParentMap.put("Mobile", "Electronics");
        childParentMap.put("Tv", "Electronics");
        childParentMap.put("Smartphone", "Mobile");
        childParentMap.put("Laptop", "Electronics");
        childParentMap.put("Dell", "Laptop");
        childParentMap.put("Tablet", "Mobile");
        childParentMap.put("Samsung", "Tablet");
        childParentMap.put("Sony", "Tv");
        childParentMap.put("Camera", "Electronics");
        childParentMap.put("Nikon", "Camera");
        childParentMap.put("Canon", "Camera");
        childParentMap.put("Refrigerator", "Appliances");
        childParentMap.put("WashingMachine", "Appliances");


    }
}

