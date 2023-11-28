package Persistance.IDAO.DAO;

import Application.Model.Category;
import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAO implements IDAO<Category> {


    private ResultSet rs;

    public CategoryDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM categories";
        PreparedStatement statement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }
    @Override
    public void save(Category c) throws SQLException {

        rs.moveToInsertRow();

        rs.updateString(2, c.getName());
        rs.updateString(3, c.getCode());
        rs.updateString(4, c.getDesc());



        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public void delete(Object id) throws SQLException {

        String code = (String) id;
        rs.absolute(0);

        while(rs.next() && !rs.getString(2).equals(code));;

        rs.deleteRow();

        rs.moveToCurrentRow();
    }

    @Override
    public List<Category> findAll() throws SQLException {
        rs.absolute(0);

        List<Category> categories = new LinkedList<>();

        while(rs.next()) {
            Category c = new Category();

            c.setName(rs.getString(2));
            c.setCode(rs.getString(3));
            c.setDesc(rs.getString(4));

            categories.add(c);

        }
        return categories;
    }

    @Override
    public Category findById(Object id) throws SQLException {

        String code = (String) id;

        rs.absolute(0);

        Category c = new Category();

        while(rs.next() && !rs.getString(3).equals(code));

        c.setName(rs.getString(2));
        c.setCode(rs.getString(3));
        c.setDesc(rs.getString(4));

        return c;
    }

    @Override
    public void update(Category c) throws SQLException {

        String code = c.getCode();

        rs.absolute(0);

        while(rs.next() && !rs.getString(3).equals(code));

        if (!rs.getString(2).equals(c.getName()) || !rs.getString(4).equals(c.getDesc())) {
            rs.updateString(2, c.getName());
            rs.updateString(3, c.getCode());
            rs.updateString(4, c.getDesc());
            rs.updateRow();
            rs.moveToCurrentRow();
        }




    }
}
