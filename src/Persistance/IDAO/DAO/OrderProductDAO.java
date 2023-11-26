package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.OrderProduct_IDAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderProductDAO implements OrderProduct_IDAO {

    private ResultSet rs;

    public OrderProductDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orderProd";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }
    @Override
    public void save(int oid, String code, int qty) throws SQLException {

        rs.moveToInsertRow();

        rs.updateInt(2,oid);
        rs.updateString(3,code);
        rs.updateInt(4,qty);

        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public List<String> getAllProductsByOrderId(int oid) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orderProd WHERE orderId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,oid);
        ResultSet set = statement.executeQuery();

        List<String> productList = new LinkedList<>();

        while(set.next())
        {
            productList.add(set.getString(3));

        }

        return productList;
    }

    public Integer salesQty(Integer minId,Integer maxId, String code) throws SQLException
    {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT SUM(qty) FROM orderProd WHERE  prodCode = ? AND orderProd.orderId BETWEEN ? AND ?;";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,code);
        statement.setInt(2,minId);
        statement.setInt(3,maxId);


        ResultSet set = statement.executeQuery();

        if(set.next())
            return set.getInt(1);

        return null;
    }

    public List<String> getProductsInPeriod(Integer minId,Integer maxId) throws SQLException
    {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT prodCode FROM orderProd WHERE orderProd.orderId BETWEEN ? AND ? GROUP BY prodCode";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,minId);
        statement.setInt(2,maxId);

        ResultSet set = statement.executeQuery();

        List<String> productList = new LinkedList<>();

        while(set.next())
            productList.add(set.getString(1));

        return productList;
    }
}
