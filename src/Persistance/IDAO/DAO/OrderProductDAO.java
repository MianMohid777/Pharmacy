package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.OrderProduct_IDAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    public HashMap<Integer, String> getAllProductsByOrderId(int oid) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orderProd WHERE orderId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,oid);
        ResultSet set = statement.executeQuery();

        HashMap<Integer,String> productMap = new HashMap<>();

        while(set.next())
        {
            productMap.put(set.getInt(2), set.getString(3));

        }

        return productMap;
    }
}
