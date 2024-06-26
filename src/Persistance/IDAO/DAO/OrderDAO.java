package Persistance.IDAO.DAO;

import Application.Model.*;
import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.IDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class OrderDAO implements IDAO<Order> {


    private ResultSet rs;

    public OrderDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orders";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();

    }

    @Override
    public void save(Order o) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "INSERT INTO orders(customerName, userName, time, total) VALUES (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,o.getCustomerName());
        statement.setString(2,o.getName());
        statement.setString(3,String.valueOf(o.getTimeStamp()));
        statement.setFloat(4,o.getTotalPriceFromContainer());
        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();

        if(keys.next())
        {
            o.setId(keys.getInt(1));
        }

    }

    @Override
    public void delete(Object id) throws SQLException {

        int oid = Integer.parseInt(id.toString());

        rs.absolute(0);

        while(rs.next() && rs.getInt(1) != oid);

        rs.deleteRow();

        rs.moveToCurrentRow();
    }

    @Override
    public void update(Order o) throws SQLException {

        rs.absolute(0);

        while(rs.next() && !Objects.equals(rs.getString(4), String.valueOf(o.getTimeStamp())));

        rs.updateString(2,o.getCustomerName());
        rs.updateString(3,o.getName());
        rs.updateString(4,String.valueOf(o.getTimeStamp()));
        rs.updateFloat(5,o.getTotalPriceFromContainer());

        rs.updateRow();

        rs.moveToCurrentRow();

    }

    @Override
    public List<Order> findAll() throws SQLException {

        rs.absolute(0);

        List<Order> orders = new LinkedList<>();
        while(rs.next())
        {
            Order o = new Order();
            o.setId(rs.getInt(1));
            o.setCustomerName(rs.getString(2));
            o.setTimeStamp((LocalDateTime) rs.getObject(4));

            orders.add(o);
        }
        return orders;
    }

    @Override
    public Order findById(Object id) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orders WHERE oid = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,String.valueOf(id));

        ResultSet set = statement.executeQuery();

        if(set.next())
        {
            Order o = new Order();

            o.setId(set.getInt(1));
            o.setCustomerName(set.getString(2));
            o.setName(set.getString(3));
            o.setTimeStamp((LocalDateTime) set.getObject(4));
            o.setTotalPrice(set.getFloat(5));

            return o;
        }
        return new Order();
    }

    public List<Order> findByDate(LocalDateTime startDate,LocalDateTime endDate) throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM orders WHERE time BETWEEN ? AND ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.valueOf(startDate));
        statement.setTimestamp(2, Timestamp.valueOf(endDate));

        ResultSet set = statement.executeQuery();
        List<Order> orderList = new LinkedList<>();

        while(set.next())
        {
            Order o = new Order();

            o.setId(set.getInt(1));
            o.setName(set.getString(2));
            o.setCustomerName(set.getString(3));
            o.setTimeStamp((LocalDateTime) set.getObject(4));
            o.setTotalPrice(set.getFloat(5));

            orderList.add(o);

        }

        return orderList;
    }

    public Float findByTotalByDate(LocalDateTime startDate,LocalDateTime endDate) throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT SUM(total) FROM orders WHERE time BETWEEN ? AND ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.valueOf(startDate));
        statement.setTimestamp(2, Timestamp.valueOf(endDate));

        ResultSet set = statement.executeQuery();


        if(set.next())
        {
           return set.getFloat(1);

        }

        return null;
    }


}
