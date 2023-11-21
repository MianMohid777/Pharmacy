package Application.Service;

import Application.Model.User;

import java.util.List;

public interface UserService {

    public void add(User u);
    public List<User> getAll();

    public void delete(Integer id);

    public User findById(Integer id);



}
