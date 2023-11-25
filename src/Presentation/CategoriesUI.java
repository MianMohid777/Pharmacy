package Presentation;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class CategoriesUI extends JFrame {

    public CategoriesUI() {

        setTitle("Category Tree ");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample data
        String[] data = {
                "25,Di-178719197,Ga1475053137",
                "26,Di-178719197,Ki-2047723204",
                "27,Di-178719197,Ca-2076370661",
                "28,Di-178719197,Bo64368143"
        };

        // Build a map to store nodes by id
        Map<String, String> nodeMap = new HashMap<>();

        // Create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
        nodeMap.put("Categories", "Root");

        // Process the data and create nodes
        for (String line : data) {
            String[] parts = line.split(",");
            String id = parts[0];
            String parent = parts[1];
            String child = parts[2];

            // Create a new node
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(child);

            // Add the node to the map
            nodeMap.put(child, parent);

            if(nodeMap.containsKey(parent))
            {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
                root.add(parentNode);
            }
            else {

                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
                parentNode.add(node);
            }
        }

        // Create the tree model with the root node
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        // Create the JTree with the tree model
        JTree tree = new JTree(treeModel);

        // Add the JTree to a JScrollPane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
    }
    public static void main(String[]args)
    {
        CategoriesUI ui = new CategoriesUI();
    }
}
