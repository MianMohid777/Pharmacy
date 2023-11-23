package Persistance.IDAO.DAO;

import Application.Model.Manager;
import Application.Model.Role;
import Application.Model.Sales_Assistant;
import Application.Model.User;
import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.IDAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAO implements IDAO<User> {

    private ResultSet rs;

    public UserDAO() throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM user";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();

    }
    @Override
    public void save(User u) throws SQLException {

        rs.moveToInsertRow();
        rs.updateString(2,u.getName());
        rs.updateString(3,u.getUserName());
        rs.updateString(4,u.getPassWord());

        if(u.getRole() instanceof Manager)
            rs.updateString(5,"M");
        else if(u.getRole() instanceof Sales_Assistant)
            rs.updateString(5,"A");
        else
            System.out.println("Role Not Defined");

        rs.insertRow();

        rs.moveToCurrentRow();

    }
    @Override
    public void delete(Object id) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "DELETE FROM user WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, (Integer) id);
        statement.executeUpdate();
    }

    @Override
    public List<User> findAll() throws SQLException {

        List<User> users = new LinkedList<>();

        rs.absolute(0);

        while(rs.next())
        {
            User u = new User();

            u.setName(rs.getString(2));
            u.setUserName(rs.getString(3));
            u.setPassWord(rs.getString(3));

            if(rs.getString(4).equals("M"))
                u.setRole(new Manager());
            else if(rs.getString(4).equals("A"))
               u.setRole(new Sales_Assistant());
            else
                u.setRole(null);

            users.add(u);

        }

        return users;
    }

    @Override
    public User findById(Object id) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, (Integer) id);
        ResultSet set = statement.executeQuery();

        if(set.next())
        {
            User u = new User();

            u.setName(set.getString(2));
            u.setUserName(set.getString(3));
            u.setPassWord(set.getString(4));

            if(set.getString(5).equals("M"))
                u.setRole(new Manager());
            else if(set.getString(5).equals("A"))
                u.setRole(new Sales_Assistant());
            else
                u.setRole(null);

            return u;
        }

        return null;
    }

    @Override
    public void update(User u) throws SQLException {


        rs.absolute(0);

        while(rs.next() && !rs.getString(3).equals(u.getUserName()));

        rs.updateString(2,u.getName());
        rs.updateString(3,u.getUserName());
        rs.updateString(4,u.getPassWord());

        if(u.getRole() instanceof Manager)
            rs.updateString(5,"M");
        else if(u.getRole() instanceof Sales_Assistant)
            rs.updateString(5,"A");
        else
            System.out.println("Role Not Defined");

        rs.updateRow();
    }


    public User findByUserName(String username) throws SQLException {

        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM user WHERE userName = ?";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();

        if(set.next())
        {
            User u = new User();

            u.setName(set .getString(2));
            u.setUserName(set.getString(3));
            u.setPassWord(set.getString(4));

            if(set.getString(5).equals("M"))
                u.setRole(new Manager());
            else if(set.getString(5).equals("A"))
                u.setRole(new Sales_Assistant());
            else
                u.setRole(null);

            return u;
        }

        return null;
    }

    public static void main(String[] args) throws SQLException {
        Role r = new Manager();
        Role r2 = new Sales_Assistant();

        User u = new User("Mohid","mohid008","test1234",r);
        User u2 = new User("Mohid","mohid009","test1234",r2);

        UserDAO dao = new UserDAO();

        //dao.save(u);
        //dao.save(u2);

        //dao.delete(5);

    }
}
