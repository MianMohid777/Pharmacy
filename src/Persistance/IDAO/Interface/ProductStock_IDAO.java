package Persistance.IDAO.Interface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface ProductStock_IDAO {

    void save(String pid, int qty, LocalDate expiry) throws SQLException;

    int delete(Integer stockId) throws SQLException;

    void update(String pid,int need) throws SQLException;

    List<Integer> checkExpiry()throws SQLException;

    HashMap<Integer,String> getExpiredProduct(List<Integer> stockIds) throws SQLException;

    Integer findStock(Integer id, String code) throws SQLException;

}
