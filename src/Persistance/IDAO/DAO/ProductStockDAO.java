package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.ProductStock_IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ProductStockDAO implements ProductStock_IDAO {

    private ResultSet rs;
    ProductStockDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM ProductStock";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }
    @Override
    public void save(int pid, int qty, Date expiry) throws SQLException {

        rs.moveToInsertRow();

        rs.updateString(2, String.valueOf(pid));
        rs.updateString(3, String.valueOf(qty));
        rs.updateString(4, String.valueOf(expiry));

        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public int delete(int pid,Date expiry) throws SQLException {

        rs.absolute(0);

        int qty = 0;
        while(rs.next() && (rs.getInt(2) != pid || rs.getDate(4) != expiry));

        qty = rs.getInt(3);

        rs.deleteRow();

        rs.moveToCurrentRow();

        return qty;

    }

    @Override
    public void update(int pid,int need) throws SQLException {

        Connection conn = DB_Connection.getConnection();

        String query = "SELECT * FROM ProductStock WHERE (expiryDate - curdate() > 0) AND pid = ? ORDER BY expiryDate";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1,pid);
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
        String query = " SELECT stockId FROM ProductStock WHERE (expiryDate - CURDATE() = 0)";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

        ResultSet set = statement.executeQuery();

        while(set.next())
        {
            stockIds.add(set.getInt(1));
        }

        return stockIds;
    }

    @Override
    public Vector<Object> getExpiredProduct(List<Integer> stockIds) throws SQLException {

        rs.absolute(0);
        Vector<Object> prodExp = new Vector<>();
        int i = 0;


        while(rs.next())
        {
            int id = stockIds.get(i);

            if(rs.getInt(1)==id)
            {
                prodExp.add(rs.getInt(2));
                prodExp.add(rs.getInt(4));

                i++;
            }
        }
        return prodExp;
    }
}
