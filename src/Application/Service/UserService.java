package Application.Service;

import Application.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

     void add(User u) throws SQLException;
     void update(User u) throws SQLException;
     List<User> getAll() throws SQLException;

     void delete(String id) throws SQLException;

     User findById(Integer id) throws SQLException;
     User findByUserName(String userName) throws SQLException;

}
