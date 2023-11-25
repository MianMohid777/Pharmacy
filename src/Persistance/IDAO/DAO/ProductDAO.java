package Persistance.IDAO.DAO;

import Application.Model.Product;
import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.IDAO;
import com.mysql.cj.protocol.Resultset;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO implements IDAO<Product> {

    private ResultSet rs;

    public ProductDAO() throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM product";
        PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = statement.executeQuery();
    }

    @Override
    public void save(Product p) throws SQLException {

        rs.moveToInsertRow();

        rs.updateString(2, p.getName());
        rs.updateString(3, p.getCode());
        rs.updateString(4, p.getDesc());


        rs.updateString(5, String.valueOf(p.getPackQty()));


        rs.updateString(6, String.valueOf(p.getQtyPerPack()));


        rs.updateString(7, String.valueOf(p.getStockQty()));


        rs.updateString(8, String.valueOf(p.getPrice()));


        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public void delete(Object id) throws SQLException {

        String code = (String) id;
        rs.absolute(0);

        while (rs.next() && !rs.getString(3).equals(code));

        rs.deleteRow();

        rs.moveToCurrentRow();
    }

    @Override
    public List<Product> findAll() throws SQLException {

        rs.absolute(0);

        List<Product> products = new LinkedList<>();

        while (rs.next()) {
            Product p = new Product();

            p.setName(rs.getString(2));
            p.setCode(rs.getString(3));
            p.setDesc(rs.getString(4));


            p.setPackQty(rs.getInt(5));

            p.setQtyPerPack(rs.getInt(6));


            p.setStockQty(rs.getInt(7));


            p.setPrice(rs.getFloat(8));


            products.add(p);

        }
        return products;
    }

    @Override
    public Product findById(Object id) throws SQLException {

        String code = (String) id;

        rs.absolute(0);

        Product p = new Product();

        while (rs.next() && !rs.getString(3).equals(code)) ;

        p.setName(rs.getString(2));
        p.setCode(rs.getString(3));
        p.setDesc(rs.getString(4));


        p.setPackQty(rs.getInt(5));

        p.setQtyPerPack(rs.getInt(6));


        p.setStockQty(rs.getInt(7));


        p.setPrice(rs.getFloat(8));


        return p;
    }

    @Override
    public void update(Product p) throws SQLException {

        String code = p.getCode();

        rs.absolute(0);

        while (rs.next() && !rs.getString(3).equals(code)) ;

        if (!rs.getString(2).equals(p.getName())) {
            rs.updateString(2, p.getName());
            rs.updateString(3, p.getCode());
            rs.updateString(4, p.getDesc());
        }

        if (rs.getInt(5) != p.getPackQty()) {
            rs.updateString(5, String.valueOf(p.getPackQty()));
        }

        if (rs.getInt(6) != p.getQtyPerPack()) {
            rs.updateString(6, String.valueOf(p.getQtyPerPack()));
        }

        if (rs.getInt(7) != p.getStockQty()) {
            rs.updateString(7, String.valueOf(p.getStockQty()));
        }

        if (rs.getFloat(8) != p.getPrice()) {
            rs.updateString(8, String.valueOf(p.getPrice()));
        }


        rs.updateRow();

        rs.moveToCurrentRow();
    }

    public List<String> findBySearch(String search) throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM product WHERE prod_Name LIKE CONCAT(?,'%')";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,search);
        ResultSet set = statement.executeQuery();

        List<String> searchResult = new LinkedList<>();

        while(set.next())
        {
            searchResult.add(set.getString(2));
        }

        return searchResult;
    }

    public Product findByName(String name) throws SQLException {
        Connection conn = DB_Connection.getConnection();
        String query = "SELECT * FROM product WHERE prod_Name = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,name);
        ResultSet set = statement.executeQuery();

        if(set.next())
        {
            Product p = new Product();

            p.setName(set.getString(2));
            p.setCode(set.getString(3));
            p.setDesc(set.getString(4));


            p.setPackQty(set.getInt(5));

            p.setQtyPerPack(set.getInt(6));


            p.setStockQty(set.getInt(7));


            p.setPrice(set.getFloat(8));

            return p;
        }

        return null;
    }

    public static void main(String[] args) throws SQLException {
        ProductDAO dao = new ProductDAO();

        for(String s: dao.findBySearch("Pa"))
        {
            System.out.println(s);
        }
    }
}
