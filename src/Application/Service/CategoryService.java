package Application.Service;

import Application.Model.Category;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface CategoryService {

    void add(Category c) throws SQLException;

    void delete(String code) throws SQLException;

    void update(Category c) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    Category findByCode(String code) throws SQLException;


    void addSubCategory(String parent, String child) throws SQLException;


    List<String> makeTree(String code) throws SQLException;

    HashMap<String,String> getParentChild() throws SQLException;




}
