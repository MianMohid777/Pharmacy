package Application.Service.Implementation;

import Application.Model.User;
import Application.Service.UserService;
import Persistance.IDAO.DAO.UserDAO;

import java.util.List;

public class UserService_I implements UserService {

    private UserDAO userRepo;

    @Override
    public void add(User u) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public User findById(Integer id) {
        return null;
    }
}
