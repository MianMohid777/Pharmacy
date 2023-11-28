package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.Intermediate_IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductCategoryDAO implements Intermediate_IDAO {

    private ResultSet rs;

    public ProductCategoryDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM product_category";
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

        while(rs.next() && (rs.getInt(2)!= (Integer)x || rs.getInt(3)!= (Integer)y ));

        rs.deleteRow();
        rs.moveToCurrentRow();
    }

    public void update(Object x,Object y) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "UPDATE product_category SET product_category.hierarchy = ? WHERE prodCode = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,String.valueOf(y));
        statement.setString(2,String.valueOf(x));

        statement.executeUpdate();
    }
    // { Prescription Medications/Antibiotics/Penicillin } //

    public List<String> findProductInCategory(String name) throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM product_category WHERE hierarchy REGEXP ? ";
        PreparedStatement statement = conn.prepareStatement(query);
        String regexPattern = "\\b" + name + "\\b";

        statement.setString(1,regexPattern);
        ResultSet set = statement.executeQuery();

        List<String> codes = new LinkedList<>();

        while(set.next())
        {
             codes.add(set.getString(2));

        }

        return codes;
    }

    public HashMap<String,String> findAllProductCategories() throws SQLException {
        rs.absolute(0);

        HashMap<String,String> hierarchyMap = new HashMap<>();
        while(rs.next())
        {
            hierarchyMap.put(rs.getString(2),rs.getString(3));
        }

        return hierarchyMap;
    }
    public static void main(String[] args) throws SQLException {
        ProductCategoryDAO dao=new ProductCategoryDAO();

        for(String s:dao.findProductInCategory("Antibiotics") )
             System.out.println(s);

    }
}
