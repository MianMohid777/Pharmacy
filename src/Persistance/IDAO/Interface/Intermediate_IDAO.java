package Persistance.IDAO.Interface;

import java.sql.SQLException;
import java.util.List;

public interface Intermediate_IDAO {

    void save(Object x , Object y) throws SQLException;

    void delete(Object x,Object y) throws SQLException;

    void update(Object x,Object y) throws SQLException;


}
