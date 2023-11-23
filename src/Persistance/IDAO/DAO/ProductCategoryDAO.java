package Persistance.IDAO.DAO;

import Persistance.Connection.DB_Connection;
import Persistance.IDAO.Interface.Intermediate_IDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        rs.updateObject(1,x);
        rs.updateObject(2,y);

        rs.insertRow();

        rs.moveToCurrentRow();
    }

    @Override
    public void delete(Object x, Object y) throws SQLException {

        rs.absolute(0);

        while(rs.next() && (rs.getInt(1)!= (Integer)x || rs.getInt(2)!= (Integer)y ));

        rs.deleteRow();
        rs.moveToCurrentRow();
    }
}
