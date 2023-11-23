package Persistance.IDAO.Interface;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public interface ProductStock_IDAO {

    void save(int pid, int qty, Date expiry) throws SQLException;

    int delete(int pid,Date expiry) throws SQLException;

    void update(int pid,int need) throws SQLException;

    List<Integer> checkExpiry()throws SQLException;

    Vector<Object> getExpiredProduct(List<Integer> stockIds) throws SQLException;

}
