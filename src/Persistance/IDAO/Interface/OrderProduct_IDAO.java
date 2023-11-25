package Persistance.IDAO.Interface;

import Application.Model.Product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface OrderProduct_IDAO {

    void save(int oid,String code,int qty) throws SQLException;

    HashMap<Integer,String> getAllProductsByOrderId(int oid) throws SQLException;


}
