package Application.Service.Implementation;

import Application.Model.Category;
import Application.Service.CategoryService;
import Persistance.IDAO.DAO.CategoryDAO;
import Persistance.IDAO.DAO.CategoryHierDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CategoryS_I implements CategoryService {

    private static CategoryDAO categoryRepo;
    private static CategoryHierDAO hierDAO;

    public CategoryS_I() throws SQLException {
       categoryRepo = new CategoryDAO();
       hierDAO = new CategoryHierDAO();
    }


    @Override
    public void add(Category c) throws SQLException {
        categoryRepo.save(c);
    }

    @Override
    public void delete(String code) throws SQLException {

        categoryRepo.delete(code);
    }

    @Override
    public void update(Category c) throws SQLException {

        categoryRepo.update(c);
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        return categoryRepo.findAll();
    }

    @Override
    public Category findByCode(String code) throws SQLException {
        return categoryRepo.findById(code);
    }

    @Override
    public void addSubCategory(String parent, String child) throws SQLException {

        hierDAO.save(parent,child);
    }

    @Override
    public List<String> makeTree(String code) throws SQLException {
        return hierDAO.getTree(code);
    }

    @Override
    public HashMap<String, String> getParentChild() throws SQLException {
        return hierDAO.getAllParentChild();
    }
}
