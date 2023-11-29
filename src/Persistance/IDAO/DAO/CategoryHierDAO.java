package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.Intermediate_IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryHierDAO implements Intermediate_IDAO {

    private ResultSet rs;

    public CategoryHierDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM category_hierarchy";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }
    @Override
    public void save(Object x, Object y) throws SQLException {

        rs.moveToInsertRow();

        rs.updateObject(2,x);
        rs.updateObject(3,y);

        rs.insertRow();
        rs.moveToCurrentRow();
    }

    @Override
    public void delete(Object x, Object y) throws SQLException {

        rs.absolute(0);

        while(rs.next() && (rs.getString(1)!= x || rs.getString(2)!= y ));

        rs.deleteRow();
        rs.moveToCurrentRow();
    }

    @Override
    public void update(Object x, Object y) throws SQLException {

    }


    public List<String> getTree(String leafId) throws SQLException {

        List<String> nodes = new LinkedList<>();
        Connection conn = DB_Connection.getConnection();

        String query = "WITH RECURSIVE CategoryTree AS ( SELECT id, catId, subId FROM category_hierarchy WHERE subId = ? UNION ALL SELECT h.id, h.catId, h.subId FROM category_hierarchy h JOIN CategoryTree tree ON h.subId = tree.catId) SELECT * FROM CategoryTree ORDER BY id ASC";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1,(leafId));

        while(rs.next())
        {
           nodes.add(rs.getString(2));
        }

        return nodes;
    }

    public HashMap<String,String> getAllParentChild() throws SQLException {

        rs.absolute(0);

        HashMap<String,String> relMap = new HashMap<>();  // Relation Map

        while(rs.next())
        {
            relMap.put(rs.getString(3), rs.getString(2));

        }

        return relMap;

    }
}
