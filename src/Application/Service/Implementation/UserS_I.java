package Application.Service.Implementation;

import Application.Model.User;
import Application.Service.UserService;
import Persistance.IDAO.DAO.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserS_I implements UserService {

    private static UserDAO userRepo;

    public UserS_I() throws SQLException {
        userRepo = new UserDAO();
    }

    @Override
    public void add(User u) throws SQLException {

        userRepo.save(u);

    }

    @Override
    public void update(User u) throws SQLException {
        userRepo.update(u);
    }

    @Override
    public List<User> getAll() throws SQLException {

        return userRepo.findAll();
    }

    @Override
    public void delete(String id) throws SQLException {

        userRepo.delete(id);
    }

    @Override
    public User findById(Integer id) throws SQLException {
        return userRepo.findById(id);
    }

    @Override
    public User findByUserName(String userName) throws SQLException {
        return userRepo.findByUserName(userName);
    }
}
