package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.ProductStock_IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ProductStockDAO implements ProductStock_IDAO {

    private ResultSet rs;
    public ProductStockDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM ProductStock";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }
    @Override
    public void save(String pid, int qty, LocalDate expiry) throws SQLException {

        rs.moveToInsertRow();

        rs.updateString(2, pid);
        rs.updateString(3, String.valueOf(qty));
        rs.updateString(4, String.valueOf(expiry));

        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public int delete(Integer stockId) throws SQLException {

        rs.absolute(0);

        int qty = 0;
        while(rs.next() && rs.getInt(1) != stockId);

        qty = rs.getInt(3);

        rs.deleteRow();

        rs.moveToCurrentRow();

        return qty;

    }

    @Override
    public void update(String pid,int need) throws SQLException {

        Connection conn = DB_Connection.getConnection();

        String query = "SELECT * FROM ProductStock WHERE (expiryDate - curdate() > 0) AND prodCode = ? ORDER BY expiryDate";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        statement.setString(1,pid);
        ResultSet set = statement.executeQuery();


        if(set.next() && need > 0)
        {
            int avail = set.getInt(3);
            int rem = 0;
            if(avail > need)
            {
                rem = avail - need;
                set.updateInt(3,rem);
                need = 0;
                set.updateRow();
            }
            else
            {
                need = need - avail;
                set.deleteRow();
            }

        }


    }

    @Override
    public List<Integer> checkExpiry() throws SQLException {

        List<Integer> stockIds = new LinkedList<>();

        Connection conn = DB_Connection.getConnection();
        String query = " SELECT stockId,prodCode FROM ProductStock WHERE (expiryDate - CURDATE() <= 0) GROUP BY prodCode, stockId";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE);

        ResultSet set = statement.executeQuery();

        while(set.next())
        {
            stockIds.add(set.getInt(1));
        }

        return stockIds;
    }

    @Override
    public HashMap<Integer,String> getExpiredProduct(List<Integer> stockIds) throws SQLException {

        rs.absolute(0);
        HashMap<Integer,String> expiredMap = new HashMap<>();
        int i = 0;


        while(rs.next() && i< stockIds.size())
        {
            int id = stockIds.get(i);

            if(rs.getInt(1)==id)
            {
              expiredMap.put(id,rs.getString(2));

                i++;
            }
        }
        return expiredMap;
    }

    @Override
    public Integer findStock(Integer id, String code) throws SQLException {

        Connection conn = DB_Connection.getConnection();

        String query = "SELECT * FROM ProductStock WHERE stockId = ? AND prodCode = ?";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE);
        statement.setInt(1,id);
        statement.setString(2,code);
        ResultSet set = statement.executeQuery();

        return (set.next() ? set.getInt(3):null);
    }

    @Override
    public List<Vector<Object>> findStockByProduct(String code) throws SQLException
    {

        Connection conn = DB_Connection.getConnection();

        String query = "SELECT * FROM ProductStock WHERE  prodCode = ?";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE);
        statement.setString(1,code);
        ResultSet set = statement.executeQuery();

        List<Vector<Object>> stocks = new LinkedList<>();

        while(set.next())
        {
            Vector<Object>  rowDate = new Vector<>();

            rowDate.add(set.getInt(1));
            rowDate.add(set.getString(2));
            rowDate.add(set.getInt(3));
            rowDate.add(set.getDate(4));

            stocks.add(rowDate);
        }

        return stocks;
    }
}
