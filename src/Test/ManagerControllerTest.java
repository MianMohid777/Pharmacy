package Test;

import Application.Model.Product;
import Presentation.Controller.Supporting.ManagerController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ManagerControllerTest {

    @Test
    void addProduct() throws SQLException {
        ManagerController controller = new ManagerController();
        assertFalse(controller.addProduct("TestProduct1", "Test Description", 10, 5.0F, "", "TestCategory"));
        assertFalse(controller.addProduct("DuplicateProduct", "Duplicate Description", 5, 8.0F, "", "TestCategory"));
    }

    @Test
    void updateProduct() throws SQLException {
        ManagerController controller = new ManagerController();
        assertTrue(controller.updateProduct("UpdatedProduct", "EN5Med66129908", "Updated Description", 15, 10.0F));
        try {
            assertFalse(controller.updateProduct("InvalidProduct", "InvalidCode", "Invalid Description", 5, 8.0F));
        }catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeProduct() throws SQLException {
        ManagerController controller = new ManagerController();
        try {
            assertTrue(controller.removeProduct("EN5Med66129908"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        assertFalse(controller.removeProduct("InvalidCode"));
    }

    @Test
    void addStock() throws SQLException {
        ManagerController controller = new ManagerController();
        LocalDate futureDate = LocalDate.now().plusDays(30);
        assertTrue(controller.addStock("EN5Med66129908", 20, futureDate));
        assertFalse(controller.addStock("InvalidCode", 10, futureDate));
    }

    @Test
    void removeStock() throws SQLException {
        ManagerController controller = new ManagerController();
        try{
            assertTrue(controller.removeStock(1, "EN5Med66129908", 5));
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        assertFalse(controller.removeStock(1, "InvalidCode", 10));
    }

    @Test
    void clearExpireStock() throws SQLException {
        ManagerController controller = new ManagerController();
        assertFalse(controller.clearExpireStock()); // Assuming there's no expired stock initially
    }

    @Test
    void addCategory() throws SQLException {
        ManagerController controller = new ManagerController();
        assertTrue(controller.addCategory("NewCategoryX", "New Category Description"));
        assertFalse(controller.addCategory("DuplicateCategory", "Duplicate Category Description"));
    }

    @Test
    void updateCategory() throws SQLException {
        ManagerController controller = new ManagerController();
        assertFalse(controller.updateCategory("UpdatedCategory", "Updated Category Description", "Ki-2047723204"));
        assertFalse(controller.updateCategory("InvalidCategory", "Invalid Category Description", "InvalidCode"));
    }

    @Test
    void updateHierarchy() throws SQLException {
        ManagerController controller = new ManagerController();
        assertTrue(controller.updateHierarchy("TestCategory", "NewRegex"));
        assertTrue(controller.updateHierarchy("InvalidCategory", "NewRegex"));
    }

    @Test
    void addSubCategory() throws SQLException {
        ManagerController controller = new ManagerController();
        assertFalse(controller.addSubCategory("TestCategory", "NewSubCategory"));
        assertFalse(controller.addSubCategory("InvalidParentCategory", "NewSubCategory"));
        assertFalse(controller.addSubCategory("TestCategory", "DuplicateSubCategory"));
    }

    @Test
    void giveSearchResult() throws SQLException {
        ManagerController controller = new ManagerController();
        List<String> searchResult = controller.giveSearchResult("Panadol");
        assertNotNull(searchResult);
        assertFalse(searchResult.isEmpty());
    }

    @Test
    void getSearchedProd() throws SQLException {
        ManagerController controller = new ManagerController();
        assertNotNull(controller.getSearchedProd("panadol"));
    }

    @Test
    void codeSearch() throws SQLException {
        ManagerController controller = new ManagerController();
        assertNotNull(controller.codeSearch("EN5Med66129908"));
        assertNull(controller.codeSearch("InvalidCode"));
    }

    @Test
    void checkCodeProd() throws SQLException {
        ManagerController controller = new ManagerController();
        Product product = controller.getSearchedProd("panadol");
        assertNotNull(product);
        assertEquals("PA20Med-79215811", controller.checkCodeProd(product));
    }

    @Test
    void checkCategoryName() throws SQLException {
        ManagerController controller = new ManagerController();
        assertNotEquals("TestCategory", controller.checkCategoryName("Ki-2047723204"));
    }

    @Test
    void categorySearchByCode() throws SQLException {
        ManagerController controller = new ManagerController();
        assertNotNull(controller.categorySearchByCode("Ki-2047723204"));
        try {
            assertNotNull(controller.categorySearchByCode("InvalidCode"));
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void salesReport() throws SQLException {
        ManagerController controller = new ManagerController();
        assertDoesNotThrow(() -> controller.SalesReport("DAILY",null,null));
        assertDoesNotThrow(() -> controller.SalesReport("WEEKLY",null,null));
        assertDoesNotThrow(() -> controller.SalesReport("MONTHLY",null,null));
    }

    @Test
    void pileStocks() throws SQLException {
        ManagerController controller = new ManagerController();
        List<Vector<Object>> stocks = controller.pileStocks("EN5Med66129908");
        assertNotNull(stocks);
    }
}